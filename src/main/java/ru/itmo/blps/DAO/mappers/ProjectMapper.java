package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.*;
import ru.itmo.blps.DAO.entities.Project;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project(target_amount, current_amount, name, description) values (#{targetAmount}, 0, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertProject(Project project);

    @Insert("insert into initiator_project(user_id, project_id) values (#{user_id}, ${project_id})")
    void addInitiator(Integer user_id, Integer project_id);

    @Insert("insert into backer_project(user_id, project_id) values (#{user_id}, ${project_id})")
    void addBacker(Integer user_id, Integer project_id);

    @Select("select * from project where id = #{id}")
    Project findProjectById(Integer id);

    @Select("select * from user_t inner join initiator_project ip on user_t.id = ip.user_id where project_id = #{project_id}")
    List<User> getInitiators(Integer project_id);

    @Select("select * from user_t inner join backer_project bp on user_t.id = bp.user_id where project_id = #{project_id}")
    List<User> getBackers(Integer project_id);

    @Select("select * from project inner join backer_project bp on project.id = bp.project_id where user_id = #{user_id}")
    List<Project> getBackedProjects(Integer user_id);

    @Select("select * from project inner join initiator_project bp on project.id = bp.project_id where user_id = #{user_id}")
    List<Project> getInitializedProjects(Integer user_id);

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
