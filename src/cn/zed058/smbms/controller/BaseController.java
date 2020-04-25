package cn.zed058.smbms.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // 自定义编辑器进行类型转换
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
