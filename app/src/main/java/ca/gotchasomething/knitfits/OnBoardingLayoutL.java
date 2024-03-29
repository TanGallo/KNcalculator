package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.ViewPager;

public class OnBoardingLayoutL extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    private int[] slides2 = {
            R.layout.onboarding_landscape_1,
            R.layout.onboarding_landscape_2,
            R.layout.onboarding_landscape_3,
            R.layout.onboarding_landscape_4,
            R.layout.onboarding_landscape_5};
    private OnboardingAdapter adapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private Button skipButton, nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferenceManager(this).checkPreferences()) {
            loadHome();
        }

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.onboarding_layout_landscape);

        viewPager = findViewById(R.id.viewPager);
        adapter = new OnboardingAdapter(slides2, this);
        viewPager.setAdapter(adapter);

        dotsLayout = findViewById(R.id.dotsLayout);
        skipButton = findViewById(R.id.skipButton);
        nextButton = findViewById(R.id.nextButton);
        skipButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        createDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);

                if (position == slides2.length - 1) {
                    nextButton.setText(getResources().getString(R.string.onboarding_start_button));
                    skipButton.setVisibility(View.INVISIBLE);
                } else {
                    nextButton.setText(getResources().getString(R.string.onboarding_next_button));
                    skipButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots(int current_position) {
        if (dotsLayout != null)
            dotsLayout.removeAllViews();

        dots = new ImageView[slides2.length];

        for (int i = 0; i < slides2.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(
                        AppCompatResources.getDrawable(
                                this, R.drawable.active_dots2));
            } else {
                dots[i].setImageDrawable(
                        AppCompatResources.getDrawable(
                                this, R.drawable.default_dots2));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            dotsLayout.addView(dots[i], params);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextButton:
                loadNextSlide();
                break;

            case R.id.skipButton:
                loadHome();
                new PreferenceManager(this).writePreferences();
                break;
        }
    }

    private void loadHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void loadNextSlide() {
        int next = viewPager.getCurrentItem() + 1;

        if (next < slides2.length) {
            viewPager.setCurrentItem(next);
        } else {
            loadHome();
            new PreferenceManager(this).writePreferences();
        }
    }
}
