package ru.itmo.blps.services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.services.Exceptions.NoSuchProjectException;
import ru.itmo.blps.services.AdminService;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ProjectMapper projectMapper;

    @Override
    public int setStatus(Integer pid, Integer statusCode) {
        Project project = projectMapper.findProjectById(pid);
        if (project == null) throw new NoSuchProjectException("Can't find this project.");

        return projectMapper.updateStatus(pid, statusCode);
    }
}
