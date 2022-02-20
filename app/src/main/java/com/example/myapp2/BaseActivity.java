package com.example.myapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;

import com.example.myapp2.async.AsyncTaskLoaderActivity;
import com.example.myapp2.broadcast.BroadcastActivity;
import com.example.myapp2.databinding.ActivityBaseBinding;

public abstract class BaseActivity extends AppCompatActivity {
    private ActivityBaseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.include.addView(binding().getRoot());

        setSupportActionBar(binding.toolbar);

        DrawerLayout drawer = binding.drawerLayoutBroadcast;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.broadcast, R.string.action_settings);
        drawer.setDrawerListener(actionBarDrawerToggle);
        drawer.post(actionBarDrawerToggle::syncState);

        binding.navView.setNavigationItemSelectedListener(item -> {
            onNavigationSelect(item.getItemId(), drawer);
            return true;
        });
    }

    private void onNavigationSelect(@IdRes int id, DrawerLayout drawer) {
        if (id == currentId()) {
            drawer.close();
        } else {
            Intent intent;
            switch (id) {
                case R.id.nav_home:
                    intent = new Intent(this, MainActivity.class);
                    break;
                case R.id.nav_help:
                    intent = new Intent(this, HelpActivity.class);
                    break;
                case R.id.nav_asyncTask:
                    intent = new Intent(this, AsyncTaskLoaderActivity.class);
                    break;
                case R.id.nav_broadcast:
                    intent = new Intent(this, BroadcastActivity.class);
                    break;
                default:
                    throw new IllegalStateException("Undefined resource");
            }
            startActivity(intent);
        }
    }

    protected abstract @IdRes
    int currentId();

    protected abstract ViewBinding binding();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, HelpActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
