package com.mariamnarouz.firebaseapp.ui.chat.add_chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mariamnarouz.firebaseapp.R;
import com.mariamnarouz.firebaseapp.ui.chat.add_chat.presenter.AddChatPresenter;
import com.mariamnarouz.firebaseapp.ui.chat.chat_details.ChatDetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by Mariam.Narouz on 12/27/2017.
 */

@RequiresPresenter(AddChatPresenter.class)
public class AddChatActivity extends NucleusAppCompatActivity<AddChatPresenter> {


    @BindView(R.id.activity_add_chat_btn_create_chat)
    Button activity_add_chat_btn_create_chat;

    @BindView(R.id.activity_add_chat_spn_choose_contact)
    Spinner activity_add_chat_spn_choose_contact;

    ProgressDialog progressDialog;
    ArrayAdapter<String> spinnerArrayAdapter;
    String userId = "" , userName = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        ButterKnife.bind(this);

        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_add_chat_spn_choose_contact.setAdapter(spinnerArrayAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

    }



    public void updateView(AddChatViewModel viewModel) {
     if (viewModel.isLoading()){
                progressDialog.show();
            }else {
                progressDialog.hide();
            }
            if (viewModel.isNotifyDataChanged()){
                if (viewModel.getData().size() > 0 ) {
                    setData(viewModel.getData());
                    activity_add_chat_btn_create_chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddChatActivity.this, ChatDetailsActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("userName",userName);
                            startActivity(intent);
                        }
                    });
                }
                Log.i(" activity", "size= " + viewModel.getData().size());
                getPresenter().setNotifyDataChanged(false);
            }

    }

    public void setData(ArrayList<String> data) {
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_add_chat_spn_choose_contact.setAdapter(spinnerArrayAdapter);
        userId = getPresenter().getUserId(0);
        userName = getPresenter().getUserName(0);
        activity_add_chat_spn_choose_contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userId = getPresenter().getUserId(position);
                userName = getPresenter().getUserName(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog = null;
    }
}
