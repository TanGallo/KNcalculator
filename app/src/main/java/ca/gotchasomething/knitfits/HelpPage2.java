package ca.gotchasomething.knitfits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpPage2 extends Fragment {

    ImageButton exButton1, exButton1b, exButton2, exButton2b, exButton3, exButton3b;
    View v;
    TextView help2TextC2, help2TextD2, help2TextF;
    RelativeLayout ex1Layout, ex2Layout, ex3Layout;

    public HelpPage2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_help_page2, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exButton1 = v.findViewById(R.id.exButton1);
        exButton1.setOnClickListener(onClickExButton1);
        exButton1b = v.findViewById(R.id.exButton1b);
        exButton1b.setVisibility(View.INVISIBLE);
        exButton1b.setOnClickListener(onClickExButton1b);
        help2TextC2 = v.findViewById(R.id.help2TextC2);
        help2TextC2.setVisibility(View.GONE);
        ex1Layout = v.findViewById(R.id.ex1Layout);
        ex1Layout.setVisibility(View.GONE);
        exButton2 = v.findViewById(R.id.exButton2);
        exButton2.setOnClickListener(onClickExButton2);
        exButton2b = v.findViewById(R.id.exButton2b);
        exButton2b.setVisibility(View.INVISIBLE);
        exButton2b.setOnClickListener(onClickExButton2b);
        help2TextD2 = v.findViewById(R.id.help2TextD2);
        help2TextD2.setVisibility(View.GONE);
        ex2Layout = v.findViewById(R.id.ex2Layout);
        ex2Layout.setVisibility(View.GONE);
        exButton3 = v.findViewById(R.id.exButton3);
        exButton3.setOnClickListener(onClickExButton3);
        exButton3b = v.findViewById(R.id.exButton3b);
        exButton3b.setVisibility(View.INVISIBLE);
        exButton3b.setOnClickListener(onClickExButton3b);
        help2TextF = v.findViewById(R.id.help2TextF);
        help2TextF.setVisibility(View.GONE);
        ex3Layout = v.findViewById(R.id.ex3Layout);
        ex3Layout.setVisibility(View.GONE);
    }

    View.OnClickListener onClickExButton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextC2.setVisibility(View.VISIBLE);
            ex1Layout.setVisibility(View.VISIBLE);
            exButton1b.setVisibility(View.VISIBLE);
            exButton1.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickExButton1b = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextC2.setVisibility(View.GONE);
            ex1Layout.setVisibility(View.GONE);
            exButton1b.setVisibility(View.INVISIBLE);
            exButton1.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickExButton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextD2.setVisibility(View.VISIBLE);
            ex2Layout.setVisibility(View.VISIBLE);
            exButton2b.setVisibility(View.VISIBLE);
            exButton2.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickExButton2b = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextD2.setVisibility(View.GONE);
            ex2Layout.setVisibility(View.GONE);
            exButton2b.setVisibility(View.INVISIBLE);
            exButton2.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickExButton3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextF.setVisibility(View.VISIBLE);
            ex3Layout.setVisibility(View.VISIBLE);
            exButton3b.setVisibility(View.VISIBLE);
            exButton3.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickExButton3b = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help2TextF.setVisibility(View.GONE);
            ex3Layout.setVisibility(View.GONE);
            exButton3b.setVisibility(View.INVISIBLE);
            exButton3.setVisibility(View.VISIBLE);

        }
    };
}
