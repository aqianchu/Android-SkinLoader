package com.sunny.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.sunny.library.attr.DynamicAttr;
import com.sunny.library.listener.SkinObserver;
import com.sunny.library.manager.SkinFactory;
import com.sunny.library.manager.SkinManager;

import java.lang.reflect.Field;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements SkinObserver {

    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(getLayoutInflater(), false);

            skinFactory = new SkinFactory();
            skinFactory.setAppCompatDelegate(getDelegate());
            LayoutInflaterCompat.setFactory(getLayoutInflater(), skinFactory);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        skinFactory.clean();
    }


    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        skinFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    @Override
    public void onThemeUpdate() {
        skinFactory.applySkin();
    }

}
