package com.example.brush.brushgo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by pig98520 on 2017/7/28.
 */

public class Tutorial_Adapter extends PagerAdapter {
/*    private int[] imageUrl={R.drawable.tutorial_1,R.drawable.tutorial_2,R.drawable.tutorial_3,R.drawable.tutorial_4};*/
    private String [] imageUrl ={
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=8ae81c40-2caa-4750-b7e7-2d4f412aa966",
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=bc81cb81-fece-4b31-94a9-06e9ceaf79e5",
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=af657ade-e60b-4847-8d3f-f7c96a9a0dce",
            "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=430191f7-371d-4f86-a7ef-7ad0c1d39757"
    };
    private LayoutInflater layoutInflater;
    private Context context;
    private View view;
    private ImageView imageView;

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
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        container.invalidate();
    }
}
