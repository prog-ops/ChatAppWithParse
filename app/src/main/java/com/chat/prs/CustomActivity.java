package com.chat.prs;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

// in tutorial this activity is not declared in manifest
//public class CustomActivity extends FragmentActivity implements View.OnClickListener {
public class CustomActivity extends AppCompatActivity implements View.OnClickListener {
    //* APPLY THIS CONSTANS AS TOUCHLISTENER FOR VIEWS TO PROVIDE ALPHA TOUCH EFFECT
    // THE VIEW MUST HAVE A NON TRANSPARENT BACKGROUND
    public static final TouchEffect TOUCH = new TouchEffect();

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(layoutId);
        setupActionBar();
    }

    protected void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
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

    //* SETS THE TOUCH AND CLICKLISTENER FOR A VIEW WITH GIVEN ID
    public View setTouchNClick(int id) {
        View v = setClick(id);
        if (v != null) {
            v.setOnTouchListener(TOUCH);
        }
        return v;
    }

    //* SETS CLICKLISTENER FOR A VIEW WITH GIVEN ID
    // return the view on which listener is applied
    public View setClick(int id) {
        View v = findViewById(id);
        if (v != null) {
            v.setOnClickListener(this);
        }
        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
