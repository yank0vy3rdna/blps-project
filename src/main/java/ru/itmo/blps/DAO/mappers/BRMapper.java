package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import ru.itmo.blps.DAO.entities.BackRecord;

import java.util.List;

// Mapper for Back record.
@Mapper
public interface BRMapper {

    @Insert("insert into back_record(user_id, project_id, amount) values " +
            "(#{userId},#{projectId},#{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertBR(BackRecord br);

    @Select("select * from back_record where id = #{id}")
    BackRecord findBRById(Integer id);

    @Select("select * from back_record where user_id = #{uid}")
    BackRecord findBRByUID(Integer uid);

    @Select("select * from back_record where project_id = #{pid}")
    BackRecord findBRByPID(Integer pid);

    @Select("select 1 from backer_project where user_id = #{uid} and project_id = #{pid}")
    Integer findBPByUserIdAndProjectId(Integer uid, Integer pid);

    @Select("select * from back_record where amount = #{amount}")
    BackRecord findBRByAmount(Integer amount);

    @Select("select * from back_record where user_id = #{uid} and project_id = #{pid}")
    BackRecord findBRByUIDAndPID(Integer uid, Integer pid);

    @Select("select * from back_record")
    List<BackRecord> findAllBR();
}
