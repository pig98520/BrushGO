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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by swlab on 2017/5/5.
 */

public class Video_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button menu;
    private DrawerLayout drawer;
    private FirebaseAuth auth;
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
            Toast.makeText(Video_Activity.this,"雙擊以退出",Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.video);
        processView();
        processControl();
    }

    private void processView() {
        NavigationView navigateionView=(NavigationView) findViewById(R.id.nav_video);
        navigateionView.setNavigationItemSelectedListener(Video_Activity.this);
        menu=(Button) findViewById(R.id.btn_menu);
        drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        auth= FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference("video");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    DB_Video info=snapshot.getValue(DB_Video.class);
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

        FirebaseRecyclerAdapter<DB_Video,infoViewHolder> adapter=new FirebaseRecyclerAdapter<DB_Video, infoViewHolder>(DB_Video.class,R.layout.video_list,infoViewHolder.class,dbRef) {
            @Override
            protected void populateViewHolder(infoViewHolder viewHolder, DB_Video model, int position) {
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

        public void setList(final DB_Video video) {
            title.setText(video.getTitle());
            content.setText("影片來源: "+video.getContent());
            Glide.with(image.getContext())
                    .load(video.getImageUrl())
                    .into(image);
/*            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAGGG",video.getVideo_id());
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("video_id",video.getVideo_id());
                    Intent intent = new Intent(itemView.getContext(),Video_Youtube_Activity.class);
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }


    private void processControl() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.Home)
        {
            Intent intent=new Intent();
            intent.setClass(this,Home_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Video)
        {
            Intent intent=new Intent();
            intent.setClass(this,Video_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Information)
        {
            Intent intent=new Intent();
            intent.setClass(this,Information_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Tutorial)
        {
            Intent intent=new Intent();
            intent.setClass(this,Tutorial_Activity.class);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.Setting)
        {
            Intent intent=new Intent();
            intent.setClass(this,Setting_Activity.class);
            startActivity(intent);
            finish();
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
                    startActivity(new Intent(Video_Activity.this,MainActivity.class));
                    finish();
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