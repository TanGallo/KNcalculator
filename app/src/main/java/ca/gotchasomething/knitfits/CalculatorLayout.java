package ca.gotchasomething.knitfits;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ca.gotchasomething.knitfits.data.ProjectsDbHelper;

public class CalculatorLayout extends MainNavigation {

    boolean same = false, diff = false, visible = false;
    Button nextOrigNumbersButton, nextCustomNumbersButton, changeCustomNumbersButton, nextOrigNumbersLButton,
            changeSameDiffButton, calcButton, calc2Button, calc3Button, calcResButton;
    Cursor cursor;
    Double customSize = 0.0, gli = 0.0, gwi = 0.0, minus = 0.0, multiple = 0.0, pli = 0.0, plr = 0.0, plus = 0.0, pwi = 0.0, pws = 0.0;
    EditText origNumberText, origNumberLText, customSizeText, numberLabel, plusLabel, minusLabel;
    General gen;
    int sameWidth = 0, sameLength = 0, diffWidth = 0, diffLength = 0;
    Intent reset, backToCalculator;
    ProjectsDbHelper projectsDbHelper;
    RadioButton castOnRadioButton, increaseDecreaseRadioButton, lengthRadioButton, widthRadioButton, sameRadioButton,
            diffRadioButton, evenRadioButton, oddRadioButton, multRadioButton, noneApplyRadioButton;
    RadioGroup calcPurposeRadioGroup, sameDiff, conditions;
    SpinnerAdapter sAdapter;
    Spinner spinner;
    SQLiteDatabase db;
    String spinnerString = null;
    TextView origNumberResultText, origNumberLResultText, origIncDecText, finishedSizeLabel, conditionsLabel,
            newNumberLabel, newNumberResult;

    //data captured in spinner selection for use in calculations
    //double pws = 0.0, pwi = 0.0, plr = 0.0, pli = 0.0, gwi = 0.0, gli = 0.0;

    //data captured in layout for use in calculations
    double origNumberW = 0.0, origNumberL = 0.0;
    //double customSize = 0.0;
    //static double multiple = 0.0;
    //static double plus = 0.0;
    //static double minus = 0.0;
    String origNumberSW = null, origNumberSL = null, origIncDecS = null, sameSize = null, evenOdd = null, widthLength = null, mustBeMultiple = null,
            customSizeS = null, multipleS = null, plusS = null, minusS = null, unit;

    //calculated values for use in calculations
    DecimalFormat origNumberDec;
    Double pwM = 0.0, plM = 0.0, gwStperI = 0.0, glRperI = 0.0;
    //static int sameWidth = 0;
    //static int sameLength = 0;
    //static int diffWidth = 0;
    //static int diffLength = 0;
    String type = null;
    int result = 0, resultIncDec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);

        navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        gen = new General();

        origNumberText = findViewById(R.id.origNumberText);
        origNumberText.setVisibility(View.GONE);
        origNumberLText = findViewById(R.id.origNumberLText);
        origNumberLText.setVisibility(View.GONE);
        origIncDecText = findViewById(R.id.origIncDecText);
        origIncDecText.setVisibility(View.GONE);
        origNumberResultText = findViewById(R.id.origNumberResultText);
        origNumberResultText.setVisibility(View.GONE);
        origNumberLResultText = findViewById(R.id.origNumberLResultText);
        origNumberLResultText.setVisibility(View.GONE);
        castOnRadioButton = findViewById(R.id.castOnRadioButton);
        increaseDecreaseRadioButton = findViewById(R.id.increaseDecreaseRadioButton);
        lengthRadioButton = findViewById(R.id.lengthRadioButton);
        widthRadioButton = findViewById(R.id.widthRadioButton);
        nextOrigNumbersButton = findViewById(R.id.nextOrigNumbersButton);
        nextOrigNumbersButton.setVisibility(View.GONE);
        finishedSizeLabel = findViewById(R.id.finishedSizeLabel);
        finishedSizeLabel.setVisibility(View.GONE);
        sameRadioButton = findViewById(R.id.sameRadioButton);
        sameRadioButton.setVisibility(View.GONE);
        diffRadioButton = findViewById(R.id.diffRadioButton);
        diffRadioButton.setVisibility(View.GONE);
        changeSameDiffButton = findViewById(R.id.changeSameDiffButton);
        changeSameDiffButton.setVisibility(View.GONE);
        customSizeText = findViewById(R.id.customSizeText);
        customSizeText.setVisibility(View.GONE);
        numberLabel = findViewById(R.id.numberLabel);
        numberLabel.setVisibility(View.GONE);
        plusLabel = findViewById(R.id.plusLabel);
        plusLabel.setVisibility(View.GONE);
        minusLabel = findViewById(R.id.minusLabel);
        minusLabel.setVisibility(View.GONE);
        calcButton = findViewById(R.id.calcButton);
        calcButton.setVisibility(View.GONE);
        calc2Button = findViewById(R.id.calc2Button);
        calc2Button.setVisibility(View.GONE);
        calc3Button = findViewById(R.id.calc3Button);
        calc3Button.setVisibility(View.GONE);
        calcResButton = findViewById(R.id.calcResButton);
        calcResButton.setVisibility(View.GONE);
        conditionsLabel = findViewById(R.id.conditionsLabel);
        conditionsLabel.setVisibility(View.GONE);
        evenRadioButton = findViewById(R.id.evenRadioButton);
        evenRadioButton.setVisibility(View.GONE);
        oddRadioButton = findViewById(R.id.oddRadioButton);
        oddRadioButton.setVisibility(View.GONE);
        multRadioButton = findViewById(R.id.multRadioButton);
        multRadioButton.setVisibility(View.GONE);
        noneApplyRadioButton = findViewById(R.id.noneApplyRadioButton);
        noneApplyRadioButton.setVisibility(View.GONE);
        nextCustomNumbersButton = findViewById(R.id.nextCustomNumbersButton);
        nextCustomNumbersButton.setVisibility(View.GONE);
        changeCustomNumbersButton = findViewById(R.id.changeCustomNumbersButton);
        changeCustomNumbersButton.setVisibility(View.GONE);
        nextOrigNumbersLButton = findViewById(R.id.nextOrigNumbersLButton);
        nextOrigNumbersLButton.setVisibility(View.GONE);
        newNumberLabel = findViewById(R.id.newNumberLabel);
        newNumberLabel.setVisibility(View.GONE);
        newNumberResult = findViewById(R.id.newNumberResult);
        newNumberResult.setVisibility(View.GONE);
        calcResButton = findViewById(R.id.calcResButton);
        calcResButton.setVisibility(View.GONE);

        nextOrigNumbersButton.setOnClickListener(onClickNextOrigNumbersButton);
        calcButton.setOnClickListener(onClickCalcButton);
        nextOrigNumbersLButton.setOnClickListener(onClickNextOrigNumbersLButton);
        changeCustomNumbersButton.setOnClickListener(onClickChangeCustomNumbersButton);
        nextCustomNumbersButton.setOnClickListener(onClickNextCustomNumbersButton);

        customSizeText.addTextChangedListener(onChangeCustomSize);

        //set spinner for choose project
        spinner = findViewById(R.id.chooseProjectSpinner);
        projectsDbHelper = new ProjectsDbHelper(this);
        db = projectsDbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + ProjectsDbHelper.TABLE_NAME + " ORDER BY " + ProjectsDbHelper.ID + " DESC", null);
        sAdapter = new SpinnerAdapter(this, cursor);
        spinner.setAdapter(sAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //retrieve project data from spinner selection
                pws = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWS)));
                pwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWI)));
                plr = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLR)));
                pli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLI)));
                gwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GWI)));
                gli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GLI)));

                /*String pwsS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWS));
                if (pwsS != null && !pwsS.equals("")) {
                    pws = Double.valueOf(pwsS);
                } else {
                    pws = 0.0;
                }

                String pwiS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWI));
                if (pwiS != null && !pwiS.equals("")) {
                    pwi = Double.valueOf(pwiS);
                } else {
                    pwi = 0.0;
                }

                String plrS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLR));
                if (plrS != null && !plrS.equals("")) {
                    plr = Double.valueOf(plrS);
                } else {
                    plr = 0.0;
                }

                String pliS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLI));
                if (pliS != null && !pliS.equals("")) {
                    pli = Double.valueOf(pliS);
                } else {
                    pli = 0.0;
                }

                String gwiS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GWI));
                if (gwiS != null && !gwiS.equals("")) {
                    gwi = Double.valueOf(gwiS);
                } else {
                    gwi = 0.0;
                }

                String gliS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GLI));
                if (gliS != null && !gliS.equals("")) {
                    gli = Double.valueOf(gliS);
                } else {
                    gli = 0.0;
                }*/

                String unitS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.UNIT));
                if (unitS != null && !unitS.equals("")) {
                    unit = unitS;
                } else {
                    unit = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //handle RadioGroup for question 2
        calcPurposeRadioGroup = findViewById(R.id.calcPurposeRadioGroup);
        calcPurposeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.castOnRadioButton) {

                    increaseDecreaseRadioButton.setVisibility(View.GONE);
                    lengthRadioButton.setVisibility(View.GONE);
                    widthRadioButton.setVisibility(View.GONE);

                    origNumberText.setVisibility(View.VISIBLE);
                    nextOrigNumbersButton.setVisibility(View.VISIBLE);

                    widthLength = "width";

                } else if (checkedId == R.id.increaseDecreaseRadioButton) {

                    castOnRadioButton.setVisibility(View.GONE);
                    lengthRadioButton.setVisibility(View.GONE);
                    widthRadioButton.setVisibility(View.GONE);

                    origIncDecText.setVisibility(View.VISIBLE);
                    calc2Button.setVisibility(View.VISIBLE);

                    widthLength = "width";
                    sameSize = "same";

                    calc2Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            calc2Button.setVisibility(View.GONE);
                            newNumberLabel.setVisibility(View.VISIBLE);
                            newNumberResult.setVisibility(View.VISIBLE);
                            calcResButton.setVisibility(View.VISIBLE);

                            if (unit == null) {
                                Toast.makeText(getBaseContext(), R.string.no_data_warning,
                                        Toast.LENGTH_LONG).show();
                                calc2Button.setVisibility(View.VISIBLE);
                                newNumberLabel.setVisibility(View.GONE);
                                newNumberResult.setVisibility(View.GONE);
                                calcResButton.setVisibility(View.GONE);
                            } else {

                                origIncDecS = origIncDecText.getText().toString();
                                if (!origIncDecS.equals("")) {
                                    origNumberW = Double.valueOf(origIncDecS);

                                } else {
                                    origNumberW = 0.0;
                                    Toast.makeText(getBaseContext(), R.string.no_number_warning,
                                            Toast.LENGTH_LONG).show();
                                    calc2Button.setVisibility(View.VISIBLE);
                                    newNumberLabel.setVisibility(View.GONE);
                                    newNumberResult.setVisibility(View.GONE);
                                    calcResButton.setVisibility(View.GONE);
                                }

                                resultIncDec = (int) Math.round((origNumberW / (pws / pwi)) * (10 / gwi));

                                newNumberLabel.setText(calculateType());
                                newNumberResult.setText(String.valueOf(resultIncDec));
                            }

                            calcResButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    backToCalculator = new Intent(CalculatorLayout.this, CalculatorLayout.class);
                                    backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                    startActivity(backToCalculator);
                                }
                            });
                        }
                    });

                } else if (checkedId == R.id.widthRadioButton) {

                    castOnRadioButton.setVisibility(View.GONE);
                    increaseDecreaseRadioButton.setVisibility(View.GONE);
                    lengthRadioButton.setVisibility(View.GONE);

                    origNumberText.setVisibility(View.VISIBLE);
                    nextOrigNumbersButton.setVisibility(View.VISIBLE);

                    widthLength = "width";

                } else if (checkedId == R.id.lengthRadioButton) {

                    castOnRadioButton.setVisibility(View.GONE);
                    increaseDecreaseRadioButton.setVisibility(View.GONE);
                    widthRadioButton.setVisibility(View.GONE);

                    origNumberLText.setVisibility(View.VISIBLE);
                    nextOrigNumbersLButton.setVisibility(View.VISIBLE);

                    widthLength = "length";
                }
            }
        });

        //handle RadioGroup for question 3
        sameDiff = findViewById(R.id.sameDiff);
        sameDiff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.sameRadioButton) {
                    sameSize = "same";
                    same = true;
                    diff = false;

                    customSizeText.setVisibility(View.GONE);
                    customSize = 0.0;
                    nextCustomNumbersButton.setVisibility(View.GONE);
                    changeCustomNumbersButton.setVisibility(View.GONE);

                    blankConditions();

                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);

                } else if (checkedId == R.id.diffRadioButton) {
                    sameSize = "diff";
                    same = false;
                    diff = true;

                    customSizeText.setVisibility(View.VISIBLE);
                    customSizeText.setText("");
                    nextCustomNumbersButton.setVisibility(View.VISIBLE);

                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);
                    calcButton.setVisibility(View.GONE);
                    calc3Button.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);
                }
            }
        });
    }

    /*public Double spinnerSel(String str1) {
        String string = str1;
        if (string != null && !string.equals("")) {
            spinnerSel = Double.valueOf(string);
        } else {
            spinnerSel = 0.0;
        }
        return spinnerSel;
    }*/

    public String unitUnit() {
        return unit;
    }

    /*public void pwM() {
        origNumberSW = origNumberText.getText().toString();
        if (!origNumberSW.equals("")) {
            origNumberW = Double.valueOf(origNumberSW);

        } else {
            origNumberW = 0.0;
            Toast.makeText(getBaseContext(), R.string.no_number_warning,
                    Toast.LENGTH_LONG).show();
            origNumberResultText.setVisibility(View.GONE);
            goneSameDiff();
            nextOrigNumbersButton.setVisibility(View.VISIBLE);
        }

        pwM = (origNumberW / (pws / pwi));

    }*/

    /*public void plM() {
        origNumberSL = origNumberLText.getText().toString();
        if (!origNumberSL.equals("")) {
            origNumberL = Double.valueOf(origNumberSL);

        } else {
            origNumberL = 0.0;
            Toast.makeText(getBaseContext(), R.string.no_number_warning,
                    Toast.LENGTH_LONG).show();
            origNumberResultText.setVisibility(View.GONE);
            goneSameDiff();
            nextOrigNumbersLButton.setVisibility(View.VISIBLE);
        }

        plM = (origNumberL / (plr / pli));

    }*/


    public int calculateResult() {

        multiple = gen.dblFromSource(numberLabel.getText().toString());
        plus = gen.dblFromSource(plusLabel.getText().toString());
        minus = gen.dblFromSource(minusLabel.getText().toString());

        /*multipleS = numberLabel.getText().toString();
        if (!multipleS.equals("")) {
            multiple = Double.valueOf(multipleS);
        } else {
            multiple = 0.0;
        }

        plusS = plusLabel.getText().toString();
        if (!plusS.equals("")) {
            plus = Double.valueOf(plusS);
        } else {
            plus = 0.0;
        }

        minusS = minusLabel.getText().toString();
        if (!minusS.equals("")) {
            minus = Double.valueOf(minusS);
        } else {
            minus = 0.0;
        }*/

        gwStperI = 10 / gwi;
        glRperI = 10 / gli;

        //sameWidth = (int) Math.round(pwM * gwStperI);
        //sameLength = (int) Math.round(plM * glRperI);

        sameWidth = (int) Math.round((gen.detMeasurement(
                pws,
                pwi,
                origNumberText,
                origNumberResultText,
                finishedSizeLabel,
                sameRadioButton,
                diffRadioButton,
                changeSameDiffButton,
                nextOrigNumbersButton)) * gwStperI);

        sameLength = (int) Math.round((gen.detMeasurement(
                plr,
                pli,
                origNumberLText,
                origNumberLResultText,
                finishedSizeLabel,
                sameRadioButton,
                diffRadioButton,
                changeSameDiffButton,
                nextOrigNumbersLButton)) * glRperI);

        diffWidth = (int) Math.round(customSize * gwStperI);
        diffLength = (int) Math.round(customSize * glRperI);

        if (sameSize == "same")

        {
            if (widthLength == "width") {
                if (evenOdd == "even") {
                    if (sameWidth % 2 != 0) {
                        result = sameWidth + 1;
                    } else {
                        result = sameWidth;
                    }
                } else if (evenOdd == "odd") {
                    if (sameWidth % 2 == 0) {
                        result = sameWidth + 1;
                    } else {
                        result = sameWidth;
                    }
                } else if (mustBeMultiple == "yes") {
                    if (numberLabel.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(),
                                R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.VISIBLE);
                        calc3Button.setVisibility(View.VISIBLE);
                        calcResButton.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        result = CalculateMultiple.multipleSameWidthPlusResult();
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        result = CalculateMultiple.multipleSameWidthMinusResult();
                        minusLabel.setVisibility(View.VISIBLE);
                        plusLabel.setVisibility(View.GONE);
                    } else {
                        result = CalculateMultiple.multipleSameWidthNoneResult();
                    }
                } else {
                    result = sameWidth;
                }
            } else if (widthLength == "length") {
                if (evenOdd == "even") {
                    if (sameLength % 2 != 0) {
                        result = sameLength + 1;
                    } else {
                        result = sameLength;
                    }
                } else if (evenOdd == "odd") {
                    if (sameLength % 2 == 0) {
                        result = sameLength + 1;
                    } else {
                        result = sameLength;
                    }
                } else if (mustBeMultiple == "yes") {
                    if (numberLabel.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(),
                                R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.VISIBLE);
                        calc3Button.setVisibility(View.VISIBLE);
                        calcResButton.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        result = CalculateMultiple.multipleSameLengthPlusResult();
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        result = CalculateMultiple.multipleSameLengthMinusResult();
                        minusLabel.setVisibility(View.VISIBLE);
                        plusLabel.setVisibility(View.GONE);
                    } else {
                        result = CalculateMultiple.multipleSameLengthNoneResult();
                    }
                } else {
                    result = sameLength;
                }
            }
        } else if (sameSize == "diff")

        {
            if (widthLength == "width") {
                if (evenOdd == "even") {
                    if (diffWidth % 2 != 0) {
                        result = diffWidth + 1;
                    } else {
                        result = diffWidth;
                    }
                } else if (evenOdd == "odd") {
                    if (diffWidth % 2 == 0) {
                        result = diffWidth + 1;
                    } else {
                        result = diffWidth;
                    }
                } else if (mustBeMultiple == "yes") {
                    if (numberLabel.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(),
                                R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.VISIBLE);
                        calc3Button.setVisibility(View.VISIBLE);
                        calcResButton.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        result = CalculateMultiple.multipleDiffWidthPlusResult();
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        result = CalculateMultiple.multipleDiffWidthMinusResult();
                        minusLabel.setVisibility(View.VISIBLE);
                        plusLabel.setVisibility(View.GONE);
                    } else {
                        result = CalculateMultiple.multipleDiffWidthNoneResult();
                    }
                } else {
                    result = diffWidth;
                }
            } else if (widthLength == "length") {
                if (evenOdd == "even") {
                    if (diffLength % 2 != 0) {
                        result = diffLength + 1;
                    } else {
                        result = diffLength;
                    }
                } else if (evenOdd == "odd") {
                    if (diffLength % 2 == 0) {
                        result = diffLength + 1;
                    } else {
                        result = diffLength;
                    }
                } else if (mustBeMultiple == "yes") {
                    if (numberLabel.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(),
                                R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResult.setVisibility(View.GONE);
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.VISIBLE);
                        calc3Button.setVisibility(View.VISIBLE);
                        calcResButton.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        result = CalculateMultiple.multipleDiffLengthPlusResult();
                        plusLabel.setVisibility(View.VISIBLE);
                        minusLabel.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        result = CalculateMultiple.multipleDiffLengthMinusResult();
                        minusLabel.setVisibility(View.VISIBLE);
                        plusLabel.setVisibility(View.GONE);
                    } else {
                        result = CalculateMultiple.multipleDiffLengthNoneResult();
                    }
                } else {
                    result = diffLength;
                }
            }
        }

        return result;
    }

    private String calculateType() {

        if (widthLength == "width") {
            type = getResources().getString(R.string.result_stitches);

        } else if (widthLength == "length") {
            type = getResources().getString(R.string.result_rows);
        }
        return type;
    }

    public void blankSameDiff() {
        finishedSizeLabel.setVisibility(View.VISIBLE);
        sameRadioButton.setVisibility(View.VISIBLE);
        diffRadioButton.setVisibility(View.VISIBLE);
        changeSameDiffButton.setVisibility(View.GONE);
    }

    public void goneSameDiff() {
        finishedSizeLabel.setVisibility(View.GONE);
        sameRadioButton.setVisibility(View.GONE);
        diffRadioButton.setVisibility(View.GONE);
        changeSameDiffButton.setVisibility(View.GONE);
    }

    public void goneCustomSize() {
        customSizeText.setText("");
        customSizeText.setVisibility(View.GONE);
        nextCustomNumbersButton.setVisibility(View.GONE);
        changeCustomNumbersButton.setVisibility(View.GONE);
    }

    public void blankConditions() {
        conditionsLabel.setVisibility(View.VISIBLE);
        evenRadioButton.setVisibility(View.VISIBLE);
        oddRadioButton.setVisibility(View.VISIBLE);
        multRadioButton.setVisibility(View.VISIBLE);
        noneApplyRadioButton.setVisibility(View.VISIBLE);
        evenRadioButton.setChecked(false);
        oddRadioButton.setChecked(false);
        multRadioButton.setChecked(false);
        noneApplyRadioButton.setChecked(false);

        conditions = findViewById(R.id.conditions);
        conditions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.evenRadioButton) {
                    evenRadioButton.setChecked(true);
                    evenOdd = "even";
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);
                    calcButton.setVisibility(View.VISIBLE);
                    calc3Button.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);

                } else if (checkedId == R.id.oddRadioButton) {
                    oddRadioButton.setChecked(true);
                    evenOdd = "odd";
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);
                    calcButton.setVisibility(View.VISIBLE);
                    calc3Button.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);

                } else if (checkedId == R.id.multRadioButton) {
                    multRadioButton.setChecked(true);
                    mustBeMultiple = "yes";
                    evenOdd = null;
                    numberLabel.setVisibility(View.VISIBLE);
                    plusLabel.setVisibility(View.VISIBLE);
                    minusLabel.setVisibility(View.VISIBLE);
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);
                    calc3Button.setVisibility(View.VISIBLE);
                    calcButton.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);

                    calc3Button.setOnClickListener(onClickCalc3Button);

                } else if (checkedId == R.id.noneApplyRadioButton) {
                    noneApplyRadioButton.setChecked(true);
                    evenOdd = null;
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResult.setVisibility(View.GONE);
                    calcButton.setVisibility(View.VISIBLE);
                    calc3Button.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);
                }
            }
        });
    }

    public void goneConditions() {
        conditionsLabel.setVisibility(View.GONE);
        evenRadioButton.setVisibility(View.GONE);
        oddRadioButton.setVisibility(View.GONE);
        multRadioButton.setVisibility(View.GONE);
        noneApplyRadioButton.setVisibility(View.GONE);
    }

    public void goneMultiple() {
        mustBeMultiple = null;
        numberLabel.setText("");
        plusLabel.setText("");
        minusLabel.setText("");
        numberLabel.setVisibility(View.GONE);
        plusLabel.setVisibility(View.GONE);
        minusLabel.setVisibility(View.GONE);
    }

    View.OnClickListener onClickNextOrigNumbersButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nextOrigNumbersButton.setVisibility(View.INVISIBLE);
            origNumberResultText.setVisibility(View.VISIBLE);

            pwM();

            origNumberDec = new DecimalFormat("#.#");

            if (unitUnit() == null) {
                Toast.makeText(getApplicationContext(), "You must create a project", Toast.LENGTH_LONG).show();
            } else if (pwM == 0.0) {
                visible = false;
                Toast.makeText(getBaseContext(), R.string.no_number_warning,
                        Toast.LENGTH_LONG).show();
                nextOrigNumbersButton.setVisibility(View.VISIBLE);
                origNumberResultText.setVisibility(View.GONE);
                goneSameDiff();
                goneCustomSize();
                goneConditions();
                goneMultiple();
            } else if (pwM != 0.0) {
                origNumberText.setVisibility(View.GONE);
                blankSameDiff();
                if (unitUnit().equals("cm")) {
                    origNumberResultText.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(pwM) + " " + getString(R.string.cm_label));
                } else if (unitUnit().equals("inch")) {
                    origNumberResultText.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(pwM) + " " + getString(R.string.inches_label));
                }
            }
        }
    };

    View.OnClickListener onClickNextOrigNumbersLButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nextOrigNumbersLButton.setVisibility(View.INVISIBLE);
            origNumberLResultText.setVisibility(View.VISIBLE);

            plM();

            origNumberDec = new DecimalFormat("#.#");

            if (unitUnit() == null) {
                Toast.makeText(getApplicationContext(), "You must create a project", Toast.LENGTH_LONG).show();
            } else if (plM == 0.0) {
                visible = false;
                Toast.makeText(getBaseContext(), R.string.no_number_warning,
                        Toast.LENGTH_LONG).show();
                nextOrigNumbersLButton.setVisibility(View.VISIBLE);
                origNumberLResultText.setVisibility(View.GONE);
                goneSameDiff();
                goneCustomSize();
                goneConditions();
                goneMultiple();
            } else if (plM != 0.0) {
                origNumberLText.setVisibility(View.GONE);
                blankSameDiff();
                if (unitUnit() == null) {
                    Toast.makeText(getApplicationContext(), "You must create a project", Toast.LENGTH_LONG).show();
                } else if (unitUnit().equals("cm")) {
                    origNumberLResultText.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(plM) + " " + getString(R.string.cm_label));
                } else if (unitUnit().equals("inch")) {
                    origNumberLResultText.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(plM) + " " + getString(R.string.inches_label));
                }
            }
        }
    };

    public void customNumbersStuff() {
        nextCustomNumbersButton.setVisibility(View.INVISIBLE);
        changeCustomNumbersButton.setVisibility(View.VISIBLE);

        blankConditions();

        customSize = gen.dblFromSource(customSizeText.getText().toString());

        /*customSizeS = customSizeText.getText().toString();
        if (!customSizeS.equals("")) {
            customSize = Double.valueOf(customSizeS);
        } else {
            customSize = 0.0;
        }*/

        if (customSize == 0.0) {
            Toast.makeText(getBaseContext(), R.string.no_number_warning,
                    Toast.LENGTH_LONG).show();
            nextCustomNumbersButton.setVisibility(View.VISIBLE);
            changeCustomNumbersButton.setVisibility(View.GONE);
            goneConditions();
            goneMultiple();
            newNumberLabel.setVisibility(View.GONE);
            newNumberResult.setVisibility(View.GONE);
            calcButton.setVisibility(View.GONE);
            calc3Button.setVisibility(View.GONE);
            calcResButton.setVisibility(View.GONE);
        }
    }

    View.OnClickListener onClickNextCustomNumbersButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            customNumbersStuff();
        }
    };

    View.OnClickListener onClickChangeCustomNumbersButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            customNumbersStuff();
        }
    };

    TextWatcher onChangeCustomSize = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (visible) {
                Toast.makeText(getApplicationContext(), R.string.edit_warning, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            goneConditions();
            goneMultiple();
            calcButton.setVisibility(View.GONE);
            calc3Button.setVisibility(View.GONE);
        }
    };

    View.OnClickListener onClickCalc3Button = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calc3Button.setVisibility(View.GONE);
            calcButton.setVisibility(View.GONE);
            newNumberLabel.setVisibility(View.VISIBLE);
            newNumberResult.setVisibility(View.VISIBLE);
            calcResButton.setVisibility(View.VISIBLE);
            calculateResult();

            calcResButton.setOnClickListener(onClickResetButton);

            if (multiple == 0.0) {
                calc3Button.setVisibility(View.VISIBLE);
                plusLabel.setVisibility(View.VISIBLE);
                minusLabel.setVisibility(View.VISIBLE);
                calcResButton.setVisibility(View.GONE);
            }

            newNumberLabel.setText(calculateType());
            newNumberResult.setText(String.valueOf(calculateResult()));
        }
    };

    View.OnClickListener onClickCalcButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcButton.setVisibility(View.GONE);
            calc3Button.setVisibility(View.GONE);
            newNumberLabel.setVisibility(View.VISIBLE);
            newNumberResult.setVisibility(View.VISIBLE);
            calcResButton.setVisibility(View.VISIBLE);
            calculateResult();

            calcResButton.setOnClickListener(onClickResetButton);

            newNumberLabel.setText(calculateType());
            newNumberResult.setText(String.valueOf(calculateResult()));
        }
    };

    View.OnClickListener onClickResetButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reset = new Intent(CalculatorLayout.this, CalculatorLayout.class);
            reset.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(reset);
        }
    };
}
