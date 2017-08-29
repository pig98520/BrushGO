package com.example.brush.brushgo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by pig98520 on 2017/8/28.
 */

public class Firework_Dialog extends AppCompatActivity {
/*    https://www.youtube.com/watch?v=fn5OlqQuOCk
    http://ellis-android-blog.blogspot.tw/2015/07/android-line-custom-dialog.html?m=1*/
    private TextView title;
    private TextView message;
    private Button confirm;
    private ImageView[] imageArray=new ImageView[8];
    private int[] imageView=new int[]{R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,
            R.id.imageView5,R.id.imageView6,R.id.imageView7,R.id.imageView8};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firework_dilog);
        processView();
        processControl();
    }

    private void processView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("刷牙時間到囉~");
        message = (TextView) findViewById(R.id.message);
        message.setText("恭喜您刷完牙了，按下確認以紀錄。");
        confirm = (Button) findViewById(R.id.confirm);
/*        confirm.setBackgroundColor(Color.TRANSPARENT);*/
/*        getWindow().setLayout(700,400);*/
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        for(int i=0;i<imageView.length;i++)
        {
            imageArray[i]=(ImageView)findViewById(imageView[i]);
            Glide.with(Firework_Dialog.this)
                    .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/image%2Ffirework.gif?alt=media&token=c3d69e19-6be0-415e-86ff-7aeed07f3c51"))
                    .into(imageArray[i]);
        }
    }

    private void processControl() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
