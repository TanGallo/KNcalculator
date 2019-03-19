package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends MainNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    public void loadSlides(View view) {

        new PreferenceManager(this).clearPreferences();
        startActivity(new Intent(this, OnboardingLayout.class));
        finish();
        }
}
