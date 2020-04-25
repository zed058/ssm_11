package cn.zed058.smbms.service;

import cn.zed058.smbms.pojo.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    UserService userService;
    Logger logger;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (UserService) context.getBean("userService");
        logger = Logger.getLogger(UserServiceImplTest.class);
    }

    @Test
    public void count() {
        logger.debug("用户总数是:"+userService.count(null,null));
    }

    @Test
    public void allUser() {
        userService.allUser().forEach(logger::debug);
    }

    @Test
    public void testAdd(){
        User user = new User();
        user.setUserCode("lisi");
        user.setUserName("李四");
        user.setAddress("郑州");
        System.out.println("添加成功"+userService.addUser(user));
    }

    @Test
    public void testLogin(){
        System.out.println(userService.login("lisi", "11112"));
    }

    @Test
    public void testFindUserWithPage(){
        int currNo = 2; // 查询第几页
        int pageSize = 5; // 每页显示几条数据
        String userName = "";
        Integer userRole = null;

        System.out.println("符合条件的用户总数有");
        int count = userService.count(userName, userRole);
        int totalPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize) + 1;
        System.out.println("总共有多少页" + totalPage); // 第1页 / 共3页

        System.out.println("查询到的用户信息");
        List<User> users = userService.allUserByCondition(userName, userRole, currNo, pageSize);
        users.forEach(logger::debug);

    }
}