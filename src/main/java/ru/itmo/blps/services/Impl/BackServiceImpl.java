package ru.itmo.blps.services.Impl;

import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.BackRecord;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.BRMapper;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.BackService;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.ServiceException;

@Service
public class BackServiceImpl implements BackService {

    private final BRMapper brMapper;

    private final ProjectMapper projectMapper;

    private final UserMapper userMapper;

    public BackServiceImpl(BRMapper brMapper, ProjectMapper projectMapper, UserMapper userMapper) {
        this.brMapper = brMapper;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }

    @Override
    public int back(Integer projectId, Integer userId, Integer amount) {
        if (amount < 0) {
            throw new ServiceException("Please give me money! Not steal mine!!!");
        }
        Project project = projectMapper.findProjectById(projectId);
        if (project == null) {
            throw new NoSuchProjectException("Can't find this project.");
        }

        User user = userMapper.findUserById(userId);
        if (user == null) {
            throw new NoSuchUserException("No such user.");
        }

        // Update backer list.
        projectMapper.addBacker(userId, projectId);

        // Insert back record.
        BackRecord br = new BackRecord();
        br.setUserId(userId);
        br.setProjectId(projectId);
        br.setAmount(amount);
        int effectRow = brMapper.insertBR(br);
        Integer new_amount = amount;
        if (project.getCurrentAmount() != null) {
            new_amount += project.getCurrentAmount();
        }

        // Update current amount.
        projectMapper.updateCurrentMoney(projectId, new_amount);

        return effectRow;

    }

}
