package ru.itmo.blps.mailing;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.ProjectService;

import java.util.List;

@Controller
@AllArgsConstructor
public class MailingController {
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    //    @Scheduled(cron = "0 20 * * *")
    @Scheduled(fixedRate = 1000)
    public void mailAboutProjectStatus() {
        List<User> users = userMapper.findAllUsers();
        for (User user : users) {
            List<Project> projects = projectMapper.getBackedProjects(user.getId());
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Message to user: ");
            messageBuilder.append(user.getUsername());
            for (Project project : projects) {
                messageBuilder.append("\n");
                messageBuilder.append(project.getName());
                messageBuilder.append(" collected ");
                messageBuilder.append(project.getCurrentAmount());
                messageBuilder.append(" dollars. Goal is ");
                messageBuilder.append(project.getTargetAmount());
            }
            System.out.println(messageBuilder);
        }
    }
}
