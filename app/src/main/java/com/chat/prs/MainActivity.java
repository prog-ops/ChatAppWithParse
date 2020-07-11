package com.chat.prs;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.prs.databinding.UserListBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

//* MainActivity is UserList
public class MainActivity extends CustomActivity {
    UserListBinding b;
    private ArrayList<ParseUser> uList;
    public static ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = UserListBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

//        ActionBar actionBar = getActionBar();
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }*/
//        getActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        updateUserStatus(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserStatus(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserList();
    }

    private void updateUserStatus(boolean online) {
        user.put("online", online);
        user.saveEventually();
    }

    private void loadUserList() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Loading..");
        pd.setCancelable(false);
        pd.show();

        ParseUser.getQuery().whereNotEqualTo(
                "username",
                user.getUsername()
        )
                .findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        pd.dismiss();

                        if (objects != null) {

                            if (objects.size() == 0) {
                                Toast.makeText(MainActivity.this, "No user found", Toast.LENGTH_SHORT).show();

                            } else {

                                uList = new ArrayList<>(objects);
//                                b.RVUSERLIST.setAdapter(new UserAdapterRv());
                                b.LIST.setAdapter(new UserAdapter());
                                b.LIST.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                        startActivity(new Intent(
                                                MainActivity.this, Chat.class)
                                                .putExtra(
                                                        Const.EXTRA_DATA,
                                                        uList.get(pos).getUsername()));
                                    }
                                });
                            }

                        } else {

                            // utils show dialog
                            e.printStackTrace();

//                                String s = e.getMessage();
//                                Toast.makeText(MainActivity.this, "error user "+s, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /**
     * *
     */
    private class UserAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return uList.size();
        }

        @Override
        public ParseUser getItem(int i) {
            return uList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int pos, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.chat_item, null);
            }

            ParseUser c = getItem(pos);
            TextView label = (TextView) view;
            label.setText(c.getUsername());
            label.setCompoundDrawablesWithIntrinsicBounds(
                    c.getBoolean("online")
                            ?
                            R.drawable.online : R.drawable.offline,
                    0,
                    R.drawable.arrow,
                    0
            );
            return view;
        }

    }
}