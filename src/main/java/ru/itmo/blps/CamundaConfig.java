package ru.itmo.blps;

import lombok.AllArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.EnableExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.controllers.inputModel.BackModel;
import ru.itmo.blps.services.BackMessageProducerService;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;

@Configuration
@EnableExternalTaskClient(
)
@AllArgsConstructor
public class CamundaConfig {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final BackMessageProducerService backMessageProducerService;


    @Bean
    @ExternalTaskSubscription(
            topicName = "create_project"
    )
    public ExternalTaskHandler getCreateProjectHandler() {
        return (ExternalTask externalTask, ExternalTaskService externalTaskService) -> {
            String userId = externalTask.getVariable("userId");
            if (userId == null) {
                return;
            }
            User user = userMapper.findUserByLogin(externalTask.getVariable("userId"));
            if (user == null) {
                userMapper.insertUser(new User(0, externalTask.getVariable("userId"), "", 0));
            }
            user = userMapper.findUserByLogin(externalTask.getVariable("userId"));
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
            String userId = externalTask.getVariable("userId");
            if (userId == null) {
                return;
            }
            User user = userMapper.findUserByLogin(externalTask.getVariable("userId"));
            if (user == null) {
                userMapper.insertUser(new User(0, externalTask.getVariable("userId"), "", 0));
            }
            user = userMapper.findUserByLogin(externalTask.getVariable("userId"));
            BackModel backModel = new BackModel(
                    projectMapper.findIdByName(externalTask.getVariable("project_name")),
                    externalTask.getVariable("back_amount"), user.getUsername());
            backMessageProducerService.sendBackMessage("back", backModel);
            externalTaskService.complete(externalTask);
        };
    }
}