package cn.zed058.smbms.controller;

import cn.zed058.smbms.pojo.Role;
import cn.zed058.smbms.pojo.User;
import cn.zed058.smbms.service.RoleService;
import cn.zed058.smbms.service.UserService;
import cn.zed058.smbms.util.Constant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user") // 窄化请求
public class UserController extends BaseController {

    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

    Logger logger = Logger.getLogger(UserController.class);

    // 用户退出的登陆
    @RequestMapping("logout.html")
    public String logout(HttpSession session){
        session.removeAttribute(Constant.CURRENT_USR);
        return "login";
    }

    @RequestMapping("userCodeExist")
    @ResponseBody //把返回值直接写入响应体中
    public String userCodeExist(String userCode) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(userCode)) {
            map.put("userCode", "exist"); // !=
        } else {
            if (userService.findUserByUserCode(userCode)) {
                map.put("userCode", "exist");
            } else {
                map.put("userCode", "noexist");
            }
        }
        return JSONObject.toJSONString(map);
    }

    // 修改用户 1.跳转修改页面
    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public String modify(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        return "usermodify";
    }

    @RequestMapping(value = "modifySave", method = RequestMethod.POST)
    public String modifySave(User user, HttpSession session) {
        // 补全用户信息
        User sessionUser = (User) session.getAttribute("user");
        user.setCreatedBy(sessionUser.getId());  // 修改人
        user.setCreationDate(new Date()); // 修改时间
        if (userService.updateUser(user) > 0) {
            return "redirect:/user/userList.html"; // 修改成功
        } else {
            return "usermodify";
        }
    }

    // 异步查看用户信息
   @RequestMapping(value = "view/{id}")
    @ResponseBody
    public Object view(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    /* @RequestMapping("view")
    @ResponseBody
    public Object view(Integer id){
        return userService.findUserById(id);
    }*/

    @RequestMapping(value = "useradd", method = RequestMethod.GET)
    public String userAdd(@ModelAttribute("user") User user) {
        return "useradd";
    }


    // 保存用户的方法
    @RequestMapping(value = "useraddsave", method = RequestMethod.POST)
    public String useraddsave(@Valid User user, BindingResult result, HttpSession session,
                              @RequestParam("a_idPicPath") MultipartFile idPicPath,
                              HttpServletRequest request) throws IOException {

        // 检查校验错误
        if (result.hasErrors()) {
            return "useradd";
        }

        // 上传文件操作
        if (!idPicPath.isEmpty()) {
            // 上传的有文件
            int size = 1024 * 1000 * 1000 * 5;
            if (idPicPath.getSize() > size) { //如果文件超过1M不允许上传了
                request.setAttribute("error", "文件太大，超过5M了");
                return "useradd";
            } else {
                String originalFilename = idPicPath.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename); //获取文件扩展名
                if ("png".equals(extension) || "jpg".equals(extension) || "jpeg".equals(extension) || "bmp".equals(extension)) {
                    // 改一下文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(100000) + "." + extension;
                    String realPath = request.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
                    File file = new File(realPath, fileName);
                    // 判断目标文件是否存在
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    idPicPath.transferTo(file);
                    // 后续操作
                    user.setIdPicPath("statics" + File.separator + "uploadfiles" + File.separator + fileName);
                } else {
                    request.setAttribute("error", "文件类型不对，必须是一个图片");
                    return "useradd";
                }
            }
        }
        User sessionUser = (User) session.getAttribute("user");
        user.setCreatedBy(sessionUser.getId());  // 创建人
        user.setCreationDate(new Date()); // 创建时间
        // 保存用户信息的方法
        if (userService.addUser(user) > 0) {
            return "redirect:/user/userList.html"; // 添加成功
        }
        return "useradd";
    }

    @RequestMapping(value = "doLogin.html", method = RequestMethod.POST)
    public String doLogin(String userCode, String userPassword, HttpServletRequest request, HttpSession session) {
        User user = userService.login(userCode, userPassword);
        if (user == null) {// 登錄失敗
            request.setAttribute("error", "用戶名或密碼錯誤");
            return "login";
        } else {// 登錄成功
            session.setAttribute(Constant.CURRENT_USR, user);
            return "frame";
        }
    }

    @RequestMapping("userList.html")
    public String userList(@RequestParam(value = "queryName", required = false) String queryName,
                           @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
                           @RequestParam(value = "currentNo", required = false, defaultValue = "1") Integer currentNo,
                           Model model) {
        int _queryUserRole = 0; //查询的角色
        int pageSize = Constant.pageSize;
        if (queryName == null) queryName = "";
        if (!StringUtils.isEmpty(queryUserRole)) {
            _queryUserRole = Integer.parseInt(queryUserRole);
        }
        List<User> userList = userService.allUserByCondition(queryName, _queryUserRole, currentNo, pageSize);
        int totalCount = userService.count(queryName, _queryUserRole);
        int totalPageCount = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize) + 1;

        // 查询所有的角色
        List<Role> roleList = roleService.findAll();
        // 到页面回传数据
        model.addAttribute("roleList", roleList);
        model.addAttribute("userList", userList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentPageNo", currentNo);
        model.addAttribute("queryName", queryName);
        model.addAttribute("queryUserRole", _queryUserRole);
        return "userList";
    }
}
