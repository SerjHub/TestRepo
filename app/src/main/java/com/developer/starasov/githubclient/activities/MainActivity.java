package com.developer.starasov.githubclient.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.fragments.AccountFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(new AccountFragment(),R.id.containerMain);

    }



    public void showFragment(Fragment fragment,int resource){
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (GlobalValues.currentFragment != null)
            transaction.hide(GlobalValues.currentFragment);
        GlobalValues.currentFragment = fragment;
        transaction.add(resource,fragment).commit();
    }
}
