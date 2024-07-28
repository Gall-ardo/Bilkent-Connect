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

        String url = "http://kafemud.bilkent.edu.tr/monu_eng.html";

        parseHtmlInBackground(url, findViewById(R.id.lunchText), findViewById(R.id.lunchInfoText), 0);
        parseHtmlInBackground(url, findViewById(R.id.dinnerText), findViewById(R.id.dinnerInfoText), 1);
    }




    private void parseHtmlInBackground(String url, TextView mealView, TextView calorieView, int mealType) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String[]> futureResult = executor.submit(() -> {
            try {
                Document document = Jsoup.connect(url).get();

                LocalDate currentDate = LocalDate.now();
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
                int dayIndex = dayOfWeek.getValue() - 1;

                Elements rows = document.select("tr");
                int rowIndex = dayIndex * 12 + 2 + mealType * 6;

                String mealName = "";
                for (int i = 7; i < 12; i++)
                {
                    mealName += rows.get(rowIndex + i).select("td").text() + "\n";
                }
                String calorieInfo = rows.get(rowIndex + 6).select("td").text();

                return new String[] { mealName, calorieInfo };
            } catch (IOException e) {
                e.printStackTrace();
                return new String[] { "Error occurred during HTML parsing", "" };
            }
        });

        executor.shutdown();

        try {
            String[] result = futureResult.get();
            String mealName = result[0];
            String calorieInfo = result[1];

            runOnUiThread(() -> {
                mealView.setText(mealName);
                calorieView.setText(calorieInfo);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSideMenuItemClick() {
        Log.d("ClickEvent", "Clicked on: ");

        toggleSideMenu();
    }

    public void toggleSideMenu() {
        isSideMenuVisible = !isSideMenuVisible;

        float fromXDelta, toXDelta;

        if (isSideMenuVisible) {
            fromXDelta = -1f;
            toXDelta = 0f;
        } else {
            fromXDelta = 0f;
            toXDelta = -1f;
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