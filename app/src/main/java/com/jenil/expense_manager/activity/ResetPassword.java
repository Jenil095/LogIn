package com.jenil.expense_manager.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputLayout;
import com.jenil.expense_manager.DbHelper;
import com.jenil.expense_manager.R;

public class ResetPassword extends AppCompatActivity {

    TextView username;
    ImageView image;
    TextView logo_text;
    TextInputLayout password, repassword;
    Button call_login;
    DbHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        username = findViewById(R.id.user_text);
        image = findViewById(R.id.forgotpass_img);
        logo_text = findViewById(R.id.password_text);
        password = findViewById(R.id.password_reset);
        repassword = findViewById(R.id.repassword_reset);
        call_login = findViewById(R.id.login_screen);
        Db = new DbHelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        call_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getEditText().getText().toString();
                String repass = repassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)){
                    Toast.makeText(ResetPassword.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }else {
                    if (pass.equals(repass)) {
                        Boolean checkpasswordupdate = Db.updatepassword(user, pass);
                        if (checkpasswordupdate == true) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            Toast.makeText(ResetPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();

                            Pair[] pairs = new Pair[6];
                            pairs[0] = new Pair<View, String>(image, "logo_image");
                            pairs[1] = new Pair<View, String>(logo_text, "logo_text");
                            pairs[2] = new Pair<View, String>(username, "username_tran");
                            pairs[3] = new Pair<View, String>(password, "username_tran");
                            pairs[4] = new Pair<View, String>(repassword, "username_tran");
                            pairs[5] = new Pair<View, String>(call_login, "button_tran");

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ResetPassword.this, pairs);
                                startActivity(intent, options.toBundle());
                            }

                        } else {
                            Toast.makeText(ResetPassword.this, "Password Not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetPassword.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}