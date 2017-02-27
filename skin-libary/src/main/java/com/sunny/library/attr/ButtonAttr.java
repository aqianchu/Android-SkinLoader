package com.sunny.library.attr;

import com.sunny.library.manager.SkinManager;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;

/**
 * 
 * 用于单复选按钮
 *
 */

public class ButtonAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof CompoundButton){
			CompoundButton btn=(CompoundButton) view;
			Drawable bg = SkinManager.getInstance().getDrawable(attrValueId);
			btn.setButtonDrawable(bg);
		}
	}

}
