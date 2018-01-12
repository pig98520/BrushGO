package com.example.brush.brushgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG =" MainActivity" ;
    private static final int RC_SIGN_IN=1;
    private GoogleSignInOptions gso;
    private Dialog progressDialog;
    private Dialog customDialog;
    private TextView dialog_title;
    private TextView dialog_message;
    private EditText dialog_name;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private Button btn_google;
/*    private Button btn_sigin;
    private Button btn_forget;
    private Button btn_signup;
    private EditText edt_id;
    private EditText edt_psw;*/
    private String user;
    private String psw;
    private String user_name;
    private String nowDate;
    private int timeArray[]=new int[] {120,180,240};
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authLinstener;
    private GoogleApiClient apiClient;
    private Firebase myFirebaseRef;
    private Firebase settingRef;
    private Firebase profileRef;
    private Firebase toothRef;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

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
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "無法使用google帳戶登入",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void processViews() {
        btn_google=(Button)findViewById(R.id.btn_google);
/*        btn_sigin =(Button)findViewById(R.id.btn_signin);
        btn_signup =(Button)findViewById(R.id.btn_signup);
        btn_forget =(Button)findViewById(R.id.btn_forget);
        edt_id =(EditText)findViewById(R.id.edt_id);
        edt_psw =(EditText)findViewById(R.id.edt_psw);*/
        myFirebaseRef = new Firebase("https://brushgo-67813.firebaseio.com/");
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
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));
                }
            }
        };

    }

    private void processControllers() {
       /* btn_sigin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user= edt_id.getText().toString();
                psw= edt_psw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this, "請輸入帳號和密碼", Toast.LENGTH_SHORT).show();
                else {
                    loadingDialog();
                    login(user, psw);
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                user= edt_id.getText().toString();
                psw= edt_psw.getText().toString();
                if(user.equals("")||psw.equals(""))
                    Toast.makeText(MainActivity.this,  "請輸入帳號和密碼", Toast.LENGTH_SHORT).show();
                else
                    signupDialog();

            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                forgetDialog();
            }
        });*/
        btn_google.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(isNetworkAvailable()){
                loadingDialog();
                signIn();
                }
                else
                    Toast.makeText(MainActivity.this,"請確認您的裝置有連接至網路",Toast.LENGTH_LONG).show();
            }
        });
    }

/*    private void forgetDialog() {
            AlertDialog.Builder forgetDialog=new AlertDialog.Builder(this);
            forgetDialog.setTitle("忘記密碼");
            forgetDialog.setMessage("是否發送忘記密碼E-mail聯絡客服?\n\n"+"若無安裝郵件軟體，請寫信到brushgoapp@gmail.com");
            DialogInterface.OnClickListener confirmClick =new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent sendMail = new Intent(Intent.ACTION_SEND);
                    sendMail.setType("message/rfc822");
                    sendMail.putExtra(Intent.EXTRA_EMAIL  , new String[]{"brushgoapp@gmail.com"});
                    sendMail.putExtra(Intent.EXTRA_SUBJECT, "我忘記我的BrushGo密碼");
                    sendMail.putExtra(Intent.EXTRA_TEXT   , "請完成以下資料，我們將盡快發送新的密碼至您的Mail\n\n\n" +
                                                                "--------------------------------------------------\n"+
                                                                "姓名:\n\n"+
                                                                "郵件:\n\n"+
                                                                "備用郵件:\n\n"+
                                                                "--------------------------------------------------"+
                                                                "\n\n\n感謝您的填寫，我們將盡快為您處理");
                    try {
                        startActivity(Intent.createChooser(sendMail, "郵件傳送中..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(MainActivity.this, "傳送郵件失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            DialogInterface.OnClickListener cancelClick =new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            };
            forgetDialog.setNeutralButton("是",confirmClick);
            forgetDialog.setNegativeButton("否",cancelClick);
            forgetDialog.show();
    }*/

    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(authLinstener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

/*    private void login(final String user, final String psw){
        auth.signInWithEmailAndPassword(user, psw)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Home_Activity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"請檢查帳號和密碼。", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

/*    private void createUser(final String user_name, final String user, final String psw) {
        auth.createUserWithEmailAndPassword(user,psw)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    public void onSuccess(AuthResult authResult) {
                        newuser(user_name);
                        progressDialog.dismiss();
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Tutorial_Activity.class);
                        startActivity(intent);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if(!isEmailValid(user))
                            Toast.makeText(MainActivity.this,"帳號必須為電子郵件。", Toast.LENGTH_SHORT).show();
                        else if(psw.length()<6)
                            Toast.makeText(MainActivity.this,"密碼至少為六碼。", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"註冊失敗，請檢查帳號是否已存在。", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    private void loadingDialog() {
        progressDialog =new Dialog(this,R.style.DialogCustom);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.setCancelable(false);
        dialog_title = (TextView) progressDialog.findViewById(R.id.title);
        dialog_title.setText("Loading");
        dialog_message = (TextView) progressDialog.findViewById(R.id.message);
        dialog_message.setText("登入中請稍候...");
        progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

        progressDialog.show();
    }

    private void newuser(String user_name) {
        settingRef = myFirebaseRef.child("setting").child(auth.getCurrentUser().getUid().trim());
        DB_Setting setting = new DB_Setting(auth.getCurrentUser().getEmail(),180,1,null,null,null,null,null);
        settingRef.setValue(setting);

        profileRef=myFirebaseRef.child("profile").child(auth.getCurrentUser().getUid().trim());
        DB_Profile profile=new DB_Profile(user_name,nowDate,null,null,null);
        profileRef.setValue(profile);

        toothRef=myFirebaseRef.child("tooth").child(auth.getCurrentUser().getUid().trim());
        for(int i=0;i<28;i++)
        {
            toothRef.child(i+1+"").setValue("g");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        
    }

}