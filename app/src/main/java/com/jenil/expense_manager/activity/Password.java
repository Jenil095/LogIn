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

public class Password extends AppCompatActivity {

    ImageView image;
    TextView logo_text;
    TextInputLayout username;
    Button reset;
    DbHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        image = findViewById(R.id.forgotpass_img);
        logo_text = findViewById(R.id.user_text);
        username = findViewById(R.id.username_reset);
        reset = findViewById(R.id.btn_reset);
        Db = new DbHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getEditText().getText().toString();

                if (TextUtils.isEmpty(user)){
                    Toast.makeText(Password.this, "All fields Required", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkuser = Db.checkusername(user);
                    if(checkuser == true){
                        Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
                        intent.putExtra("username", user);


                        Pair[] pairs = new Pair[4];
                        pairs[0] = new Pair<View, String>(image, "logo_image");
                        pairs[1] = new Pair<View, String>(logo_text, "logo_text");
                        pairs[2] = new Pair<View, String>(username, "username_tran");
                        pairs[3] = new Pair<View, String>(reset, "button_tran");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Password.this, pairs);
                            startActivity(intent, options.toBundle());
                        }

                    }else{
                        Toast.makeText(Password.this, "User Does Not Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}