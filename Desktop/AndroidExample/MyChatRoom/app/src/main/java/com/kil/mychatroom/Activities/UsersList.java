package com.kil.mychatroom.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kil.mychatroom.Adapters.AdapterUserList;
import com.kil.mychatroom.Commands.ApiContractor;
import com.kil.mychatroom.Commands.RecyclerTouchListener;
import com.kil.mychatroom.Constants;
import com.kil.mychatroom.ModelView.UserListModel;
import com.kil.mychatroom.R;

import java.util.List;

public class UsersList extends AppCompatActivity implements ApiContractor.getUsers{
    RecyclerView recyclerView;
    String name[]={"amit@in.com","ram@in.com","sham@in.com"};
    AdapterUserList adapterAdapterUserList;

    List<UserListModel> userListModels;
    UserListModel users;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userslist);
        recyclerView=findViewById(R.id.recycler_view);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        users=new UserListModel();
        users.getAllUser(UsersList.this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(UsersList.this, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UsersList.this,ChatActivity.class);
                intent.putExtra(Constants.RECEVIER,adapterAdapterUserList.getUser(position).email);
                intent.putExtra(Constants.RECEVIER_UID,adapterAdapterUserList.getUser(position).uid);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    @Override
    public void onGetUserSuccess(List<UserListModel> allUser) {
        mProgressDialog.dismiss();
        adapterAdapterUserList =new AdapterUserList(allUser);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(UsersList.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterAdapterUserList);
    }

    @Override
    public void onGetUserFailure(String msg) {
        mProgressDialog.dismiss();
        Log.d("UserEmailAvailable","Success");
        Toast.makeText(this, "Failiure", Toast.LENGTH_SHORT).show();
    }
}
