package com.sunny.library;

import android.app.Application;

import com.sunny.library.manager.SkinManager;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		SkinManager.getInstance().init(this);
		SkinManager.getInstance().loadSkin();
		
	}

}
