package com.example.phiiphiroberts.ueat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{


    private EditText input_email;
    private Button btnResetPass;
    private Button btn_back;
    private ConstraintLayout activity_forgot;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //view
        input_email= (EditText)findViewById(R.id.forgot_mail);
        btnResetPass = (Button)findViewById(R.id.btn_reset);
        btn_back = (Button)findViewById(R.id.btn_back);
        activity_forgot = (ConstraintLayout) findViewById(R.id.activity_forgot_password);


        btnResetPass.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_back)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else  if(v.getId() == R.id.btn_reset)
        {
            resetPassword(input_email.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "We Have sent password to email:" +email, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }else {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "Failed to send password",Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    }
                });
       }

   }
