package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into bl.project(target_amount, current_amount, name, description) values (#{target_amount}, #{current_amount}, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertProject(Project project);

    @Select("select * from bl.project where id = #{id}")
    Project findProjectById(Integer id);

    @Select("select * from bl.project where name = #{name}")
    Project findProjectByName(String name);

    @Select("select * from bl.project")
    List<Project> findAllProjects();

    @Select("select id from bl.project where name = #{name}")
    Integer findIdByName(String name);
}
