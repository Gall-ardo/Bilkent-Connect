package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.bilkentconnect.databinding.ActivityBccCafeteriaPageBinding;
import com.hao.bilkentconnect.databinding.ActivityMainBinding;
import com.hao.bilkentconnect.databinding.ActivityMealCommentBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BccCafeteriaPage extends AppCompatActivity {
    private ActivityBccCafeteriaPageBinding binding;
    private ConstraintLayout bccLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBccCafeteriaPageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        bccLayout = findViewById(R.id.bccLayout);
        sideMenu = findViewById(R.id.sideMenu);

        ImageView menuIcon = findViewById(R.id.menuIcon);
        menuIcon.setOnClickListener(v -> toggleSideMenu());
        sideMenu.setOnClickListener(v -> onSideMenuItemClick());
        bccLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSideMenuVisible) {
                    toggleSideMenu();
                }
            }
        });

        //gotoMainFragment();

        resultTextView = findViewById(R.id.lunchText);

        // Replace this URL with the URL of the HTML page you want to parse
        String url = "http://kafemud.bilkent.edu.tr/monu_eng.html";

        // Execute the AsyncTask to perform HTML parsing in the background
        parseHtmlInBackground(url);
    }

    private void onSideMenuItemClick() {
        // Handle the click event for side menu items
        Log.d("ClickEvent", "Clicked on: ");

        // Close the side menu after handling the click
        toggleSideMenu();
    }

    public void toggleSideMenu() {
        isSideMenuVisible = !isSideMenuVisible;

        float fromXDelta, toXDelta;

        if (isSideMenuVisible) {
            fromXDelta = -1f; // Start from the left edge
            toXDelta = 0f;
        } else {
            fromXDelta = 0f;
            toXDelta = -1f; // Move back to the left edge
        }

        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromXDelta,
                Animation.RELATIVE_TO_SELF, toXDelta,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
        );

        animation.setDuration(300);
        sideMenu.startAnimation(animation);

        int visibility = isSideMenuVisible ? View.VISIBLE : View.INVISIBLE;
        sideMenu.setVisibility(visibility);
    }

    private static class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            String url = "http://kafemud.bilkent.edu.tr/monu_eng.html";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    // Successful HTTP request
                    return response.body().string();
                } else {
                    // Handle unsuccessful response
                    return "Error: " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Process the result on the UI thread
            // For example, log it or display it in a TextView
            System.out.println(result);
        }
    }

    private void parseHtmlInBackground(String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> futureResult = executor.submit(() -> {
            try {
                // Fetch the HTML content from the URL
                Document document = Jsoup.connect(url).get();

                // Extract information from the HTML
                Elements headlines = document.select("td.style18");

                // Build a string with the extracted information
                StringBuilder result = new StringBuilder();
                for (Element headline : headlines) {
                    result.append("Headline: ").append(headline.text()).append("\n");
                }

                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error occurred during HTML parsing";
            }
        });

        executor.shutdown();

        try {
            // Update the UI with the parsed HTML information
            resultTextView.setText(futureResult.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}