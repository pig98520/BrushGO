package com.example.brush.brushgo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnSignin;
    private Button btnForget;
    private Button btnSignup;
    private EditText editID;
    private EditText editPsw;
    private String user;
    private String psw;
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processViews();
        processControllers();
    }

    private void processViews() {
        btnSignin=(Button)findViewById(R.id.btnSignIn);
        btnSignup=(Button)findViewById(R.id.btnSignUp);
        btnForget=(Button)findViewById(R.id.btnForget);
        editID=(EditText)findViewById(R.id.edtID);
        editPsw=(EditText)findViewById(R.id.edtPsw);
    }

    private void processControllers() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user=editID.getText().toString();
                psw=editPsw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter your ID and Password~", Toast.LENGTH_SHORT).show();
                else
                    login(user,psw);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                user=editID.getText().toString();
                psw=editPsw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this,  "Please enter your ID and Password~", Toast.LENGTH_SHORT).show();
                else
                    createUser(user,psw);
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Are you forget your password?", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Question_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void login(final String user, final String psw){
        auth.signInWithEmailAndPassword(user, psw)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this,"歡迎回來~   "+user, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Home_Activity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"請填入帳號和密碼。", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void createUser(final String user,final String psw) {
        auth.createUserWithEmailAndPassword(user,psw)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this,"歡迎加入~ "+user, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Home_Activity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"註冊失敗，請檢查帳號是否存在。", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}