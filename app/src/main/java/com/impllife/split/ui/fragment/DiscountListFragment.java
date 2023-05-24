package com.impllife.split.ui.fragment;

import android.widget.GridView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.ListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;

import java.util.Arrays;

public class DiscountListFragment extends NavFragment {
    public DiscountListFragment() {
        super(R.layout.fragment_discount_list, "Discount cards");
    }

    @Override
    protected void init() {
        ListAdapter<String> adapter = new ListAdapter<>(Arrays.asList("Varus", "+"), (data, parent) -> {
            TextView view = new TextView(getContext());
            view.setText(data);
            view.setOnClickListener(v -> navigate(R.id.fragment_discount));
            return view;
        });
        GridView grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);
    }

}