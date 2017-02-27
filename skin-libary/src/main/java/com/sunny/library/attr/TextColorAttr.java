package com.sunny.library.attr;

import com.sunny.library.manager.SkinManager;

import android.view.View;
import android.widget.TextView;

public class TextColorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if (TYPE_NAME_COLOR.equals(attrEntryType)) {
				tv.setTextColor(SkinManager.getInstance().getColorStateList(attrValueId));
			}
		}
	}

}
