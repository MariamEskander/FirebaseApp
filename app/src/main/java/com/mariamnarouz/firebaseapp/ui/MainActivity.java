package com.mariamnarouz.firebaseapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mariamnarouz.firebaseapp.CheckForMessages;
import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.data.Singleton;
import com.mariamnarouz.firebaseapp.ui.chat.add_chat.AddChatActivity;
import com.mariamnarouz.firebaseapp.ui.chat.chats.ChatsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.go_to_chat)
    Button go_to_chat;


    @BindView(R.id.sign_out_button)
    TextView sign_out_button;

    private FirebaseAuth mAuth;
    private String TAG = MainActivity.class.getName();
    private static final String ACTION_GET_MESSAGES = "action.GET_MESSAGES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, CheckForMessages.class);
        intent.setAction(ACTION_GET_MESSAGES);
        startService(intent);

        mAuth = FirebaseAuth.getInstance();



        go_to_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatsActivity.class));
            }
        });

        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "logout:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    Singleton.getInstance(MainActivity.this).logout();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "logout:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }



}
