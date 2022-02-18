package com.example.myapp2.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapp2.R;
import com.example.myapp2.databinding.ActivityBroadcastBinding;
import com.example.myapp2.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class BroadcastActivity extends AppCompatActivity {

    private final MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();
    private ActivityBroadcastBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        this.registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}

