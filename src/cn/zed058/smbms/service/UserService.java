package cn.zed058.smbms.service;

import cn.zed058.smbms.pojo.User;

import java.util.List;

public interface UserService {

    int count(String userName,Integer userRole);
    List<User> allUser();
    int addUser(User user);
    int deleteUser(Integer id);
    User login(String userCode, String userPassword);
    List<User> allUserByCondition(String userName,Integer userRole,Integer currentNo,Integer pageSize);

    User findUserById(Integer id);

    int updateUser(User user);

    boolean findUserByUserCode(String userCode);
}
