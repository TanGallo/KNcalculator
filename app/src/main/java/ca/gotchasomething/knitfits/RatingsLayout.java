package ca.gotchasomething.knitfits;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RatingsLayout extends NewProjectsLayout {

    TextView ratingsLabel, ratingsYesLabel, ratingsNoLabel, temp, tempE;
    Button enjoyNoButton, enjoyNotSureButton, enjoyYesButton, rateNoButton, rateYesButton, emailNoButton, emailYesButton;
    Intent backToCalculator, email, goToRatings;
    public static int clicked = 0;
    public static int clickedE = 0;
    public static final String SRP = "shared ratings pref";
    public static final String CT = String.valueOf(clicked);
    String clicksS;
    public static final String SRPE = "shared ratings pref";
    public static final String CTE = String.valueOf(clickedE);
    String clicksES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratings_layout);

        ratingsLabel = findViewById(R.id.ratingsLabel);
        ratingsNoLabel = findViewById(R.id.ratingsNoLabel);
        ratingsNoLabel.setVisibility(View.GONE);
        ratingsYesLabel = findViewById(R.id.ratingsYesLabel);
        ratingsYesLabel.setVisibility(View.GONE);
        enjoyNoButton = findViewById(R.id.enjoyNoButton);
        enjoyYesButton = findViewById(R.id.enjoyYesButton);
        enjoyNotSureButton = findViewById(R.id.enjoyNotSureButton);
        rateNoButton = findViewById(R.id.rateNoButton);
        rateYesButton = findViewById(R.id.rateYesButton);
        emailNoButton = findViewById(R.id.emailNoButton);
        emailYesButton = findViewById(R.id.emailYesButton);
        rateNoButton.setVisibility(View.GONE);
        rateYesButton.setVisibility(View.GONE);
        emailNoButton.setVisibility(View.GONE);
        emailYesButton.setVisibility(View.GONE);
        temp = findViewById(R.id.temp);
        temp.setVisibility(View.GONE);
        temp.setText(String.valueOf(clicked));
        tempE = findViewById(R.id.tempE);
        tempE.setVisibility(View.GONE);
        tempE.setText(String.valueOf(clickedE));

        enjoyNoButton.setOnClickListener(onClickEnjoyNoButton);
        enjoyYesButton.setOnClickListener(onClickEnjoyYesButton);
        enjoyNotSureButton.setOnClickListener(onClickEnjoyNotSureButton);

        loadClicks();
        updateClicks();
        loadClicksE();
        updateClicksE();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(String.valueOf(clicked), temp.getText().toString());
        super.onSaveInstanceState(savedInstanceState);

    }

    View.OnClickListener onClickEnjoyNoButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ratingsLabel.setVisibility(View.GONE);
            enjoyNoButton.setVisibility(View.GONE);
            enjoyNotSureButton.setVisibility(View.GONE);
            enjoyYesButton.setVisibility(View.GONE);
            ratingsNoLabel.setVisibility(View.VISIBLE);
            emailNoButton.setVisibility(View.VISIBLE);
            emailYesButton.setVisibility(View.VISIBLE);

            emailNoButton.setOnClickListener(onClickEmailNoButton);
            emailYesButton.setOnClickListener(onClickEmailYesButton);
        }
    };

    View.OnClickListener onClickEnjoyYesButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ratingsLabel.setVisibility(View.GONE);
            enjoyNoButton.setVisibility(View.GONE);
            enjoyNotSureButton.setVisibility(View.GONE);
            enjoyYesButton.setVisibility(View.GONE);
            ratingsYesLabel.setVisibility(View.VISIBLE);
            rateNoButton.setVisibility(View.VISIBLE);
            rateYesButton.setVisibility(View.VISIBLE);

            rateNoButton.setOnClickListener(onClickRateNoButton);
            rateYesButton.setOnClickListener(onClickRateYesButton);

        }
    };

    View.OnClickListener onClickEnjoyNotSureButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(getBaseContext(), R.string.ask_later,
                    Toast.LENGTH_LONG).show();

            backToCalculator = new Intent(RatingsLayout.this, CalculatorLayout.class);
            backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(backToCalculator);
        }
    };

    View.OnClickListener onClickRateNoButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(getBaseContext(), R.string.ask_later,
                    Toast.LENGTH_LONG).show();

            backToCalculator = new Intent(RatingsLayout.this, CalculatorLayout.class);
            backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(backToCalculator);

        }
    };

    View.OnClickListener onClickRateYesButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            clicked++;
            temp.setText(String.valueOf(clicked));

            saveClicks();

            backToCalculator = new Intent(RatingsLayout.this, CalculatorLayout.class);
            backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(backToCalculator);

            goToRatings = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google_play_url)));
            goToRatings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(goToRatings);
        }

    };

    View.OnClickListener onClickEmailNoButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(getBaseContext(), R.string.ask_later,
                    Toast.LENGTH_LONG).show();

            backToCalculator = new Intent(RatingsLayout.this, CalculatorLayout.class);
            backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(backToCalculator);

        }
    };

    View.OnClickListener onClickEmailYesButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            clickedE++;
            tempE.setText(String.valueOf(clickedE));

            saveClicksE();

            backToCalculator = new Intent(RatingsLayout.this, CalculatorLayout.class);
            backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(backToCalculator);

            email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"gotchasomething.ca@gmail.com"});

            try {
                startActivity(Intent.createChooser(email, getString(R.string.choose_email)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getBaseContext(), getString(R.string.email_warning), Toast.LENGTH_LONG).show();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                email.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            } else {
                email.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            }

        }
    };

    public void saveClicks() {
        SharedPreferences sp = getSharedPreferences(SRP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CT, temp.getText().toString());
        clicked = Integer.valueOf(temp.getText().toString());

        editor.apply();
    }

    public void loadClicks() {
        SharedPreferences sp = getSharedPreferences(SRP, MODE_PRIVATE);
        clicksS = sp.getString(CT, "");

    }

    public void updateClicks() {
        temp.setText(clicksS);

        if (!clicksS.equals("")) {
            clicked = Integer.valueOf(clicksS);
        } else {
            clicked = 0;
        }

    }

    public void saveClicksE() {
        SharedPreferences spE = getSharedPreferences(SRPE, MODE_PRIVATE);
        SharedPreferences.Editor editorE = spE.edit();
        editorE.putString(CTE, tempE.getText().toString());
        clickedE = Integer.valueOf(tempE.getText().toString());

        editorE.apply();
    }

    public void loadClicksE() {
        SharedPreferences spE = getSharedPreferences(SRPE, MODE_PRIVATE);
        clicksES = spE.getString(CTE, "");

    }

    public void updateClicksE() {
        tempE.setText(clicksES);

        if (!clicksES.equals("")) {
            clickedE = Integer.valueOf(clicksES);
        } else {
            clickedE = 0;
        }

    }

}
