package com.example.login;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText PassInput;
    private CheckBox ShowPass;

    EditText emailid, pass;
    Button daftar;
    TextView masuk;
    FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PassInput = findViewById(R.id.password);
        ShowPass = findViewById(R.id.showPass);

        mFireBaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        daftar = findViewById(R.id.button);
        masuk = findViewById(R.id.login);

        ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowPass.isChecked()) {
                    PassInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    PassInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String pwd = pass.getText().toString();
                if(email.isEmpty()){
                    emailid.setText("Tolong masukkan email");
                    emailid.requestFocus();
                }
                else if(pwd.isEmpty()){
                    pass.setText("Tolong masukkan password");
                    pass.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Isi yang kosong", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFireBaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}