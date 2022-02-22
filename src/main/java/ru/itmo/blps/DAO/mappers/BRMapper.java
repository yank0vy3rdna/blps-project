package ru.itmo.blps.DAO.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import ru.itmo.blps.DAO.entities.Back_record;
import ru.itmo.blps.DAO.entities.User;

import java.util.List;

// Mapper for Back record.
@Mapper
public interface BRMapper {

    @Insert("insert into back_record(user_id, project_id, amount) values " +
            "(#{user_id},#{project_id},#{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertBR(Back_record br);

    @Select("select * from back_record where id = #{id}")
    Back_record findBRById(Integer id);

    @Select("select * from back_record where user_id = #{uid}")
    Back_record findBRByUID(Integer uid);

    @Select("select * from back_record where project_id = #{pid}")
    Back_record findBRByPID(Integer pid);

    @Select("select * from back_record where amount = #{amount}")
    Back_record findBRByAmount(Integer amount);

    @Select("select * from back_record where user_id = #{uid} and project_id = #{pid}")
    Back_record findBRByUIDAndPID(Integer uid, Integer pid);

    @Select("select * from back_record")
    List<Back_record> findAllBR();
}
