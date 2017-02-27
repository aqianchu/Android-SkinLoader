package com.sunny.library.attr;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.sunny.library.manager.SkinManager;

public class BackgroundAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (TYPE_NAME_COLOR.equals(attrEntryType)) {
            view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueId));
        } else if (TYPE_NAME_DRAWABLE.equals(attrEntryType)) {
            Drawable bg = SkinManager.getInstance().getDrawable(attrValueId);
            view.setBackgroundDrawable(bg);
        }
    }

}
