package com.sunny.library.attr;

import android.view.View;

public abstract class SkinAttr {
	protected static final String TYPE_NAME_COLOR = "color";
	protected static final String TYPE_NAME_DRAWABLE = "drawable";

	public String attrName;

	public int attrValueId;

	public String attrEntryName;

	public String attrEntryType;

	public abstract void apply(View view);

	@Override
	public String toString() {
		return "SkinAttr [attrName=" + attrName + ", attrValueId=" + attrValueId + ", attrEntryName=" + attrEntryName
				+ ", attrEntryType=" + attrEntryType + "]";
	}
}
