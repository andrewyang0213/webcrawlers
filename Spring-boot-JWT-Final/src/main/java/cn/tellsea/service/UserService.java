package cn.tellsea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tellsea.bean.User;
import cn.tellsea.dto.ResponseResult;
import cn.tellsea.mapper.UserMapper;

@Service("UserService")
public interface UserService {

    User findByUsername(User user);

    User findUserById(String userId);

    ResponseResult insert(User user);

    ResponseResult updateByPrimaryKey(User user);

    ResponseResult deleteByPrimaryKey(Integer id);

    ResponseResult deleteByIds(String id);

    ResponseResult selectByPrimaryKey(Integer id);

    String selectByPage(Integer offset, Integer limit, String name, String email, String address);

    User checkUserPass(String username, String password);

}