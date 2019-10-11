package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

abstract class MainNavigation extends AppCompatActivity {

    protected BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_new_project:
                    Intent intent1 = new Intent(MainNavigation.this, NewProjectsLayout.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent1);
                    break;
                case R.id.navigation_my_projects:
                    Intent intent2 = new Intent(MainNavigation.this, MyProjectsLayout.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent2);
                    break;
                case R.id.navigation_calculator:
                    Intent intent3 = new Intent(MainNavigation.this, CalculatorLayout.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent3);
                    break;
                case R.id.navigation_help:
                    Intent intent4 = new Intent(MainNavigation.this, HelpLayout.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent4);
                    break;
            }
            return false;
        }
    };
    BottomNavigationView navigation;
}
