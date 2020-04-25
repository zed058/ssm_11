package cn.zed058.smbms.dao;

import cn.zed058.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<User> findAll();

    int add(User user);

    int delete(Integer id);

    List<User> getUserByUserCode(String userCode);

    List<User> findAllByConditionWithPage(
            @Param("userName") String userName,
            @Param("userRole") Integer userRole,
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize);

    int getCountByCondition(
            @Param("userName") String userName,
            @Param("userRole") Integer userRole);

    User findById(Integer id);

    int update(User user);

    int findUserByUserCode(String userCode);
}
