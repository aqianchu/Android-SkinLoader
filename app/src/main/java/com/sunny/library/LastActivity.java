package com.sunny.library;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sunny.library.base.BaseActivity;
import com.sunny.library.fragment.ThirdFragment;

public class LastActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        initFragment();
    }

    private void initFragment() {

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.layout, new ThirdFragment());
        tx.commit();

    }


}
