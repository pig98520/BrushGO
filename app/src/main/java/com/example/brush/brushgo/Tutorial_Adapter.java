package com.example.brush.brushgo;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by pig98520 on 2017/7/28.
 */

public class Tutorial_Adapter extends PagerAdapter{
    private String[] imageUrl=
            {"https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_0.png?alt=media&token=cc09d7a7-10e5-4eaf-a028-a71501fa54c3",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_1.png?alt=media&token=106a8205-a1b3-487a-9662-5e3212460b9d",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_2.png?alt=media&token=0fe91140-2301-46ab-9ab2-f22f367f4499",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_3.png?alt=media&token=8a124fee-18fc-429c-8a0e-99632a1ed499",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2Ftutorial_4.png?alt=media&token=0853e679-8ce5-44b6-8e26-601cd3868118"};
    private LayoutInflater layoutInflater;
    private Context context;

    public void setImageUrl(String url,int i){
        this.imageUrl[i]=url;
    }

    public Tutorial_Adapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return imageUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(ConstraintLayout)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.tutorial_image,container,false);
        ImageView imageView=(ImageView)v.findViewById(R.id.imageView);
        Glide.with(context).load(Uri.parse(imageUrl[position])).into(imageView);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
