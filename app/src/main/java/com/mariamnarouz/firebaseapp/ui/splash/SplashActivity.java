package com.mariamnarouz.firebaseapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mariamnarouz.firebaseapp.ui.MainActivity;
import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.data.model.User;
import com.mariamnarouz.firebaseapp.ui.login.LoginActivity;

/**
 * Created by Mariam.Narouz on 12/26/2017.
 */

public class SplashActivity extends AppCompatActivity {



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        User currentUser = Singleton.getInstance(this).getUser();
        updateUI(currentUser);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    private void updateUI(User currentUser) {
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


}
