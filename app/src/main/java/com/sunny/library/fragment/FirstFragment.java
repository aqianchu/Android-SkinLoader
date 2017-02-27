package com.sunny.library.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunny.library.R;
import com.sunny.library.attr.AttrFactory;
import com.sunny.library.attr.DynamicAttr;
import com.sunny.library.base.BaseFragment;
import com.sunny.library.manager.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends BaseFragment {


    private LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layout = (LinearLayout) view.findViewById(R.id.layout);
        addDynamicView();
    }

    private void addDynamicView() {
        TextView textView = new TextView(getActivity());
        textView.setText("this is fragment dynamic textview");
        textView.setTextColor(SkinManager.getInstance().getColor(R.color.text_default));
        layout.addView(textView);
        List<DynamicAttr> dynamicAttrs = new ArrayList<>();
        dynamicAttrs.add(new DynamicAttr(AttrFactory.TEXT_COLOR, R.color.text_default));
        dynamicAddView(textView, dynamicAttrs);
    }


}
