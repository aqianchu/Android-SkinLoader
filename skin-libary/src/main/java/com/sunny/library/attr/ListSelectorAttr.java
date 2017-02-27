package com.sunny.library.attr;

import android.view.View;
import android.widget.AbsListView;

import com.sunny.library.manager.SkinManager;

public class ListSelectorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		AbsListView tv = (AbsListView) view;

		if (TYPE_NAME_COLOR.equals(attrEntryType)) {
			tv.setSelector(SkinManager.getInstance().getColor(attrValueId));
		} else if (TYPE_NAME_DRAWABLE.equals(attrEntryType)) {
			tv.setSelector(SkinManager.getInstance().getDrawable(attrValueId));
		}
	}

}
