package com.pstagram.pstagram.pstagram;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserIdManager {

    private static UserIdManager _instance;
    private Context context;
    private String KEY_USER_ID = "user_id";


    private UserIdManager(Context context) {
        this.context = context;
    }

    public static UserIdManager getInstance(Context context){
        if(_instance==null){
            _instance = new UserIdManager(context);
        }
        return _instance;
    }

    // 값 불러오기
    public int getUserId() {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getInt(KEY_USER_ID, 0);
    }

    // 값 저장하기
    public void saveUserId(int userId) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // 값(Key Data) 삭제하기
    public void removeUserId() {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(KEY_USER_ID);
        editor.apply();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences() {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}