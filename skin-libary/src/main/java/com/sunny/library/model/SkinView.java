package com.sunny.library.model;

import android.view.View;

import com.sunny.library.attr.SkinAttr;
import com.sunny.library.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinView {

    public View view;

    public List<SkinAttr> attrs;

    public SkinView() {
        attrs = new ArrayList<>();
    }

    public void apply() {
        if (view != null && !ListUtils.isEmpty(attrs)) {
            for (SkinAttr attr : attrs) {
                attr.apply(view);
            }
        }
    }

    public void clean() {
        if (ListUtils.isEmpty(attrs)) {
            return;
        }
        for (SkinAttr at : attrs) {
            at = null;
        }
    }

    @Override
    public String toString() {
        return "SkinView{" +
                "view=" + view +
                '}';
    }
}
