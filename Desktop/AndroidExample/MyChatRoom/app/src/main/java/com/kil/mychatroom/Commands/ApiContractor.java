package com.kil.mychatroom.Commands;

import com.google.firebase.auth.FirebaseUser;
import com.kil.mychatroom.ModelView.UserListModel;

import java.util.List;

public interface ApiContractor {
    interface View{
        void onSuccess ();
        void onFailure (String msg);
    }

    interface RegistrationSuccess{
        void onRegistrationSuccess(FirebaseUser firebaseUser);

        void onRegistrationFailure(String message);
    }
    interface AddUser{
        void onAddUserSuccess(String message);
        void onAddUserFailure(String message);
    }
    interface getUsers{
        void onGetUserSuccess(List<UserListModel> allUser);
        void onGetUserFailure(String msg);
    }
}
