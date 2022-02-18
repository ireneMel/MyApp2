package com.example.myapp2;

import androidx.viewbinding.ViewBinding;

import com.example.myapp2.databinding.ActivityHelpBinding;

public class HelpActivity extends BaseActivity {
    @Override
    protected int currentId() {
        return R.id.nav_help;
    }

    @Override
    protected ViewBinding binding() {
        return ActivityHelpBinding.inflate(getLayoutInflater());
    }
}