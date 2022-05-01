package ru.itmo.blps.services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.ServiceException;
import ru.itmo.blps.services.InitiatorService;

@Service
@AllArgsConstructor
public class InitiatorServiceImpl implements InitiatorService {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    @Override
    public void takeMoney(Integer uid, Integer pid, Integer amount) {
        Project project = projectMapper.findProjectById(pid);
        if (project == null) throw new NoSuchProjectException("Can't find this project.");
        if (project.getStatus() != 1) throw new ServiceException("Can't get money from this project.");

        User user = userMapper.findUserById(uid);
        if (user == null) throw new NoSuchUserException("No such user.");

        System.out.println("Take away your money!!!");

        projectMapper.updateStatus(pid, 2);
    }
}
