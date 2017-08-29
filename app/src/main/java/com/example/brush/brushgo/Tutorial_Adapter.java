package com.example.brush.brushgo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by pig98520 on 2017/7/28.
 */

public class Tutorial_Adapter extends PagerAdapter {
/*    private int[] imageUrl={R.drawable.tutorial_1,R.drawable.tutorial_2,R.drawable.tutorial_3,R.drawable.tutorial_4};*/
    private String [] imageUrl ={
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=3d9446c0-ea3a-4ed6-80c5-456f32c6bd3b",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=93dde55c-b5be-4358-bc12-6e8fcc1f8b2b",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=375503ae-7d38-4e8a-b827-5ce7d15e6903",
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=1dd95b86-5bbc-49ac-8647-76279ed87450"
    };
    private LayoutInflater layoutInflater;
    private Context context;
    private View view;
    private ImageView imageView;
    private Firebase dbRef;
    private ArrayList<String > imageList =new ArrayList<String>();

    public Tutorial_Adapter (Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount() {
        return imageUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view== object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.tutorial_image,container,false);
        imageView=(ImageView)view.findViewById(R.id.imageView);
/*        imageView.setImageResource(imageUrl[position]);*/
        Glide.with(context).load(Uri.parse(imageUrl[position])).into(imageView);
        container.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.invalidate();
    }
}
