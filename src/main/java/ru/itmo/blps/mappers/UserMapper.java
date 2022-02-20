package ru.itmo.blps.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.context.annotation.Bean;
import ru.itmo.blps.models.User;

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