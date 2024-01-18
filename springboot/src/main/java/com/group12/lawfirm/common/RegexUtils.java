package com.group12.lawfirm.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 验证手机号
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
        String phone_regex = "^(01|01[1,2,3,4,5,6,7,89])\\\\d{8}$";
        if (mobile.length() != 10 || mobile.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(phone_regex);
            Matcher m = p.matcher(mobile);
            boolean isPhone = m.matches();
            if (!isPhone) {
                return false;
            }
            return true;
        }
    }

    public static boolean checkEmail(String email) {
        String email_regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(email_regex);
        Matcher m = p.matcher(email);
        boolean isPhone = m.matches();
        if (!isPhone) {
            return false;
        }
        return true;
    }
}
