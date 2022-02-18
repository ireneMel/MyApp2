package com.example.myapp2.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewbinding.ViewBinding;

import com.example.myapp2.BaseActivity;
import com.example.myapp2.HelpActivity;
import com.example.myapp2.MainActivity;
import com.example.myapp2.R;
import com.example.myapp2.async.AsyncTaskLoaderActivity;
import com.example.myapp2.databinding.ActivityBroadcastBinding;
import com.example.myapp2.databinding.ContentBroadcastBinding;
import com.google.android.material.navigation.NavigationView;

public class BroadcastActivity extends BaseActivity {
    private final MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();

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
    protected int currentId() {
        return R.id.nav_broadcast;
    }

    @Override
    protected ViewBinding binding() {
        return ContentBroadcastBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}

