package com.example.phiiphiroberts.ueat;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MessageEmail extends AppCompatActivity implements View.OnClickListener {

    private EditText new_pass;
    private TextView txtWelcome;
    private Button changePass;
    private ConstraintLayout activity_pass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_email);


        //view
        new_pass= (EditText)findViewById(R.id.new_pass);
        changePass = (Button)findViewById(R.id.change_pass);
        activity_pass = (ConstraintLayout)findViewById(R.id.activity_message_email);
        txtWelcome  = (TextView) findViewById(R.id.dashboard_welcome);



        auth = FirebaseAuth.getInstance();


        changePass.setOnClickListener(this);

        //Session check
        if(auth.getCurrentUser() != null)
            txtWelcome.setText("Welcome , "+auth.getCurrentUser().getEmail());


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.change_pass)
            changePassword(new_pass.getText().toString());


    }

    private void changePassword(String newPassword) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar snackBar = Snackbar.make(activity_pass,"Password changed",Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }

            }
        });
    }
}
