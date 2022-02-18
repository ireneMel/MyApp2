package com.example.myapp2.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapp2.HelpActivity;
import com.example.myapp2.MainActivity;
import com.example.myapp2.R;
import com.example.myapp2.async.AsyncTaskLoaderActivity;
import com.example.myapp2.databinding.ActivityBroadcastBinding;
import com.google.android.material.navigation.NavigationView;

public class BroadcastActivity extends AppCompatActivity {

    private final MyBroadcastReceiver mReceiver = new MyBroadcastReceiver();
    private ActivityBroadcastBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBroadcastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        DrawerLayout drawer = binding.drawerLayoutBroadcast;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.broadcast, R.string.action_settings);
        drawer.setDrawerListener(actionBarDrawerToggle);
        drawer.post(actionBarDrawerToggle::syncState);

        Intent intent = new Intent(this, HelpActivity.class);
        Intent intent_at = new Intent(this, AsyncTaskLoaderActivity.class);
        Intent intent_home = new Intent(this, MainActivity.class);
        binding.navView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home)
                startActivity(intent_home);
            if(item.getItemId() == R.id.nav_help)
                startActivity(intent);
            if(item.getItemId() == R.id.nav_asyncTask)
                startActivity(intent_at);
            if(item.getItemId() == R.id.nav_broadcast)
                drawer.close();
            return true;
        });

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

