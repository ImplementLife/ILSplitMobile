package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.gridlayout.widget.GridLayout;

import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.view.BtnDate;

import java.util.Calendar;
import java.util.Date;

import static android.view.Gravity.FILL_HORIZONTAL;

public class TransactionNewFragment extends NavFragment {
    private Date dateCreate;

    private BtnDate btnToday;
    private BtnDate btnYesterday;
    private BtnDate btnSelectDate;
    private GridLayout grid;

    private EditText etSum;
    private EditText etDscr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_new, container, false);
        setNavTitle("New transaction");

        init(inflater, view);

        Calendar calendar = Calendar.getInstance();

        btnToday.setName("Today");
        btnToday.setDate(calendar.getTime());
        btnToday.setOnClickListener(v -> {
            btnToday.select();
            btnYesterday.unselect();
            btnSelectDate.unselect();

            dateCreate = btnToday.getDate();
        });

        dateCreate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        btnYesterday.setName("Yesterday");
        btnYesterday.setDate(calendar.getTime());
        btnYesterday.setOnClickListener(v -> {
            btnYesterday.select();
            btnToday.unselect();
            btnSelectDate.unselect();

            dateCreate = btnYesterday.getDate();
        });

        btnSelectDate.setName("Select");
        initDateSelect();
        btnSelectDate.setOnClickListener(v -> {
            btnSelectDate.select();
            btnToday.unselect();
            btnYesterday.unselect();

            navController.navigate(R.id.action_fragment_transaction_new_to_fragment_date_select);
        });


        setBtn(btnToday.getRoot(), 0, 0);
        setBtn(btnYesterday.getRoot(), 0, 1);
        setBtn(btnSelectDate.getRoot(), 0, 2);

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            runAsync(() -> {
                save();
                view.post(() -> navController.navigateUp());
            });
        });

        return view;
    }

    private void init(LayoutInflater inflater, View view) {
        grid = view.findViewById(R.id.group_btn);
        btnToday = new BtnDate(inflater, grid);
        btnYesterday = new BtnDate(inflater, grid);
        btnSelectDate = new BtnDate(inflater, grid);
        etSum = view.findViewById(R.id.field_sum);
        etDscr = view.findViewById(R.id.et_dscr);
    }

    private void save() {
        Transaction transaction = new Transaction();
        transaction.setSum(etSum.getText().toString());
        transaction.setDateCreate(dateCreate);
        transaction.setDescription(etDscr.getText().toString());

        DataService.getInstance().insert(transaction);
    }

    private void initDateSelect() {
        long dateCreateAsLong = 0;
        if (getArguments() != null) {
            dateCreateAsLong = getArguments().getLong("date");
        }
        if (dateCreateAsLong != 0) {
            this.dateCreate = new Date(dateCreateAsLong);
            this.btnSelectDate.setDate(dateCreate);
        } else {
            this.dateCreate = new Date();
            this.btnSelectDate.setDate(null);
        }
    }

    private void setBtn(View view, int row, int col) {
        androidx.gridlayout.widget.GridLayout.LayoutParams layoutParams = new androidx.gridlayout.widget.GridLayout.LayoutParams(
            androidx.gridlayout.widget.GridLayout.spec(row, 1f),
            androidx.gridlayout.widget.GridLayout.spec(col, 1f));
        layoutParams.setGravity(FILL_HORIZONTAL);
        layoutParams.height = 100;

        grid.addView(view, layoutParams);
    }
}