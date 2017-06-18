package com.example.brush.brushgo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG =" MainActivity" ;
    private static final int RC_SIGN_IN=1;
    private GoogleSignInOptions gso;
    private Button btn_sigin;
    private Button btn_forget;
    private Button btn_signup;
    private Button btn_google;
    private EditText edt_id;
    private EditText edt_psw;
    private String user;
    private String psw;
    private int timeArray[]=new int[] {120,180,240};
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authLinstener;
    private GoogleApiClient apiClient;
    private Firebase myFirebaseRef;
    private Firebase userRef;
    private boolean isdoubleClick=false;

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(MainActivity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
            isdoubleClick=true;
        }
        else
        {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                isdoubleClick=false;
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processViews();
        processControllers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void processViews() {
        btn_sigin =(Button)findViewById(R.id.btn_signin);
        btn_signup =(Button)findViewById(R.id.btn_signup);
        btn_forget =(Button)findViewById(R.id.btn_forget);
        btn_google=(Button)findViewById(R.id.btn_google);
        edt_id =(EditText)findViewById(R.id.edt_id);
        edt_psw =(EditText)findViewById(R.id.edt_psw);
        auth= FirebaseAuth.getInstance();
        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(MainActivity.this,"登入失敗",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        authLinstener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));
            }
        };

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
        btn_google.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signIn();
            }
        });
    }
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(authLinstener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                        DB_Setting data = new DB_Setting(auth.getCurrentUser().getEmail(),timeArray[(int) (Math.random()*3)],3,"10:00","22:00");
                        userRef.setValue(data);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"註冊失敗，請檢查帳號是否存在。", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        
    }
}