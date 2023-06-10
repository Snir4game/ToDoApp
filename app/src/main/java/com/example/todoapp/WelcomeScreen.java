package com.example.todoapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        // קריאה של אובייקט Handler
        Handler handler = new Handler();
        // שימוש בפונקציה שמאפשרת דיךי של כמה שניות ןהיא מקבלת 2 פרמטרים מאיפה להתחיל ולאן לעבור
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // MainActivityתתחיל במסך של WelcomeScreen ואז תעבור
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                finish(); // סגירה של הפעילות של מסך WelcomeScreen
            }
        }, 4000); // תעבור למסך הבא אחרי 4 שניות
    }
}
