package com.example.cleanv2.Service;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class ImageLoader implements ImageLoadingService {


    @Override
    public void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);

    }

    @Override
    public void loadImage(int resource, ImageView imageView) {

    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {

    }
}

