package com.developer.starasov.githubclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.starasov.githubclient.Api.ApiListener;
import com.developer.starasov.githubclient.helpers.ApplicationController;
import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.models.AccountModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class LoginActivity extends AppCompatActivity {

    private EditText login,password;
    private Button logBtn;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginEdit);
        password = findViewById(R.id.passwordEdit);

        logBtn = findViewById(R.id.logIn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("sgaaeloi666");
                sendLoginData(login.getText().toString() , password.getText().toString());
            }
        });

        preferences = getPreferences(MODE_PRIVATE);
        tryLogin();


    }



    private void sendLoginData(String name, String pass){

        ApplicationController.getHttpClient().loginGitHub(name, pass, new ApiListener() {
            @Override
            public void onResponse(JsonElement data, int code) {
                if (code == 200){
                    GlobalValues.accountJsonData = data.getAsJsonObject();
                    GlobalValues.accountModel = new Gson().fromJson(data.getAsJsonObject(), AccountModel.class);
                    Log.i("FindPAth",data.toString());
                    preferences.edit()
                            .putString("login",login.getText().toString())
                            .putString("password",password.getText().toString())
                            .apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
//                    login.setText("");
//                    password.setText("");

                }
                else {
                    Toast.makeText(LoginActivity.this, "Логин или пароль были изменены либо введены неправильно, попробуйте ввести заново", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(LoginActivity.this, "Problems with internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tryLogin(){
        final String login = preferences.getString("login","null");
        final String pass = preferences.getString("password","null");
        if (!login.equals("null") && !pass.equals("null")){
            this.login.setText(login);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendLoginData(login,pass);
                }
            },250);

        }
    }


}
