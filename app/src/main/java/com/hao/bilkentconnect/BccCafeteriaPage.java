package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
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
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BccCafeteriaPage extends AppCompatActivity {
    private ActivityBccCafeteriaPageBinding binding;
    private ConstraintLayout bccLayout;
    private boolean isSideMenuVisible = false;
    private LinearLayout sideMenu;
    private TextView lunchTextView;
    private TextView dinnerTextView;
    private String[] lunchMeals = new String[7];
    private String[] dinnerMeals = new String[7];

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

        // Replace this URL with the URL of the HTML page you want to parse
        String url = "http://kafemud.bilkent.edu.tr/monu_eng.html";

        // Execute the AsyncTask to perform HTML parsing in the background
        parseHtmlInBackground(url,"td.style18", findViewById(R.id.lunchText), 0, 0);
        parseHtmlInBackground(url,"td.style18", findViewById(R.id.dinnerText), 1, 0);
        parseHtmlInBackground(url,"td", findViewById(R.id.lunchInfoText), 0, 1);
        parseHtmlInBackground(url,"td", findViewById(R.id.dinnerInfoText), 1, 1);
        //parseHtmlInBackground(url, dinnerTextView);
    }


    private void parseHtmlInBackground(String url, String targetElement, TextView targetLocation, int mealType, int info) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> futureResult = executor.submit(() -> {
            try {
                // Fetch the HTML content from the URL
                Document document = Jsoup.connect(url).get();

                // Extract information from the HTML
                Elements headlines = document.select(targetElement);

                LocalDate currentDate = LocalDate.now();
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

                // Build a string with the extracted information
                StringBuilder result = new StringBuilder();

                if ( dayOfWeek.toString().equals("MONDAY") ){
                    result.append(headlines.get(info*(6 + mealType) + mealType));
                }
                else if ( dayOfWeek.toString().equals("TUESDAY") ){
                    result.append(headlines.get(info*(11 + mealType) + mealType + 2).text());
                }
                else if ( dayOfWeek.toString().equals("WEDNESDAY") ){
                    result.append(headlines.get(info*(16 + mealType) + mealType + 4).text());
                }
                else if ( dayOfWeek.toString().equals("THURSDAY") ){
                    result.append(headlines.get(info*(21 + mealType) + mealType + 6).text());
                }
                else if ( dayOfWeek.toString().equals("FRIDAY") ){
                    result.append(headlines.get(info*(26 + mealType) + mealType + 8).text());
                }
                else if ( dayOfWeek.toString().equals("SATURDAY") ){
                    result.append(headlines.get(info*(31 + mealType) + mealType + 10).text());
                }
                else if ( dayOfWeek.toString().equals("SUNDAY") ){
                    result.append(headlines.get(info*(36 + mealType) + mealType + 12).text());
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
            targetLocation.setText(futureResult.get());
            Log.d("", futureResult.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToChatPage(View view){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
    public void goToSharePostActivity(View view){
        Intent intent = new Intent(this, SharePostsActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view){
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

    public void goToSecondHandSalePageFromBcc(View view){
        Intent intent = new Intent(this, SecondHandMain.class);
        startActivity(intent);
    }

    public void goToLunchCommentsPage(View view){
        Intent intent = new Intent(this, MealCommentActivity.class);
        intent.putExtra("mealType", "Öğlen Yemeği/Lunch");
        intent.putExtra("textColor", Color.parseColor("#FF5722"));
        startActivity(intent);
    }

    public void goToDinnerCommentsPage(View view){
        Intent intent = new Intent(this, MealCommentActivity.class);
        intent.putExtra("mealType", "Akşam Yemeği/Dinner");
        intent.putExtra("textColor", Color.parseColor("#0D99FF"));
        startActivity(intent);
    }
}