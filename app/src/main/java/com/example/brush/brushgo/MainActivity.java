package com.example.brush.brushgo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btn_sigin;
    private Button btn_forget;
    private Button btn_signup;
    private EditText edt_id;
    private EditText edt_psw;
    private String user;
    private String psw;
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    Firebase myFirebaseRef;
    Firebase userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processViews();
        processControllers();
    }

    private void processViews() {
        btn_sigin =(Button)findViewById(R.id.btn_signin);
        btn_signup =(Button)findViewById(R.id.btn_signup);
        btn_forget =(Button)findViewById(R.id.btn_forget);
        edt_id =(EditText)findViewById(R.id.edt_id);
        edt_psw =(EditText)findViewById(R.id.edt_psw);
    }

    private void processControllers() {
        btn_sigin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user= edt_id.getText().toString();
                psw= edt_psw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter your ID and Password~", Toast.LENGTH_SHORT).show();
                else
                    login(user,psw);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                user= edt_id.getText().toString();
                psw= edt_psw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this,  "Please enter your ID and Password~", Toast.LENGTH_SHORT).show();
                else
                    createUser(user,psw);
            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(MainActivity.this,"請檢查帳號和密碼。", Toast.LENGTH_SHORT).show();
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

                        myFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com/");
                        userRef = myFirebaseRef.child("setting").child(auth.getCurrentUser().getUid().trim());
                        DB_Setting data = new DB_Setting(auth.getCurrentUser().getEmail(),180,3);
                        userRef.setValue(data);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"註冊失敗，請檢查帳號是否存在。", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}