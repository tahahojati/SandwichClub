package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
        setTvBoldLabelAndText(R.string.detail_place_of_origin_label, R.id.origin_tv, mSandwich.getPlaceOfOrigin(), " ");
        setTvBoldLabelAndText(R.string.detail_description_label, R.id.description_tv, mSandwich.getDescription(), "\n");
        setTvBoldLabelAndText(R.string.detail_ingredients_label, R.id.ingredients_tv,
                collectionToString(mSandwich.getIngredients(), ", "), "\n");
        setTvBoldLabelAndText(R.string.detail_also_known_as_label, R.id.also_known_tv,
                collectionToString(mSandwich.getAlsoKnownAs(), ", "), " ");
    }

    private String collectionToString(@NonNull List<String> list, @NonNull String delimiter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.join(delimiter, list);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < list.size(); ++index) {
                sb.append(list.get(index));
                if (index != list.size() - 1) {
                    sb.append(delimiter);
                }
            }
            return sb.toString();
        }
    }

    private void setTvBoldLabelAndText(int labelStringId, int tvResId, String valueText, @Nullable String separator) {
        String label = getString(labelStringId);
        SpannableStringBuilder ssb = new SpannableStringBuilder(label);
        if (separator != null) {
            ssb.append(separator);
        }
        ssb.append(valueText);

        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length() + separator.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(tvResId)).setText(ssb);
    }
}
