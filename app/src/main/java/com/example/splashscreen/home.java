package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    ImageSlider imageSlider;
    ImageView profBtn, calendarBtn, eligible_button, notify_button, text_sms;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        // Initialize views
        imageSlider = findViewById(R.id.imageSlider);
        profBtn = findViewById(R.id.profBtn);
        calendarBtn = findViewById(R.id.calendar_btn);
        eligible_button = findViewById(R.id.eligible_button);
        ImageView book_btn = findViewById(R.id.book_btn);
        ImageView consult = findViewById(R.id.consultation_btn);
        ImageView book = findViewById(R.id.request_consultation);
        ImageView hospitals = findViewById(R.id.hospitals);
        ImageView locationDonating = findViewById(R.id.location_donating);
        notify_button = findViewById(R.id.notify_button);
        text_sms = findViewById(R.id.text_sms);

        // Image slider setup
        ArrayList<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel(R.drawable.pic1, "", null));
        imagelist.add(new SlideModel(R.drawable.pic2, "", null));
        imagelist.add(new SlideModel(R.drawable.pic3, "", null));
        imagelist.add(new SlideModel(R.drawable.pic4, "", null));
        imageSlider.setImageList(imagelist, ScaleTypes.CENTER_CROP);

        // Add animations
        addTouchAnimation(profBtn);
        addTouchAnimation(calendarBtn);
        addTouchAnimation(book_btn);
        addTouchAnimation(consult);
        addTouchAnimation(book);
        addTouchAnimation(hospitals);
        addTouchAnimation(locationDonating);
        addTouchAnimation(notify_button);
        addTouchAnimation(text_sms);

        // Hover effect (optional)
        calendarBtn.setOnHoverListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_HOVER_ENTER:
                    calendarBtn.setAlpha(0.7f);
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                    calendarBtn.setAlpha(1.0f);
                    break;
            }
            return false;
        });

        // Save user email
        String userEmail = getIntent().getStringExtra("user_email");
        if (userEmail != null) {
            getSharedPreferences("user_data", MODE_PRIVATE)
                    .edit()
                    .putString("email", userEmail)
                    .apply();
        }


        profBtn.setOnClickListener(v -> {
            Toast.makeText(home.this, "Profile Button Clicked", Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(home.this, Profile.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(home.this, "Failed to open Profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
        text_sms.setOnClickListener(v -> startActivity(new Intent(home.this, MessagesActivity.class)));
        calendarBtn.setOnClickListener(v -> startActivity(new Intent(home.this, calendar.class)));
        eligible_button.setOnClickListener(v -> startActivity(new Intent(home.this, eligible.class)));
        consult.setOnClickListener(v -> startActivity(new Intent(home.this, ConsultationActivty.class)));

        book_btn.setOnClickListener(v -> {
            bottom_sheet_calendar bottomSheet = new bottom_sheet_calendar();
            bottomSheet.show(getSupportFragmentManager(), "BottomSheetCalendar");
        });

        locationDonating.setOnClickListener(v -> {
            String geoUri = "geo:0,0?q=hospitals or red cross donation centers in Philippines";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/hospitals+or+red+cross+donation+centers+in+Philippines")));
            }
        });

        notify_button.setOnClickListener(v -> {
            startActivity(new Intent(home.this, notification_activity.class));
        });

        // Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addTouchAnimation(ImageView imageView) {
        imageView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return false;
        });
    }
}
