package com.sunny.library.attr;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.sunny.library.manager.SkinManager;

public class DividerAttr extends SkinAttr {

	public int dividerHeight = 1;
	
	@Override
	public void apply(View view) {
		if(view instanceof ListView){
			ListView tv = (ListView)view;
			if(TYPE_NAME_COLOR.equals(attrEntryType)){
				int color = SkinManager.getInstance().getColor(attrValueId);
				ColorDrawable sage = new ColorDrawable(color);
				tv.setDivider(sage);
				tv.setDividerHeight(dividerHeight);
			}else if(TYPE_NAME_DRAWABLE.equals(attrEntryType)){
				tv.setDivider(SkinManager.getInstance().getDrawable(attrValueId));
			}
		}
	}

}
