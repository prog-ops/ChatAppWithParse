package com.chat.prs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chat.prs.databinding.RegisterBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends CustomActivity {
    RegisterBinding b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = RegisterBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        setTouchNClick(b.REG.getId());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        String e = b.EDITEMAIL.getText().toString();
        String u = b.EDITUSERNAME.getText().toString();
        String p = b.EDITPASSWORD.getText().toString();

        if (u.length() == 0 | p.length() == 0 | e.length() == 0) {
            // Utils.showDialog(this, R.string.err_empty);
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Wait.. registering");
        pd.setCancelable(false);
        pd.show();

        final ParseUser pu = new ParseUser();
        pu.setEmail(e);
        pu.setUsername(u);
        pu.setPassword(p);
        pu.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                pd.dismiss();

                if (e == null) {
                    MainActivity.user = pu;
                    startActivity(new Intent(Register.this, MainActivity.class));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    // utils show dialog error signup
                    e.printStackTrace();
                    Toast.makeText(Register.this, "error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 & resultCode == RESULT_OK) {
            finish();
        }
    }
}
