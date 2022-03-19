package com.jenil.expense_manager.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.jenil.expense_manager.DbHelper;
import com.jenil.expense_manager.R;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword, regRepassword;
    Button regBtn, regToLoginBtn;
    ImageView image;
    TextView logoText, sloganText;
    DbHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        regRepassword = findViewById(R.id.reg_repassword);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.login_screen);
        Db = new DbHelper(this);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = regUsername.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String repassword = regRepassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword))
                    Toast.makeText(SignUp.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(repassword)) {
                        Boolean checkuser = Db.checkusername(username);
                        if (checkuser == false) {
                            Boolean insert = Db.insertData(username, password);
                            if (insert == true) {
                                Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Password Are Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(regUsername, "username_tran");
                pairs[4] = new Pair<View, String>(regPassword, "password_tran");
                pairs[5] = new Pair<View, String>(regBtn, "button_tran");
                pairs[6] = new Pair<View, String>(regToLoginBtn, "login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}