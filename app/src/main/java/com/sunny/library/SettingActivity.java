package com.sunny.library;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.library.attr.AttrFactory;
import com.sunny.library.attr.DynamicAttr;
import com.sunny.library.base.BaseActivity;
import com.sunny.library.listener.OnLoaderListener;
import com.sunny.library.manager.SkinManager;
import com.sunny.library.utils.SkinConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity {
    private static final String SKIN_DIR = Environment.getExternalStorageDirectory() + File.separator + "skin-plugin.apk";

    private LinearLayout layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        layout = (LinearLayout) findViewById(R.id.layout);

        addDynamicView();
    }


    private void addDynamicView() {
        TextView textView = new TextView(this);
        textView.setText("this is dynamic textview");
        textView.setTextColor(SkinManager.getInstance().getColor(R.color.text_default));
        layout.addView(textView);
        List<DynamicAttr> dynamicAttrs = new ArrayList<>();
        dynamicAttrs.add(new DynamicAttr(AttrFactory.TEXT_COLOR, R.color.text_default));
        dynamicAddView(textView, dynamicAttrs);
    }

    public void startNight(View v) {
        SkinManager.getInstance().loadSkin(SkinConfig.SKIN_NIGHT);
    }

    public void startDay(View v) {
        SkinManager.getInstance().loadSkin(SkinConfig.SKIN_DEFAULT);
    }

    public void startNightPlugin(View v) {
        loadPlugin();
    }

    public void startNext(View v) {
        startActivity(new Intent(this, LastActivity.class));
    }

    public void startPrev(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void loadPlugin() {
        File skin = new File(SKIN_DIR);

        if (skin == null || !skin.exists()) {
            Toast.makeText(getApplicationContext(), "请检查" + SKIN_DIR + "是否存在", Toast.LENGTH_SHORT).show();
            return;
        }

        SkinManager.getInstance().loadSkin(skin.getAbsolutePath(), new OnLoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getApplicationContext(), "切换失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
