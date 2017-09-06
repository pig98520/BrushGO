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
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=85823925-4687-4447-a815-e1ea1bc82dd8",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=3131009c-33c0-4bd2-91e4-9aa2f4755e5f",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=37ae4bc7-03b0-4c2f-aa74-25649244b7a8",
        "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=1bef073e-6bd7-41be-b6f8-efc5310871b8"
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
