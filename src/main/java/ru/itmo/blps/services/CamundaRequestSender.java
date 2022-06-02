package ru.itmo.blps.services;

import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;

@Service
public interface CamundaRequestSender {
    void createProject(User user, Project project);
    void backProject(User user, int amount, String projectName);
}
