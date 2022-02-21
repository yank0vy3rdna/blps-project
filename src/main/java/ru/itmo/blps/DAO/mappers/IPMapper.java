package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import ru.itmo.blps.DAO.entities.Initiator_project;

import java.util.List;

// Mapper for Initiator project.
@Mapper
public interface IPMapper {
    @Insert("insert into initiator_project(user_id, project_id) values " +
            "(#{user_id},#{project_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertIP(Initiator_project bp);

    @Select("select * from initiator_project where user_id = #{uid}")
    Initiator_project findIPByUID(Integer uid);

    @Select("select * from initiator_project where project_id = #{pid}")
    Initiator_project findIPByPID(Integer pid);

    @Select("select * from initiator_project where user_id = #{uid} and project_id = #{pid}")
    Initiator_project findIPByUIDAndPID(Integer uid, Integer pid);

    @Select("select * from initiator_project")
    List<Initiator_project> findAllIP();
}
