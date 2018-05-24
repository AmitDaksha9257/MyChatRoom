package com.kil.mychatroom.Activities;

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
import android.widget.TextView;
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
import com.kil.mychatroom.Model.User;
import com.kil.mychatroom.ModelView.UserListModel;
import com.kil.mychatroom.ModelView.UserLogin;
import com.kil.mychatroom.R;
import com.kil.mychatroom.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity implements ApiContractor.View {
    EditText et_email,et_password;
    Button btn_login;
    TextView clickSignUp;
    ActivityLoginBinding binding;
    UserLogin user;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(Login.this,R.layout.activity_login);
        user=new UserLogin(new User("Enter Your Email","Enter Your Password"));
        binding.setUserLogin(user);
        binding.setLoginActivity(Login.this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setIndeterminate(true);

    }
    public void userLogin(){

        mProgressDialog.show();
        user.loginUser(Login.this,binding.getUserLogin().email,binding.getUserLogin().Password);
    }

    public void goToSignup(){
        Intent intent=new Intent(Login.this,Signup.class);
        startActivity(intent);
    }
    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "Sucess", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Login.this,UsersList.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String msg) {
        mProgressDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
}