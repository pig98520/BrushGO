package com.example.brush.brushgo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pig98520 on 2017/8/28.
 */

public class Finsih_Dialog extends AppCompatActivity {
/*    https://www.youtube.com/watch?v=fn5OlqQuOCk*/
    private TextView title;
    private TextView message;
    private Button confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firework_dilog);
        processView();
        processControl();
    }

    private void processView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("刷牙時間到瞜~");
        message = (TextView) findViewById(R.id.message);
        message.setText("恭喜您刷完牙了，按下確認以紀錄。");
        confirm = (Button) findViewById(R.id.confirm);
        getWindow().setLayout(800, 420);
    }

    private void processControl() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Finsih_Dialog.this,  "使用紀錄已更新", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
