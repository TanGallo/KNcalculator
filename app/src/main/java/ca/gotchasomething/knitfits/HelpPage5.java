package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpPage5 extends Fragment {

    View v;
    TextView websiteURL, emailAddress, ratingsLabel;
    LinearLayout ratingsLayout;
    long id;
    String name;
    Intent goToRatings, web, email;

    public HelpPage5() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help_page5, container, false);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        websiteURL = v.findViewById(R.id.websiteURL);
        websiteURL.setOnClickListener(onClickWebsiteURL);
        emailAddress = v.findViewById(R.id.emailAddress);
        emailAddress.setOnClickListener(onClickEmailAddress);
        ratingsLabel = v.findViewById(R.id.ratingsLabel);
        ratingsLabel.setOnClickListener(onClickRatings);
        ratingsLayout = v.findViewById(R.id.ratingsLayout);
        ratingsLayout.setOnClickListener(onClickRatings);

    }

    View.OnClickListener onClickWebsiteURL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url = "http://www.gotchasomething.ca";

            web = new Intent(Intent.ACTION_VIEW);
            web.setData(Uri.parse(url));
            startActivity(web);
        }
    };

    View.OnClickListener onClickEmailAddress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"gotchasomething.ca@gmail.com"});

            try {
                startActivity(Intent.createChooser(email, "Choose an Email client"));
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(getActivity(), getString(R.string.email_warning), Toast.LENGTH_LONG).show();
            }
        }
    };

    View.OnClickListener onClickRatings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            goToRatings = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ca.gotchasomething.knitfits"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                goToRatings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            } else {
                goToRatings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(goToRatings);

        }
    };
}
