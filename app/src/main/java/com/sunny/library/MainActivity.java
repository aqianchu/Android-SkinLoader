package com.sunny.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sunny.library.base.BaseActivity;
import com.sunny.library.fragment.FirstFragment;

public class MainActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    public void startNext(View v) {
        startActivity(new Intent(this, SettingActivity.class));
    }


    private void initFragment() {

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.layout, new FirstFragment());
        tx.commit();

    }
}
