package com.kil.mychatroom.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kil.mychatroom.Commands.ApiContractor;
import com.kil.mychatroom.Commands.userSignup;
import com.kil.mychatroom.Model.User;
import com.kil.mychatroom.ModelView.UserModel;
import com.kil.mychatroom.R;
import com.kil.mychatroom.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity implements ApiContractor.RegistrationSuccess,ApiContractor.AddUser {
    EditText et_username,et_password;
    Button btn_signup;
    ActivitySignupBinding binding;
    UserModel user;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(Signup.this,R.layout.activity_signup);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setIndeterminate(true);


        user=new UserModel(new User("Email", "Password"));

        binding.setUserRegister(user);
        binding.setUserSignupActivity(Signup.this);
        binding.setUserRegisterEvent(new userSignup() {
            @Override
            public void onClick() {
                Toast.makeText(Signup.this,"Clicked",Toast.LENGTH_SHORT).show();
                Log.d("DETAILS",binding.getUserRegister().getEmail()+" And "+
                binding.getUserRegister().getPassword());
                //Register User

                user.registerUser(Signup.this,binding.getUserRegister().getEmail()
                ,binding.getUserRegister().getPassword());
                mProgressDialog.show();
            }

//            @Override
//            public void goToLogin() {
//                Intent i=new Intent(Signup.this,Login.class);
//                startActivity(i);
//            }
        });



    }
    public void nexScreen(){

                Intent i=new Intent(Signup.this,Login.class);
                startActivity(i);

    }
    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {

        mProgressDialog.setMessage("Adding uer to database");
        user.addUserToDatabase(Signup.this,firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddUserSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(this, "Process Complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddUserFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show();

    }
}
