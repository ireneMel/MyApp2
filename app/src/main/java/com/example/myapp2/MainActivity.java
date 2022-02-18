package com.example.myapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.example.myapp2.databinding.ContentMainBinding;

public class MainActivity extends BaseActivity {
    @Override
    protected int currentId() {
        return R.id.nav_home;
    }

    private ContentMainBinding binding;
    @Override
    protected ViewBinding binding() {
        return binding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ContentMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
    }


}