package cn.zed058.smbms.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrToDateConverter implements Converter<String, Date> {
    String formater;

    public String getFormater() {
        return formater;
    }

    public void setFormater(String formater) {
        this.formater = formater;
    }

    @Override
    public Date convert(String s) {
        Date date = null;
        try {
            date = new SimpleDateFormat(formater).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
