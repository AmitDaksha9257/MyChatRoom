package com.kil.mychatroom.ModelView;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kil.mychatroom.Activities.Login;
import com.kil.mychatroom.Activities.Signup;
import com.kil.mychatroom.Commands.ApiContractor;
import com.kil.mychatroom.Constants;
import com.kil.mychatroom.MainActivity;
import com.kil.mychatroom.Model.User;
import com.kil.mychatroom.R;

public class UserModel extends BaseObservable{

    public String email;
    public String password;
    public String email_Hint;
    public String password_Hint;
    public UserModel(){

    }
    public UserModel(User user){
        this.email_Hint=user.emailhint;
        this.password_Hint=user.passwordhint;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(R.id.et_emailId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(R.id.et_password);
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



    public void registerUser(final Activity activity, String email, String password){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", String.valueOf(task.isSuccessful()));

                        if (!task.isSuccessful()) {


                        }else{
                            addUserToDatabase(activity,task.getResult().getUser());
                            ((Signup)activity).onRegistrationSuccess(task.getResult().getUser());

                        }
                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ((Signup)activity).onRegistrationFailure(e.getMessage());
            }
        });
    }

    public void addUserToDatabase(final Activity activity,FirebaseUser firebaseUser){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        User user=new User(firebaseUser.getUid(),firebaseUser.getEmail(),Constants.FCM_TOKEN);
        database.child(Constants.DB_USER).child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("Success","Success");
                            ((Signup)activity).onAddUserSuccess("User Successfully added");
                        }else{
                            Log.d("Failure","Failure");
                            ((Signup)activity).onAddUserFailure("User Failed to added");
                        }
                    }
                });
    }

}
