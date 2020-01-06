package com.pstagram.pstagram.pstagram;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Util {
    public static void showText(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
//        Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    public static RequestBody createRequestBody(String str){
        return RequestBody.create(MediaType.parse("text/plain"),str);
    }
}
