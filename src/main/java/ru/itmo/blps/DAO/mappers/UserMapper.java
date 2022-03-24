package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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
}