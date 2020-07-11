package com.chat.prs;

import android.app.ActionBar;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

public class CustomActivity extends FragmentActivity implements View.OnClickListener {
    public static final TouchEffect TOUCH = new TouchEffect();

    @Override
    public void onClick(View view) {

    }

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(layoutId);
        setupActionBar();
    }

    protected void setupActionBar(){
        final ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.icon);
        actionBar.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.actionbar
        ));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public View setTouchNClick(int id) {
        View v = setClick(id);
        if (v != null) {
            v.setOnTouchListener(TOUCH);
        }
        return v;
    }

    public View setClick(int id) {
        View v = findViewById(id);
        if (v != null) {
            v.setOnClickListener(this);
        }
        return v;
    }
}
