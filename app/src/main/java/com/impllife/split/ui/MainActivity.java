package com.impllife.split.ui;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static MainActivity getInstance() {
        return instance;
    }
    private static MainActivity instance;

    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate");

        //Looks like a fix problem with inet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //hide top bar with app name
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);
        instance = this;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        findViewById(R.id.btn_back).setOnClickListener(v -> navController.navigateUp());
    }

    public void setHeadTitle(String title) {
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }
    public void hideHead() {
        findViewById(R.id.head).setVisibility(View.GONE);
    }
    public void showHead() {
        findViewById(R.id.head).setVisibility(View.VISIBLE);
    }
    public void setInfoAction(Runnable exe) {
        exe.run();
    }
}