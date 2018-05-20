package com.example.phiiphiroberts.ueat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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


public class MainActivity extends AppCompatActivity {
    EditText userPhone,userPass;
    Button myLogin;
    TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPhone= (EditText) findViewById(R.id.userPhone);
        userPass = (EditText) findViewById(R.id.userPass);
        forgotPass= (TextView) findViewById(R.id.forgotPass);
        myLogin = (Button) findViewById(R.id.myLogin);


        //Init Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        //Setting a Listener to Login Button
        myLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {

                    final String user_phone = userPhone.getText().toString().trim();
                    final String user_pass = userPass.getText().toString().trim();

                    //Checking for Empty Fields and setting errors to fields
                    if (TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_pass)) {
                        userPhone.setError("This field is required");
                        userPass.setError("This field is required");
                        return;
                    } else if (TextUtils.isEmpty(user_phone) && !TextUtils.isEmpty(user_pass)) {
                        userPhone.setError("This field is required");
                        return;
                    } else if (!TextUtils.isEmpty(user_phone) && TextUtils.isEmpty(user_pass)) {
                        userPass.setError("This field is required");
                        return;
                    } else {

                    }

                    //setting a progress dialogue
                    final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("Please Waiting...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            //Check if user information exist in database
                            if (dataSnapshot.child(userPhone.getText().toString()).exists()) {

                                //Get User Information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(userPhone.getText().toString()).getValue(User.class);
                                user.setPhone(userPhone.getText().toString());

                                if (user.getPassword().equals(userPass.getText().toString())) {
                                    Intent Home = new Intent(MainActivity.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(Home);

                                    //giving feedback after user click sign in button
                                    FancyToast.makeText(MainActivity.this, "Login Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    finish();


                                } else {
                                    FancyToast.makeText(MainActivity.this, "Wrong Password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                }
                            } else {
                                mDialog.dismiss();
                                FancyToast.makeText(MainActivity.this, "User not found in database", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }
                else {
                    FancyToast.makeText(MainActivity.this, "Please Check your Internet Connection", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    return;
                }
            }


        });

        //sending user to forget activity
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });


        //calling the method configureSignUpButton
        configureSignUpButton();

    }

    //switching to signUp activity
    private void configureSignUpButton() {
        Button signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));

            }
        });
    }

}
