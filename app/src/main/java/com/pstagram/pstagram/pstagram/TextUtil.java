package com.pstagram.pstagram.pstagram;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class TextUtil {
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /* 특수문자 허용안함
    길이 2-10
    영문,한글,숫자만 허용
    공백 비허용    */
    public static boolean isVaildUserName(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else if (2 > target.length() || target.length() > 10) {
            return false;
        } else if (Pattern.matches("(^[a-zA-Z]|[ㄱ-ㅎ가-힣0-9]|\\S$)", target)) {
            return false;
        }
        return true;
    }


    /* 특수문자는 !@#$%^&*()_+ 만 허용
     * ?, 공백 비허용
     * 길이는 5-14
     * 한글 비허용 */
    public static boolean isVaildPassword(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else if (4 > target.length() || target.length() > 14) {
            return false;
        } else if (Pattern.matches("", target)) {
            return false;
        }
        return true;
    }
}
