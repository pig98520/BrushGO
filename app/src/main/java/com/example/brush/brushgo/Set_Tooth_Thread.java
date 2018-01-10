package com.example.brush.brushgo;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.brush.brushgo.R.id.imageView_1;
import static com.example.brush.brushgo.R.id.imageView_10;
import static com.example.brush.brushgo.R.id.imageView_11;
import static com.example.brush.brushgo.R.id.imageView_12;
import static com.example.brush.brushgo.R.id.imageView_13;
import static com.example.brush.brushgo.R.id.imageView_14;
import static com.example.brush.brushgo.R.id.imageView_15;
import static com.example.brush.brushgo.R.id.imageView_16;
import static com.example.brush.brushgo.R.id.imageView_17;
import static com.example.brush.brushgo.R.id.imageView_18;
import static com.example.brush.brushgo.R.id.imageView_19;
import static com.example.brush.brushgo.R.id.imageView_2;
import static com.example.brush.brushgo.R.id.imageView_20;
import static com.example.brush.brushgo.R.id.imageView_21;
import static com.example.brush.brushgo.R.id.imageView_22;
import static com.example.brush.brushgo.R.id.imageView_23;
import static com.example.brush.brushgo.R.id.imageView_24;
import static com.example.brush.brushgo.R.id.imageView_25;
import static com.example.brush.brushgo.R.id.imageView_26;
import static com.example.brush.brushgo.R.id.imageView_27;
import static com.example.brush.brushgo.R.id.imageView_28;
import static com.example.brush.brushgo.R.id.imageView_29;
import static com.example.brush.brushgo.R.id.imageView_3;
import static com.example.brush.brushgo.R.id.imageView_30;
import static com.example.brush.brushgo.R.id.imageView_31;
import static com.example.brush.brushgo.R.id.imageView_32;
import static com.example.brush.brushgo.R.id.imageView_4;
import static com.example.brush.brushgo.R.id.imageView_5;
import static com.example.brush.brushgo.R.id.imageView_6;
import static com.example.brush.brushgo.R.id.imageView_7;
import static com.example.brush.brushgo.R.id.imageView_8;
import static com.example.brush.brushgo.R.id.imageView_9;

/**
 * Created by pig98520 on 2018/1/9.
 */

public class Set_Tooth_Thread extends Thread {
    private Home_Activity home_activity;
    private Firebase firebaseRef;
    private Firebase toothRef;
    private StorageReference storageRef;
    private String[] tooth_image=new String[]{
            "tooth_1","tooth_2","tooth_3","tooth_4",
            "tooth_5","tooth_6","tooth_7","tooth_8",
            "tooth_9","tooth_10","tooth_11","tooth_12",
            "tooth_13","tooth_14","tooth_15","tooth_16"};
    private int tooth_id[]=new int[]{
            imageView_1,imageView_2,imageView_3,imageView_4,imageView_5,imageView_6,imageView_7, imageView_8,
            imageView_9,imageView_10,imageView_11,imageView_12,imageView_13,imageView_14, imageView_15,imageView_16,
            imageView_17,imageView_18,imageView_19,imageView_20,imageView_21, imageView_22,imageView_23,imageView_24,
            imageView_25,imageView_26,imageView_27,imageView_28,imageView_29,imageView_30,imageView_31,imageView_32};
    private ImageView tooth[]=new ImageView[32];
    private RequestOptions options;
    private boolean isClean=false;

    public Set_Tooth_Thread(Home_Activity home_activity, Boolean isClean) {
        this.home_activity=home_activity;
        this.isClean=isClean;
    }

    public void run(){
        storageRef= FirebaseStorage.getInstance().getReference();
        firebaseRef=new Firebase("https://brushgo-67813.firebaseio.com/");
        toothRef=firebaseRef.child("tooth").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        for(int i=0;i<tooth.length;i++)
            tooth[i]=(ImageView)home_activity.findViewById(tooth_id[i]);

        if(isClean)
        {
            for(int i=0;i<tooth.length;i++){
                final int finalI = i;
                storageRef.child("tooth").child(tooth_image[i%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        options = new RequestOptions()
                                .dontAnimate();
                        Glide.with(home_activity)
                                .setDefaultRequestOptions(options)
                                .load(uri)
                                .into(tooth[finalI]);
                    }
                });
            }
        }
        else{
            for(int j=0;j<tooth.length;j++) {
                final int finalJ = j;
                toothRef.child(j+1+"").child("in").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.getValue().toString().equals("b")){
                                toothRef.child(finalJ +1+"").child("out").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //內B外G
                                        if(dataSnapshot.getValue().toString().equals("g")){
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_i.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(home_activity)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                        else if(dataSnapshot.getValue().toString().equals("b")){
                                            //內外皆B
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(home_activity)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }

                            if(dataSnapshot.getValue().toString().equals("g")){
                                toothRef.child(finalJ +1+"").child("out").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue().toString().equals("g")){
                                            //內外都G
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(home_activity)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                        else if(dataSnapshot.getValue().toString().equals("b")){
                                            //內G外B
                                            storageRef.child("tooth").child(tooth_image[finalJ%16]+"_o.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(home_activity)
                                                            .load(uri)
                                                            .into(tooth[finalJ]);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                        }
                        else{
                            for(int i=0;i<tooth.length;i++) {
                                toothRef.child(i + 1 + "").child("in").setValue("g");
                                toothRef.child(i + 1 + "").child("out").setValue("g");
                            }
                            run();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        }
    }
}
