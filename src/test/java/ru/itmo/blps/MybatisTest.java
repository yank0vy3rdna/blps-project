package ru.itmo.blps;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import ru.itmo.blps.DAO.entities.BackRecord;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;
import ru.itmo.blps.DAO.mappers.BRMapper;
import ru.itmo.blps.DAO.mappers.ProjectMapper;
import ru.itmo.blps.DAO.mappers.UserMapper;
import ru.itmo.blps.DAO.utils.MybatisUtils;

import java.util.List;

public class MybatisTest {
    @Test
    public void testForUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
//        UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
//        Users users = mapper.findUserById(1);
        UserMapper mapper2 = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper2.findAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        sqlSession.close();
    }

    @Test
    public void testForProjectMapper() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProjectMapper mapper2 = sqlSession.getMapper(ProjectMapper.class);
        List<Project> pro = mapper2.findAllProjects();
        for (Project project : pro) {
            System.out.println(project.toString());
        }
        sqlSession.close();
    }


    @Test
    public void testForBR() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
//        UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
//        Users users = mapper.findUserById(1);
        BRMapper mapper2 = sqlSession.getMapper(BRMapper.class);
        List<BackRecord> pro = mapper2.findAllBR();
        for (BackRecord project : pro) {
            System.out.println(project.toString());
        }
        sqlSession.close();
    }


}
