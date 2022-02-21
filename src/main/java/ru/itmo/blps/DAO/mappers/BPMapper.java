package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import ru.itmo.blps.DAO.entities.Backer_project;

import java.util.List;

public interface BPMapper {
    @Insert("insert into backer_project(user_id, project_id) values " +
            "(#{user_id},#{project_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertBP(Backer_project bp);

    @Select("select * from backer_project where user_id = #{uid}")
    Backer_project findBPByUID(Integer uid);

    @Select("select * from backer_project where project_id = #{pid}")
    Backer_project findBPByPID(Integer pid);

    @Select("select * from backer_project where user_id = #{uid} and project_id = #{pid}")
    Backer_project findBPByUIDAndPID(Integer uid, Integer pid);

    @Select("select * from backer_project")
    List<Backer_project> findAllBP();
}
