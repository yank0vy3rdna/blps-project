package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.*;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;


@Mapper
public interface UserMapper {

    @Insert("insert into user_t(username,password, role) values (#{username},#{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);

    @Select("select * from user_t where id = #{id}")
    User findUserById(Integer id);

    @Select("select * from user_t where username = #{username}")
    User findUserByLogin(String username);

    @Select("select * from user_t")
    List<User> findAllUsers();

    @Update("update user_t set role = #{role} where id = #{id}")
    Integer setRole(Integer id, Integer role);
}