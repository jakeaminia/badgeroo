package com.cs407.badgerooproject.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cs407.badgerooproject.Login.LoginActivity;
import com.cs407.badgerooproject.R;
import com.cs407.badgerooproject.databinding.ActivityFindRoommatesBinding;

// Fragments: FindRoommatesFragment, SettingsFragment, EditProfileFragment, MessagesListFragment, MessageFragment

public class FindRoommatesActivity extends AppCompatActivity {

    ActivityFindRoommatesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindRoommatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FindRoommatesFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                replaceFragment(new FindRoommatesFragment());
            } else if (itemId == R.id.nav_messages) {
                replaceFragment(new MessagesListFragment());
            } else if (itemId == R.id.nav_settings) {
                replaceFragment(new SettingsFragment());
            } else if (itemId == R.id.nav_edit_profile) {
                replaceFragment(new EditProfileFragment());
            } else if (itemId == R.id.logout) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            else {
                return false;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.HomeFragmentContainer, fragment).commit();
    }
}