package com.kil.mychatroom.ModelView;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kil.mychatroom.Activities.UsersList;
import com.kil.mychatroom.Constants;
import com.kil.mychatroom.Model.User;
import com.kil.mychatroom.Model.Users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserListModel {

    public String email;
    public String lastMessage;
    public int notifCount;
    public String uid;

    public UserListModel(Users users) {
        this.email=users.emailId;
        this.lastMessage=users.lastMessage;
        this.notifCount=users.notifCount;
    }

    public UserListModel() {
    }


    public String getEmailId() {
        return email;
    }

    public void setEmailId(String emailId) {
        this.email = emailId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getNotifCount() {
        return notifCount;
    }

    public void setNotifCount(int notifCount) {
        this.notifCount = notifCount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void getAllUser(final Activity activity){
        FirebaseDatabase.getInstance().getReference().child(Constants.DB_USER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
               List<UserListModel> users = new ArrayList<>();

                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    UserListModel user = dataSnapshotChild.getValue(UserListModel.class);
                    if (!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        users.add(user);
                   }
               }
                ((UsersList)activity).onGetUserSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               ((UsersList)activity).onGetUserFailure("Failiure");
            }
        });
    }
}
