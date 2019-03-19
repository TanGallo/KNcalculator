package ca.gotchasomething.knitfits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class HelpPage3 extends Fragment {

    View v;
    ImageButton castOnExButton1, castOnExButton2, incDecExButton1, incDecExButton2, rowExButton1, rowExButton2, lengthExButton1, lengthExButton2,
            exButton6, exButton6b, exButton7, exButton7b;
    TextView castOnExText, incDecExText, rowExText, lengthExText,
            help3TextH2, help3TextI2;
    RadioButton multRadioButton2, multRadioButton3;
    LinearLayout ex4Layout, ex5Layout;

    public HelpPage3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help_page3, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        castOnExButton1 = v.findViewById(R.id.castOnExButton1);
        castOnExButton1.setOnClickListener(onClickCastOnExButton1);
        castOnExButton2 = v.findViewById(R.id.castOnExButton2);
        castOnExButton2.setVisibility(View.INVISIBLE);
        castOnExButton2.setOnClickListener(onClickCastOnExButton2);
        incDecExButton1 = v.findViewById(R.id.incDecExButton1);
        incDecExButton1.setOnClickListener(onClickIncDecExButton1);
        incDecExButton2 = v.findViewById(R.id.incDecExButton2);
        incDecExButton2.setVisibility(View.INVISIBLE);
        incDecExButton2.setOnClickListener(onClickIncDecExButton2);
        rowExButton1 = v.findViewById(R.id.rowExButton1);
        rowExButton1.setOnClickListener(onClickRowExButton1);
        rowExButton2 = v.findViewById(R.id.rowExButton2);
        rowExButton2.setVisibility(View.INVISIBLE);
        rowExButton2.setOnClickListener(onClickRowExButton2);
        lengthExButton1 = v.findViewById(R.id.lengthExButton1);
        lengthExButton1.setOnClickListener(onClickLengthExButton1);
        lengthExButton2 = v.findViewById(R.id.lengthExButton2);
        lengthExButton2.setVisibility(View.INVISIBLE);
        lengthExButton2.setOnClickListener(onClickLengthExButton2);
        castOnExText = v.findViewById(R.id.castOnExText);
        castOnExText.setVisibility(View.GONE);
        incDecExText = v.findViewById(R.id.incDecExText);
        incDecExText.setVisibility(View.GONE);
        rowExText = v.findViewById(R.id.rowExText);
        rowExText.setVisibility(View.GONE);
        lengthExText = v.findViewById(R.id.lengthExText);
        lengthExText.setVisibility(View.GONE);
        exButton6 = v.findViewById(R.id.exButton6);
        exButton6.setOnClickListener(onClickExButton6);
        exButton6b = v.findViewById(R.id.exButton6b);
        exButton6b.setVisibility(View.INVISIBLE);
        exButton6b.setOnClickListener(onClickExButton6b);
        help3TextH2 = v.findViewById(R.id.help3TextH2);
        help3TextH2.setVisibility(View.GONE);
        ex4Layout = v.findViewById(R.id.ex4Layout);
        ex4Layout.setVisibility(View.GONE);
        exButton7 = v.findViewById(R.id.exButton7);
        exButton7.setOnClickListener(onClickExButton7);
        exButton7b = v.findViewById(R.id.exButton7b);
        exButton7b.setVisibility(View.INVISIBLE);
        exButton7b.setOnClickListener(onClickExButton7b);
        help3TextI2 = v.findViewById(R.id.help3TextI2);
        help3TextI2.setVisibility(View.GONE);
        ex5Layout = v.findViewById(R.id.ex5Layout);
        ex5Layout.setVisibility(View.GONE);
        multRadioButton2 = v.findViewById(R.id.multRadioButton2);
        multRadioButton2.setChecked(true);
        multRadioButton3 = v.findViewById(R.id.multRadioButton3);
        multRadioButton3.setChecked(true);
    }

    View.OnClickListener onClickCastOnExButton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            castOnExText.setVisibility(View.VISIBLE);
            castOnExButton2.setVisibility(View.VISIBLE);
            castOnExButton1.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickCastOnExButton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            castOnExText.setVisibility(View.GONE);
            castOnExButton2.setVisibility(View.INVISIBLE);
            castOnExButton1.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickIncDecExButton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            incDecExText.setVisibility(View.VISIBLE);
            incDecExButton2.setVisibility(View.VISIBLE);
            incDecExButton1.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickIncDecExButton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            incDecExText.setVisibility(View.GONE);
            incDecExButton2.setVisibility(View.INVISIBLE);
            incDecExButton1.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickRowExButton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            rowExText.setVisibility(View.VISIBLE);
            rowExButton2.setVisibility(View.VISIBLE);
            rowExButton1.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickRowExButton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            rowExText.setVisibility(View.GONE);
            rowExButton2.setVisibility(View.INVISIBLE);
            rowExButton1.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickLengthExButton1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            lengthExText.setVisibility(View.VISIBLE);
            lengthExButton2.setVisibility(View.VISIBLE);
            lengthExButton1.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickLengthExButton2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            lengthExText.setVisibility(View.GONE);
            lengthExButton2.setVisibility(View.INVISIBLE);
            lengthExButton1.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickExButton6 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help3TextH2.setVisibility(View.VISIBLE);
            ex4Layout.setVisibility(View.VISIBLE);
            exButton6b.setVisibility(View.VISIBLE);
            exButton6.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickExButton6b = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help3TextH2.setVisibility(View.GONE);
            ex4Layout.setVisibility(View.GONE);
            exButton6b.setVisibility(View.INVISIBLE);
            exButton6.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener onClickExButton7 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help3TextI2.setVisibility(View.VISIBLE);
            ex5Layout.setVisibility(View.VISIBLE);
            exButton7b.setVisibility(View.VISIBLE);
            exButton7.setVisibility(View.INVISIBLE);

        }
    };

    View.OnClickListener onClickExButton7b = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            help3TextI2.setVisibility(View.GONE);
            ex5Layout.setVisibility(View.GONE);
            exButton7b.setVisibility(View.INVISIBLE);
            exButton7.setVisibility(View.VISIBLE);

        }
    };
}
