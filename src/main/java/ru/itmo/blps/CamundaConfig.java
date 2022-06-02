package ru.itmo.blps;

import lombok.AllArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.EnableExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.BackerController;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;

@Configuration
@EnableExternalTaskClient()
@AllArgsConstructor
public class CamundaConfig {
    private static final Logger logger = LoggerFactory.getLogger(BackerController.class);
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final BackMessageProducerService backMessageProducerService;


    @Bean
    @ExternalTaskSubscription(
            topicName = "create_project"
    )
    public ExternalTaskHandler getCreateProjectHandler() {
        return (ExternalTask externalTask, ExternalTaskService externalTaskService) -> {
            Integer userId = externalTask.getVariable("userId");
            if (userId == null) {
                return;
            }
            User user = userMapper.findUserById(userId);
            if (user == null) {
                throw new UsernameNotFoundException(userId.toString());
            }
            Project project = new Project();
            project.setDescription(externalTask.getVariable("project_description"));
            project.setName(externalTask.getVariable("project_name"));
            project.setTargetAmount(externalTask.getVariable("project_target_amount"));
            projectMapper.insertProject(project);
            projectMapper.addInitiator(user.getId(), project.getId());
            externalTaskService.complete(externalTask);
        };
    }

    @Bean
    @ExternalTaskSubscription(topicName = "backProject")
    public ExternalTaskHandler getBackProjectHandler() {
        return (ExternalTask externalTask, ExternalTaskService externalTaskService) -> {
            Integer userId = externalTask.getVariable("userId");
            if (userId == null) {
                return;
            }
            User user = userMapper.findUserById(userId);
            if (user == null) {
                throw new UsernameNotFoundException(userId.toString());
            }
            BackModel backModel = new BackModel(
                    projectMapper.findIdByName(externalTask.getVariable("project_name")),
                    externalTask.getVariable("back_amount"), user.getUsername());
            backMessageProducerService.sendBackMessage("back", backModel);
            externalTaskService.complete(externalTask);
        };
    }

    @Bean
    @ExternalTaskSubscription(topicName = "backNotification")
    public ExternalTaskHandler getBackNotificationHandler() {
        return (ExternalTask externalTask, ExternalTaskService externalTaskService) -> {
            Integer amount = externalTask.getVariable("back_amount");
            logger.info("Somebody backed project " + externalTask.getVariable("project_name") + " for " + amount + "$");
            externalTaskService.complete(externalTask);
        };
    }
}