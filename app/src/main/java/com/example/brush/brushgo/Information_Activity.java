package com.example.brush.brushgo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.brush.brushgo.R.id.Home;

/**
 * Created by swlab on 2017/5/5.
 */

public class Information_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private Button brush;
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
    private Firebase touchedRef;
    private String nowTime;
    private Boolean isdoubleClick=false;
    private RecyclerView recyclerView;

    private Dialog customDialog;
    private Button dialog_confirm;
    private Button dialog_cancel;
    private TextView dialog_title;
    private TextView dialog_message;

    @Override
    public void onBackPressed() {
        if(!isdoubleClick)
        {
            Toast.makeText(Information_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
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

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        recordTouched();
        processView();
        processControl();
    }

    private void recordTouched() {
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        auth= FirebaseAuth.getInstance();
        touchedRef =firebaseRef.child("touched").child(auth.getCurrentUser().getUid()).child("information");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DB_recordTouched touched=new DB_recordTouched(touchedRef,nowTime);
        touched.pushValue();
    }

    private void processView() {
        NavigationView navigateionView=(NavigationView) findViewById(R.id.nav_information);
        navigateionView.setNavigationItemSelectedListener(Information_Activity.this);
        menu=(Button) findViewById(R.id.btn_menu);
        brush=(Button)findViewById(R.id.btn_brush);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        auth= FirebaseAuth.getInstance();
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference("information");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    DB_Information info=snapshot.getValue(DB_Information.class);
                    Log.i("Photo's Title:", info.getTitle());
                    Log.i("Photo's Content:", info.getContent());
                    Log.i("Photo's Url:", info.getImageUrl());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Photo", "failed: " + databaseError.getMessage());
            }
        });

        FirebaseRecyclerAdapter<DB_Information,infoViewHolder> adapter=new FirebaseRecyclerAdapter<DB_Information, infoViewHolder>(DB_Information.class,R.layout.information_list,infoViewHolder.class,dbRef) {
            @Override
            protected void populateViewHolder(infoViewHolder viewHolder, DB_Information model, int position) {
                viewHolder.setList(model);
            }
        };
        recyclerView.setAdapter(adapter);

    }
    public static class infoViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView content;

        public infoViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            content = (TextView) itemView.findViewById(R.id.txt_content);
        }

        public void setList(DB_Information information) {
            title.setText(information.getTitle());
/*            content.setText(information.getContent());*/
            content.setText(Html.fromHtml("<a href="+information.getContent()+">觀看文章</a> "));
            content.setMovementMethod(LinkMovementMethod.getInstance());
            Glide.with(image.getContext())
                    .load(information.getImageUrl())
                    .into(image);
        }
    }


    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Information_Activity.this,Home_Activity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id== Home)
        {
            startActivity(new Intent(this,Home_Activity.class));
        }
        else if(id==R.id.Video)
        {
            startActivity(new Intent(this,Video_Activity.class));
        }
        else if(id==R.id.Information)
        {
            startActivity(new Intent(this,Information_Activity.class));
        }
        else if(id==R.id.Tutorial)
        {
            startActivity(new Intent(this,Tutorial_Activity.class));
        }
        else if(id==R.id.Tooth_Condition){
            startActivity(new Intent(this,Tooth_Condition_Activity.class));
        }
        else if(id==R.id.Setting)
        {
            startActivity(new Intent(this,Setting_Activity.class));
        }
        else if(id==R.id.Logout)
        {
            customDialog =new Dialog(this,R.style.DialogCustom);
            customDialog.setContentView(R.layout.custom_dialog_two);
            customDialog.setCancelable(false);
            dialog_title = (TextView) customDialog.findViewById(R.id.title);
            dialog_title.setText("確定要登出?");
            dialog_message = (TextView) customDialog.findViewById(R.id.message);
            dialog_message.setText("登出後將無法準確紀錄您刷牙的狀況，但您仍會收到BrushGo的提醒。");
            dialog_confirm = (Button) customDialog.findViewById(R.id.confirm);
            dialog_confirm.setText("登出");
            dialog_cancel=(Button) customDialog.findViewById(R.id.cancel);
            dialog_cancel.setText("取消");
            customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);

            customDialog.show();

            dialog_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    customDialog.dismiss();
                    startActivity(new Intent(Information_Activity.this,MainActivity.class));
                }
            });
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}