package com.chat.prs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chat.prs.databinding.LoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends CustomActivity {
    LoginBinding b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = LoginBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        setTouchNClick(b.LOGIN.getId());
        setTouchNClick(b.REG.getId());
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        if (view.getId() == b.REG.getId()) {
            startActivityForResult(new Intent(this, Register.class), 10);

        } else {
            final String u = b.EDITUSERNAME.getText().toString();
            String p = b.EDITPASSWORD.getText().toString();
            if (u.length() == 0 || p.length() == 0) {
                // show dialog of empty
                Toast.makeText(this, "cant be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Wait..");
            pd.setCancelable(false);
            pd.show();

            ParseUser.logInInBackground(
                    u,
                    p,
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser pu, ParseException e) {
                            pd.dismiss();

                            if (pu != null) {
                                MainActivity.user = pu;
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, "error login "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 & resultCode == RESULT_OK) {
            finish();
        }
    }
}
