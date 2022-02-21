package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user_t(login,password) values(#{login},#{password})")
    @SelectKey(statement = " SELECT currval('user_t_id_seq')", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUser(User user);

    User findUserById(Integer id);

    List<User> findAllUsers();
}