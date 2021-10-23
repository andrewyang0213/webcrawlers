package cn.tellsea.mapper;

import cn.tellsea.bean.User;
import cn.tellsea.base.mapper.MyMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends MyMapper<User> {

    User findByUsername(@Param("username") String username);

    User findUserById(@Param("Id") String Id);


    @Select("SELECT * FROM tb_user WHERE name LIKE '%${name}%' AND email LIKE '%${email}%' AND address LIKE '%${address}%' LIMIT ${offset}, ${limit}")
    List<User> selectByPage(Integer offset, Integer limit, String name, String email, String address);

    @Select("SELECT name, password FROM tb_user")
    List<User> selectUserPass(String name, String password);

    @Select("SELECT name, password FROM download_db.tb_user WHERE name like '%${name}%' AND password like '%${password}%'")
    User checkUserPass(String name, String password);

}
