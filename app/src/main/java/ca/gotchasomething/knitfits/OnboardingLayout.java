package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class OnboardingLayout extends AppCompatActivity {

    Intent i, i2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            i = new Intent(this, OnBoardingLayoutP.class);
            startActivity(i);
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            i2 = new Intent(this, OnBoardingLayoutL.class);
            startActivity(i2);
        }
    }
}

