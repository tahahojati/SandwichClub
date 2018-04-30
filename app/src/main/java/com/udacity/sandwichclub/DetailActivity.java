package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "DetailActivity";
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        ((TextView) findViewById(R.id.origin_tv)).setText(mSandwich.getPlaceOfOrigin());
        ((TextView) findViewById(R.id.description_tv)).setText(mSandwich.getDescription());
        ((TextView) findViewById(R.id.ingredients_tv)).setText(collectionToString(mSandwich.getIngredients()));
        ((TextView) findViewById(R.id.also_known_tv)).setText(collectionToString(mSandwich.getIngredients()));
    }

    private String collectionToString(List<String> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.join(", ", list);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < list.size(); ++index) {
                sb.append(list.get(index));
                if (index != list.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }
}
