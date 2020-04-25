package cn.zed058.smbms.service.impl;

import cn.zed058.smbms.dao.UserMapper;
import cn.zed058.smbms.pojo.User;
import cn.zed058.smbms.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public int count(String userName, Integer userRole) {
        return userMapper.getCountByCondition(userName,userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> allUser() {
        return userMapper.findAll();
    }

    @Override
    public int addUser(User user) {
        return userMapper.add(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.delete(id);
    }

    @Override
    public User login(String userCode, String userPassword) {
        List<User> userList = userMapper.getUserByUserCode(userCode);
        return userList.stream().filter(user -> userPassword.equals(user.getUserPassword())).findFirst().orElse(null);
    }

  /*  public User login2(String userCode, String userPassword) {
        List<User> userList = userMapper.getUserByUserCode(userCode);
        for (User user:userList) {
            if(userPassword.equals(user.getUserPassword())){
                return user;
            }
        }
        return null;
    }
*/
    @Override
    public List<User> allUserByCondition(String userName, Integer userRole, Integer currentNo, Integer pageSize) {
        return userMapper.findAllByConditionWithPage(userName,userRole,(currentNo-1)*pageSize,pageSize);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    @Override
    public boolean findUserByUserCode(String userCode) {
        return userMapper.findUserByUserCode(userCode)>0;
    }
}
