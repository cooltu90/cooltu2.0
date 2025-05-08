package com.codingtu.cooltu.lib4a.ui.recyclerview;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingtu.cooltu.lib4j.function.base.Supplier;

public class HorizontalLinearConfig implements Config {
    @Override
    public void config(Context context, RecyclerView rv, Supplier supplier) {
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }
}
