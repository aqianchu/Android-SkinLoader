package com.sunny.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.sunny.library.attr.DynamicAttr;

import java.util.List;


public class BaseFragment extends Fragment{


    private BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            baseActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            baseActivity = null;
        }
    }


    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater result = getActivity().getLayoutInflater();
        return result;
    }

    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if (baseActivity != null) {
            baseActivity.dynamicAddView(view, pDAttrs);
        }
    }
}
