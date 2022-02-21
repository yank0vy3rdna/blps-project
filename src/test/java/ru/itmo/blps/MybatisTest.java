package ru.itmo.blps;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import ru.itmo.blps.DAO.entities.User;
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
}
