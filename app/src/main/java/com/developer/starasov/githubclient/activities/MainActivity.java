package com.developer.starasov.githubclient.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.developer.starasov.githubclient.fragments.RepositoryFragment;
import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.fragments.AccountFragment;

public class MainActivity extends AppCompatActivity {

     private TextView mainTitle;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTitle = findViewById(R.id.main_title);

        showFragment(new AccountFragment());
    }



    public void showFragment(Fragment fragment){
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (currentFragment != null)
            transaction.hide(currentFragment);
        currentFragment = fragment;
        transaction.add(R.id.containerMain,fragment).commit();
    }

    public void onBackPress(View view){
        if (currentFragment instanceof RepositoryFragment){
            showFragment(new AccountFragment());
        } else{
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(i);
        }
    }
    public void onExitAccount(View view){
        SharedPreferences preferences = getSharedPreferences(GlobalValues.PACKAGE_NAME,Context.MODE_PRIVATE);
        preferences.edit()
                .clear()
                .apply();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void setTitle(String text){
        mainTitle.setText(text);
    }

    @Override
    public void onBackPressed() {
        onBackPress(null);
    }
}
