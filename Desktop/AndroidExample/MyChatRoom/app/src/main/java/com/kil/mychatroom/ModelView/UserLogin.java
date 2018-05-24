package com.kil.mychatroom.ModelView;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kil.mychatroom.Activities.Login;
import com.kil.mychatroom.Model.User;
import com.kil.mychatroom.R;

import java.util.Observable;

import static android.content.ContentValues.TAG;

public class UserLogin extends BaseObservable {
    public String email;
    public String Password;
    public String email_Hint;
    public String password_Hint;

    public UserLogin() {

    }

    public UserLogin(User user) {
        this.email_Hint = user.emailhint;
        this.password_Hint = user.passwordhint;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(R.id.et_emailIdSignin);
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
        notifyPropertyChanged(R.id.et_passwordSignin);
    }

    public String getEmailHint() {
        return email_Hint;
    }

    public void setEmailHint(String emailHint) {
        this.email_Hint = emailHint;
    }

    public String getPasswordHint() {
        return password_Hint;
    }

    public void setPasswordHint(String passwordHint) {
        this.password_Hint = passwordHint;
    }


    public void loginUser(final Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "performFirebaseLogin:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            ((Login)activity).onSuccess();
                        } else {
                            ((Login)activity).onFailure(task.getException().getMessage());
                        }
                    }
                });

    }
}
