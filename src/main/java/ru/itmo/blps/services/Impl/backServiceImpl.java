package ru.itmo.blps.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Back_record;
import ru.itmo.blps.DAO.entities.Backer_project;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.BPMapper;
import ru.itmo.blps.DAO.mappers.BRMapper;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.services.Exceptions.NoEnoughMoneyException;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.Exceptions.NoSuchUserException;
import ru.itmo.blps.services.Exceptions.ServiceException;
import ru.itmo.blps.services.backService;

@Service
public class backServiceImpl implements backService {

    @Autowired
    private BRMapper brMapper;

    @Autowired
    private BPMapper bpMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int back(Integer projectId, Integer userId, Integer amount) {
        Project project = projectMapper.findProjectById(projectId);
        if (project == null) throw new NoSuchProjectException("Can't find this project.");

        User user = userMapper.findUserById(userId);
        if (user == null) throw new NoSuchUserException("No such user.");

        // Update backer list.
        Backer_project bp = bpMapper.findBPByUIDAndPID(userId, projectId);
        if (bp == null) {
            Backer_project newBP = new Backer_project(userId, projectId);
            bpMapper.insertBP(newBP);
        }

        // Insert back record.
        Back_record br = new Back_record();
        br.setUser_id(userId);
        br.setProject_id(projectId);
        br.setAmount(amount);
        int effectRow = brMapper.insertBR(br);

        // Update current amount.
        if (amount < 0)
            throw new ServiceException("Please give me money! Not steal mine!!!");
        projectMapper.updateCurrentMoney(projectId,amount + project.getCurrent_amount());

        return effectRow;

    }

}
