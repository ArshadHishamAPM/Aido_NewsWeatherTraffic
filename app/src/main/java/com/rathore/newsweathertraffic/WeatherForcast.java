package com.rathore.newsweathertraffic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherForcast extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    EditText cityname;
    TextView result,currentLocation;
    Button done;
    WeatherModel weatherModel;
   static double lat, lng;
    ArrayList<String> list = new ArrayList<>();
    private Location mLastLocation;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<WeatherModel> wheatherDataList = new ArrayList<>();

    LocationManager locationManager;
    InputMethodManager inputManager;
    String mprovider;

    private FusedLocationProviderClient mFusedLocationClient;

    int LOCATION_REFRESH_TIME = 1000;
    int LOCATION_REFRESH_DISTANCE = 5;
    String url="";

    Geocoder geocoder;
    List<android.location.Address> addresses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_weather_forcast);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Weather");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cityname = (EditText) findViewById(R.id.cityName);
        cityname.setText("Palo Alto");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        url = "http://api.openweathermap.org/data/2.5/forecast?lat="+37.468319+"&lon="+(-122.143936)+"&appid=291b3a14d9367e0fff371be2a1912b44";
        getWeather(url);
//        mAdapter = new RecylerviewAdapter(WeatherForcast.this,wheatherDataList);
//        mRecyclerView.setAdapter(mAdapter);


        geocoder = new Geocoder(this, Locale.getDefault());



        inputManager =
                (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);


        cityname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("pressed","Enter pressed");
                    inputManager.hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    String name = cityname.getText().toString();
                    if (name != null) {
                        //for 5 days

                        url = "http://api.openweathermap.org/data/2.5/forecast?q="+name+"&appid=291b3a14d9367e0fff371be2a1912b44";
                        //url="http://api.openweathermap.org/data/2.5/weather?q="+name+"&appid=291b3a14d9367e0fff371be2a1912b44";
                        getWeather(url);
                        // mAdapter = new RecylerviewAdapter(WeatherForcast.this,wheatherDataList);
                        // mRecyclerView.setAdapter(mAdapter);
                        //mAdapter.notifyDataSetChanged();
//                        mRecyclerView.setAdapter(new RecylerviewAdapter(WeatherForcast.this,wheatherDataList));

//                        mRecyclerView.invalidate();
                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Please Enter city name",Toast.LENGTH_SHORT).show();
                    }



                }

                return true;
            }
        });




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


        //done = (Button) findViewById(R.id.done);
        //result = (TextView) findViewById(R.id.weatherinfo);
        //currentLocation = (TextView) findViewById(R.id.currentLocation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                // Got last known location. In some rare situations this can be null.
//                if (location != null) {
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//
//                    try {
//                        addresses = geocoder.getFromLocation(lat, lng, 1);
//                        String city = addresses.get(0).getLocality();
//                        Log.i("curreentcity",city);
//                        cityname.setText(city);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    Log.i("lastknownLocation1", String.valueOf(location.getLatitude()) + "  " + String.valueOf(location.getLongitude()));
//
//                        url = "http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lng+"&appid=291b3a14d9367e0fff371be2a1912b44";
//                        getWeather(url);
//                    mAdapter = new RecylerviewAdapter(WeatherForcast.this,wheatherDataList);
//                    mRecyclerView.setAdapter(mAdapter);
////                    }
////                    else {
////                        Toast.makeText(getApplicationContext(),"Current Location Not Found",Toast.LENGTH_SHORT).show();
////                    }
//
//
//                    // ...
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Please turn on GPS",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });


        // Calling the RecyclerView















        //Log.i("latlng", String.valueOf(lat) + " " + String.valueOf(lng));
//        currentLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //url ="http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&appid=291b3a14d9367e0fff371be2a1912b44";
//
//            }
//        });



//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent intent = new Intent(getApplicationContext(),WeatherForecastList.class);
//                intent.setAction("WeatherForcast");
//                intent.putStringArrayListExtra("list",list);
//                startActivity(intent);
//            }
//        });

    }
    public void getWeather(String url){
//        final String[] message = {""};
//        final String[] description = {null};
//        final String[] main = {null};
       // url = "http://api.openweathermap.org/data/2.5/weather?q="+cityname+"&appid=291b3a14d9367e0fff371be2a1912b44";
//       //for 5 days
//        url = "http://api.openweathermap.org/data/2.5/forecast?q="+cityname+"&appid=291b3a14d9367e0fff371be2a1912b44";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        wheatherDataList.clear();
                        //mTxtDisplay.setText("Response: " + response.toString());
                        String message="",main=null,description=null;
                        Log.i("response","called");
                        Log.i("response", response.toString());
                        String weatherinfo= null;
                        try {
                            //weatherinfo = response.getString("weather");
                            weatherinfo = response.getString("list");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("weatherinfo",weatherinfo);
                        JSONArray array= null;
                        try {
                            array = new JSONArray(weatherinfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for(int i=0;i<array.length();i++) {
                            JSONObject jsonPart = null;
                            try {
                                jsonPart = array.getJSONObject(i);

                                Log.i("date", jsonPart.getString("dt_txt"));
                                String date = jsonPart.getString("dt_txt");
                                String object = jsonPart.getString("main");
                                JSONObject object1 = new JSONObject(object);
                                Log.i("temp", object1.getString("temp"));
                                String temp = object1.getString("temp");
                                String humidity = object1.getString("humidity");
                                String pressure = object1.getString("pressure");
                                Log.i("humidity", object1.getString("humidity"));
                                Log.i("pressure", object1.getString("pressure"));

                                String weather = jsonPart.getString("weather");
                                JSONArray array1 = new JSONArray(weather);
                                for (int j = 0; j < array1.length(); j++) {
                                    JSONObject jsonPart1 = array1.getJSONObject(j);
                                    weatherModel = new WeatherModel();
                                    main = jsonPart1.getString("main");
                                    Log.i("main", jsonPart1.getString("main"));
                                    description = jsonPart1.getString("description");
                                    Log.i("description", jsonPart1.getString("description"));
                                    message += main + ":" + description + "\r\n";
                                    String listcontent = main + ":" + description;
                                    list.add(listcontent);
                                    weatherModel.setWeatherinfo(description);
                                    weatherModel.setDate(date);
                                    Log.i("date1", weatherModel.getDate());
                                    weatherModel.setHumidity(humidity);
                                    weatherModel.setPressure(pressure);
                                    weatherModel.setTemp(temp);
                                    wheatherDataList.add(weatherModel);


                                }
                                mRecyclerView.setAdapter(new RecylerviewAdapter(WeatherForcast.this,wheatherDataList));
                               // mAdapter.notifyDataSetChanged();

//                                main =jsonPart.getString("main");
//                                Log.i("main",jsonPart.getString("main"));
//                                description =jsonPart.getString("description");
//                                Log.i("description",jsonPart.getString("description"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

//                        }mRecyclerView.setAdapter(new RecylerviewAdapter(WeatherForcast.this,wheatherDataList));
                       // mRecyclerView.setAdapter(mAdapter);


                        //Log.i("message",message.toString());
                       // result.setText(list.get(0));
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
               // mDrawerLayout.closeDrawers();
                Intent intent = new Intent(getApplicationContext(),NewsActivity.class);
                startActivity(intent);
                finish();
            }
            if(position==1){
               // mDrawerLayout.closeDrawers();

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                finish();
            }
            if(position==2){
                mDrawerLayout.closeDrawers();
// Intent intent = new Intent(getApplicationContext(),WeatherForcast.class);
//                startActivity(intent);
            return;
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



}
