package com.example.phiiphiroberts.ueat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phiiphiroberts.ueat.Common.Common;
import com.example.phiiphiroberts.ueat.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {
    EditText phone,username,pass;
    Button btnSignUp;


    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        configureSignInButton();


        phone= (EditText) findViewById(R.id.editPhone);
        pass = (EditText) findViewById(R.id.editPass);
        username = (EditText) findViewById(R.id.editUserName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Init Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    //final String password = pass.getText().toString();


                    if (Common.isConnectedToInternet(getBaseContext())) {
                        final String my_phone = pass.getText().toString();
                        final String password = pass.getText().toString();
                        final String user_phone = phone.getText().toString().trim();
                        final String user_pass = pass.getText().toString().trim();
                        final String user_name = username.getText().toString().trim();

                        //Checking for Empty Fields and setting errors to fields
                        if (TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_name) && TextUtils.isEmpty(user_pass)) {
                            phone.setError("This field is required");
                            username.setError("This field is required");
                            pass.setError("This field is required");
                            return;
                        }else if(!TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_pass) && pass.length()<7){
                            pass.setError("password must exceed 6 characters");
                            return;
                        }else if(!TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_pass) && phone.length()!=10) {
                            phone.setError("Phone number must be 10 characters");
                            return;
                        }else if(TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_pass) && !TextUtils.isEmpty(user_name)) {
                            phone.setError("This field is required");
                            pass.setError("This field is required");
                            return;
                        } else if (!TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_pass) && TextUtils.isEmpty(user_name)) {
                            pass.setError("This field is required");
                            username.setError("This field is required");
                            return;
                        } else if (TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_pass) && TextUtils.isEmpty(user_name)) {
                            phone.setError("This field is required");
                            username.setError("This field is required");
                            return;
                        } else if (TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_pass) && !TextUtils.isEmpty(user_name)) {
                            phone.setError("This field is required");
                            return;
                        } else if (!TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_pass) && !TextUtils.isEmpty(user_name)) {
                            pass.setError("This field is required");
                            return;
                        } else if (!TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_pass) && TextUtils.isEmpty(user_name)) {
                            username.setError("This field is required");
                            return;
                        }else {

                        }

                        //setting a progress dialogue
                        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                        mDialog.setMessage("Please Waiting");
                        mDialog.show();
                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                //Check if user info exist in database
                                if (dataSnapshot.child(phone.getText().toString()).exists()) {
                                    mDialog.dismiss();

                                    //giving feedback after user click sign in button
                                    FancyToast.makeText(SignUp.this, "Account Exist", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    finish();
                                } else {
                                    mDialog.dismiss();
                                    User user = new User(username.getText().toString(), pass.getText().toString());
                                    table_user.child(phone.getText().toString()).setValue(user);

                                    //giving feedback after user click sign in button
                                    FancyToast.makeText(SignUp.this, "SignUp Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    finish();
                                    Intent homeIntent = new Intent(SignUp.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                }else {
                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Please Waiting");
                    mDialog.show();
                    mDialog.dismiss();
                    FancyToast.makeText(SignUp.this, "Please Check your Internet Connection", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    return;
                }
            }
        });
        //cb = (CheckBox) findViewById(R.id.checkBox);
    }


    private void configureSignInButton(){
        Button btnSignUp = (Button) findViewById(R.id.btnSigIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*public void checkone(View view){
        if (cb.isChecked()){
            Toast.makeText(SignUp.this, "Remembered", Toast.LENGTH_SHORT).show();

        }
    }*/
}
