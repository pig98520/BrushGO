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
            {"https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F0.png?alt=media&token=d029581c-19e5-464e-9b60-ae6437843df8",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F1.png?alt=media&token=a6a05a32-26e7-41f7-80d5-9f77854942b9",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F2.png?alt=media&token=7e84cb70-a8bc-46dd-b043-d2e4fc97b30a",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F3.png?alt=media&token=f4bfe148-cc12-4a24-9edd-5895bb79e957",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F4.png?alt=media&token=03975a80-ca9a-4b25-acd7-e4c9f2048019",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F5.png?alt=media&token=a3c24703-0a29-48ec-850e-a7faa430d3d5",
                    "https://firebasestorage.googleapis.com/v0/b/brushgo-67813.appspot.com/o/tutorial%2F6.png?alt=media&token=c175f274-3cac-4be1-a70d-7b1793421b2a"};
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
