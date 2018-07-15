package com.handshake.pritz.OrganicHomeStay;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Toast;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class Logi extends AppCompatActivity {

    public EditText inputEmail, inputPassword;
    public FirebaseAuth auth;

    public FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar progressBar;
    private Button gosignup, gologin, goreset;
    String email,password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logi);

        inputEmail =   findViewById(R.id.email);
        inputPassword =   findViewById(R.id.password);
        progressBar =   findViewById(R.id.progressBar);
        gosignup=findViewById(R.id.btn_signupp);
        gologin=findViewById(R.id.btn_loginn);
        goreset=findViewById(R.id.btn_reset);
        auth = FirebaseAuth.getInstance();
        /*authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    Intent i = new Intent(Logi.this, MainActivity.class);
                    startActivity(i);

                }

            }
        };
        auth.addAuthStateListener(authStateListener);*/
        gosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Logi.this, signup.class));

            }
        });



        goreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Logi.this, ResetPasswordActivity.class));
            }
        });

        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Logi.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Logi.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Logi.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();

                                    if (password.length() < 6) {
                                        inputPassword.setError("Password Too Small");
                                    } else {
                                        Toast.makeText(Logi.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null)
        {
            Intent i = new Intent(Logi.this, MainActivity.class);
            startActivity(i);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}