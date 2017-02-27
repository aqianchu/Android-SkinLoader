package com.sunny.library.attr;

public class AttrFactory {

	public static final String BACKGROUND = "background";
	public static final String TEXT_COLOR = "textColor";
	public static final String LIST_SELECTOR = "listSelector";
	public static final String DIVIDER = "divider";
	public static final String BUTTON = "button";

	public static SkinAttr get(String attrName, int attrValueId, String attrEntryName, String attrEntryType) {

		SkinAttr mSkinAttr = null;

		if (BACKGROUND.equals(attrName)) {
			mSkinAttr = new BackgroundAttr();
		} else if (TEXT_COLOR.equals(attrName)) {
			mSkinAttr = new TextColorAttr();
		}else if (BUTTON.equals(attrName)) {
			mSkinAttr = new ButtonAttr();
		} else if (LIST_SELECTOR.equals(attrName)) {
			mSkinAttr = new ListSelectorAttr();
		} else if (DIVIDER.equals(attrName)) {
			mSkinAttr = new DividerAttr();
		} else {
			return null;
		}

		mSkinAttr.attrName = attrName;
		mSkinAttr.attrValueId = attrValueId;
		mSkinAttr.attrEntryName = attrEntryName;
		mSkinAttr.attrEntryType = attrEntryType;
		return mSkinAttr;
	}

	public static boolean isSupportedAttr(String attrName) {
		if (BACKGROUND.equals(attrName)) {
			return true;
		}
		if (BUTTON.equals(attrName)) {
			return true;
		}
		if (TEXT_COLOR.equals(attrName)) {
			return true;
		}
		if (LIST_SELECTOR.equals(attrName)) {
			return true;
		}
		if (DIVIDER.equals(attrName)) {
			return true;
		}
		return false;
	}
}
