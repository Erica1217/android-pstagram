package com.pstagram.pstagram.pstagram.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.pstagram.pstagram.pstagram.R;
import com.pstagram.pstagram.pstagram.RetrofitCreater;
import com.pstagram.pstagram.pstagram.TextUtil;
import com.pstagram.pstagram.pstagram.UserIdManager;
import com.pstagram.pstagram.pstagram.databinding.ActivityLoginBinding;
import com.pstagram.pstagram.pstagram.model.LoginInputModel;
import com.pstagram.pstagram.pstagram.model.LoginOutputModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Context context = this;
    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInputModel inputModel = new LoginInputModel();

                if(!TextUtil.isValidEmail(binding.email.getText())){
                    Toast.makeText(LoginActivity.this,"유효한 이메일이 아닙니다",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!TextUtil.isVaildPassword(binding.password.getText())){
                    Toast.makeText(LoginActivity.this,"유효한 비밀번호가 아닙니다",Toast.LENGTH_SHORT).show();
                    inputModel.setEmail(binding.email.getText().toString());
                    return;
                }else{
                    inputModel.setPassword(binding.password.getText().toString());
                }

                RetrofitCreater.newInstance().getService().login(inputModel).enqueue(new Callback<LoginOutputModel>() {
                    @Override
                    public void onResponse(Call<LoginOutputModel> call, Response<LoginOutputModel> response) {
                        if(response.isSuccessful()){
                            LoginOutputModel data= response.body();
                            if(data.getCode().equals("success")){
                                UserIdManager.getInstance(context).saveUserId(data.getUserId());
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
//                                Snackbar.make(binding.parentLayout,data.getMsg() , Snackbar.LENGTH_SHORT);
                                Toast.makeText(context,data.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context,response.code()+" 오류코드",Toast.LENGTH_SHORT).show();
//                            Snackbar.make(binding.parentLayout,response.code()+" 오류코드" , Snackbar.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginOutputModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
