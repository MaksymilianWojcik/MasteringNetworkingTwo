package com.example.mwojcik.masteringnetworkingtwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import java.util.List;

/***
 * 1. Dodajemy dependency do gradla i pozwolenia do manifestu
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<SimpleItem> mSimpleItemList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSimpleItemList = new ArrayList<>();

//        mRequestQueue = Volley.newRequestQueue(this);
        //Po utworzeniu Singletona:
        mRequestQueue = VolleySingleton.getInstance(this).getmRequestQueue();

        fetchData();
    }


    private void fetchData() {
        final String url = "https://pixabay.com/api/?key=10237193-1994dc03c860f7d973701a7cc&q=yellow+flowers&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VolleyRequest", "VolleyRequest - onResponse()");
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    //foreach nie dziala z JSONArray!
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SimpleItem simpleItem = new SimpleItem(jsonObject.getString("webformatURL"), jsonObject.getString("user"), jsonObject.getInt("likes"));
                        mSimpleItemList.add(simpleItem);
                    }
                    mRecyclerViewAdapter = new RecyclerViewAdapter(mSimpleItemList, MainActivity.this);
                    mRecyclerView.setAdapter(mRecyclerViewAdapter);
                    mRecyclerViewAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
//                    e.printStackTrace();
                    Log.d("VolleyRequest","VolleyRequest - onResponse() parsing json error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyRequest","VolleyRequest - onError() with: " + error.toString());
            }
        });

        mRequestQueue.add(request);
    }

    //zmienne po to, zeby przypadkowo nie pomylic sie z nazwa, to do nich sie odwolamy w DetailActivity
    public static final String INTENT_EXTRA_URL = "imageURL";
    public static final String INTENT_EXTRA_AUTHOR = "author";
    public static final String INTENT_EXTRA_LIKES = "likes";

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        SimpleItem item = mSimpleItemList.get(position);
        intent.putExtra(INTENT_EXTRA_URL, item.getmImageUrl());
        intent.putExtra(INTENT_EXTRA_AUTHOR, item.getmAuthor());
        intent.putExtra(INTENT_EXTRA_LIKES, item.getmLikes());
        startActivity(intent);
    }
}
