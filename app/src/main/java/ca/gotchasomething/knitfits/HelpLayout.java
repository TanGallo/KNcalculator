package ca.gotchasomething.knitfits;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class HelpLayout extends MainNavigation {

    TabLayout tl;
    LinearLayout container;
    FragmentManager fm;
    android.support.v4.app.FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);

        navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        tl = findViewById(R.id.tab_layout);
        container = findViewById(R.id.fragment_container);

        tl.addTab(tl.newTab().setIcon(R.drawable.ic_add_circle_outline_teal24dp));
        tl.addTab(tl.newTab().setIcon(R.drawable.ic_developer_mode_teal_24dp));
        tl.addTab(tl.newTab().setIcon(R.drawable.ic_favorite_border_teal_24dp));
        tl.addTab(tl.newTab().setText(R.string.contact));

        replaceFragment(new HelpPage2());
        tl.addOnTabSelectedListener(onTabSelectedListener);
    }

    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            if (tab.getPosition() == 0) {
                replaceFragment(new HelpPage2());
            } else if (tab.getPosition() == 1) {
                replaceFragment(new HelpPage3());
            } else if (tab.getPosition() == 2) {
                replaceFragment(new HelpPage4());
            } else if (tab.getPosition() == 3) {
                replaceFragment(new HelpPage5());
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void replaceFragment(Fragment fragment) {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }
}
