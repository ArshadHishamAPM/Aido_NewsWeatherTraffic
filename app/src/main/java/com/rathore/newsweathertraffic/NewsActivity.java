package com.rathore.newsweathertraffic;


import android.app.Fragment;
import android.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private ViewPager mPager;
    private ActionBarDrawerToggle mDrawerToggle;
    NewsModel newsModel;
    String url;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    static ArrayList<NewsModel> list = new ArrayList<>();

    private PagerAdapter mPagerAdapter;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressBar = (ProgressBar)findViewById(R.id.progress) ;
        progressBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ArrayList arrayList = new ArrayList();
        arrayList.add("News");
        arrayList.add("Traffic");
        arrayList.add("Weather");
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.listtextview, arrayList));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle("mTitle");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getActionBar().setTitle("mDrawerTitle");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),list);


        mPager.setAdapter(mPagerAdapter);
       // mPagerAdapter.notifyDataSetChanged();
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("pageNo",String.valueOf(position));
                //mPagerAdapter.notifyDataSetChanged();



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getNews();




    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<NewsModel> list1 = new ArrayList<>();
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm,ArrayList<NewsModel> list) {
            super(fm);
            list1 = list;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Log.i("getItem",String.valueOf(position));
            progressBar.setVisibility(View.INVISIBLE);
            ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
           // mPagerAdapter.notifyDataSetChanged();
            return   screenSlidePageFragment.newInstance(list1.get(position));
        }

        @Override
        public int getCount() {

            return list.size();
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

//        @Override
//        public int getCount() {
//            return NUM_PAGES;
//        }
    }
    public void getNews(){
        //news Api org
        url = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=0d913fbce03540799054a4633035a858";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       Log.i("result",response.toString());
                        try {
                            String articles = response.get("articles").toString();
                            JSONArray array=new JSONArray(articles);
                            for(int i=0;i<array.length();i++){

                                JSONObject jsonpart = array.getJSONObject(i);
                                String title = jsonpart.get("title").toString();
                                String description = jsonpart.get("description").toString();
                                String urlToImage = jsonpart.get("urlToImage").toString();
                                String readmore = jsonpart.get("url").toString();

                                Log.i("title",title);
                                //jsonpart.get("description");
                                //jsonpart.get("urlToImage");
                                newsModel = new NewsModel();
                                newsModel.setTitle(title);
                                newsModel.setDescription(description);
                                newsModel.setReadmore(readmore);
                                newsModel.setUrlofImage(urlToImage);

                                list.add(newsModel);
                               // progress.dismiss();
                                mPagerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("list",String.valueOf(list.size()));
                        Log.i("list",list.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("response",error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position==0){
//                Intent intent = new Intent(getApplicationContext(),NewsActivity.class);
//                startActivity(intent);
                mDrawerLayout.closeDrawers();
                return;
            }
            if(position==1){

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                        startActivity(intent);
                        finish();
//                    }
//                }, 150);
               // mDrawerLayout.closeDrawers();

            }
            if(position==2){

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),WeatherForcast.class);
                        startActivity(intent);
                        finish();
//                    }
//                }, 150);
                //mDrawerLayout.closeDrawers();

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mDrawerLayout.closeDrawers();
//    }

}

