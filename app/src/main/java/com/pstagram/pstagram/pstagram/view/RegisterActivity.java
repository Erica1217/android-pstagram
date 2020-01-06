package com.pstagram.pstagram.pstagram.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.pstagram.pstagram.pstagram.*;
import com.pstagram.pstagram.pstagram.databinding.ActivityRegisterBinding;
import com.pstagram.pstagram.pstagram.model.RegisterInputModel;
import com.pstagram.pstagram.pstagram.model.RegisterOutputModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    iHttpService service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        service=RetrofitCreater.newInstance().getService();
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterInputModel inputModel = new RegisterInputModel();

                if(!TextUtil.isValidEmail(binding.email.getText())){
                    Toast.makeText(RegisterActivity.this,"유효한 이메일이 아닙니다",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!TextUtil.isVaildUserName(binding.username.getText())){
                    Toast.makeText(RegisterActivity.this,"유효한 닉네임이 아닙니다",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!TextUtil.isVaildPassword(binding.password.getText())){
                    Toast.makeText(RegisterActivity.this,"유효한 비밀번호가 아닙니다",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    inputModel.setEmail(binding.email.getText().toString());
                    inputModel.setPassword(binding.password.getText().toString());
                    inputModel.setUsername(binding.username.getText().toString());
                }

                service.register(inputModel).enqueue(new Callback<RegisterOutputModel>() {
                    @Override
                    public void onResponse(Call<RegisterOutputModel> call, Response<RegisterOutputModel> response) {
                        if(response.isSuccessful()){
                            RegisterOutputModel result = response.body();
                            if(response.body().getCode().equals("success")){
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                UserIdManager.getInstance(RegisterActivity.this).saveUserId(response.body().getUserId());
                                finish();
                            }
//                            code == error
                            else{
                                Toast.makeText(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterOutputModel> call, Throwable t) {

                    }
                });
            }
        });
    }
}