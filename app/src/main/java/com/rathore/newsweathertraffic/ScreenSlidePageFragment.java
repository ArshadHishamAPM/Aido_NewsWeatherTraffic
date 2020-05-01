package com.rathore.newsweathertraffic;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by rathore on 07/08/17.
 */

public class ScreenSlidePageFragment extends Fragment {
  // NewsModel newsModel = new NewsModel();
    String title,description,urlToImage,readMore;
    public ScreenSlidePageFragment newInstance(NewsModel newsModel){
       ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString("title", newsModel.getTitle());
        args.putString("description", newsModel.getDescription());
        args.putString("urlToImage", newsModel.getUrlofImage());
        args.putString("readmore",newsModel.getReadmore());
        screenSlidePageFragment.setArguments(args);
    return screenSlidePageFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        description = getArguments().getString("description");
        urlToImage = getArguments().getString("urlToImage");
        readMore  = getArguments().getString("readmore");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        Button readmore = (Button) rootView.findViewById(R.id.readmore);
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),WebViewActivity.class);
                intent.putExtra("readmore",readMore);
                intent.setAction("ScreenSlidePageFragment");
                startActivity(intent);
            }
        });
        TextView title1 = (TextView) rootView.findViewById(R.id.title);
        TextView description1 = (TextView) rootView.findViewById(R.id.description);
        ImageView urltoimage1 = (ImageView) rootView.findViewById(R.id.image);
        title1.setText(title);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress) ;
        Glide.with(getContext())
                .load(urlToImage)
                .listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .into(urltoimage1);
        description1.setText(description);




        return rootView;
    }
}
