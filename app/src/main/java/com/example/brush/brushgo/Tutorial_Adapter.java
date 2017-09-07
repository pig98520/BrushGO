package com.example.brush.brushgo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by pig98520 on 2017/7/28.
 */

public class Tutorial_Adapter extends PagerAdapter {
    private String [] imageUrl ={
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=74fd4d58-8e2b-45de-bd9f-bb66ec7014c1",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=10f74194-ab79-430b-8712-3cf710b541cf",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=55267499-2df4-42c1-8c2b-9cf1396a3ff3",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=3191f088-bec4-4c4b-9dca-3c4172b99f0d"
    };
    private LayoutInflater layoutInflater;
    private Context context;
    private View view;
    private ImageView imageView;
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
