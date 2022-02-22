package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project(target_amount, current_amount, name, description) values (#{target_amount}, #{current_amount}, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertProject(Project project);

    @Select("select * from project where id = #{id}")
    Project findProjectById(Integer id);

    @Select("select * from project where name = #{name}")
    Project findProjectByName(String name);

    @Select("select * from project")
    List<Project> findAllProjects();

    @Select("select id from project where name = #{name}")
    Integer findIdByName(String name);

    @Update("update project set current_amount = #{moneyAmount} where id = #{projectId}")
    Integer updateCurrentMoney(Integer projectId, Integer moneyAmount);

    @Update("update project set status = #{status} where id = #{projectId}")
    Integer updateStatus(Integer projectId, Integer status);
}
