package com.example.mwojcik.masteringnetworkingtwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView authorTextView;
    private TextView likesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detail_image_view);
        authorTextView = findViewById(R.id.detail_text_view_author);
        likesTextView = findViewById(R.id.detail_text_view_like);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.INTENT_EXTRA_URL);
        String author = intent.getStringExtra(MainActivity.INTENT_EXTRA_AUTHOR);
        int likes = intent.getIntExtra(MainActivity.INTENT_EXTRA_LIKES, 0);

        Picasso.with(this).load(url)
                .fit()
                .centerInside()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}
