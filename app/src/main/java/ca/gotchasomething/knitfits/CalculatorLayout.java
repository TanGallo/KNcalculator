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
    Button origNumberNextBtn, custNumberNextBtn, changeCustNumberBtn, calcBtn, calcResBtn;
    Cursor cursor;
    DecimalFormat origNumberDec;
    Double customSize = 0.0, gli = 0.0, glRperI = 0.0, gwi = 0.0, gwStperI = 0.0, minus = 0.0, multiple = 0.0, origNumberL = 0.0, origNumber = 0.0,
            pli = 0.0, plM = 0.0, plr = 0.0,
            plus = 0.0, pwi = 0.0, pwM = 0.0, pws = 0.0;
    EditText origNumberET, origNumberLText, custSizeET, baseET, plusET, minusET;
    General gen;
    int result = 0, resultIncDec = 0, sameWidth = 0, sameLength = 0, diffWidth = 0, diffLength = 0;
    Intent reset, backToCalculator;
    ProjectsDbHelper projectsDbHelper;
    RadioButton castOnRB, incDecRB, lengthRB, widthRB, sameRB, diffRB, evenRB, oddRB, multRB, noneRB;
    RadioGroup calcPurposeRG, sameDiffRG, condRG;
    SpinnerAdapter sAdapter;
    Spinner spinner;
    SQLiteDatabase db;
    String origNumberSW = null, origNumberSL = null, origIncDecS = null, sameDiff = null, evenOdd = null, widthLength = null, mustBeMultiple = null,
            customSizeS = null, multipleS = null, plusS = null, minusS = null, type = null, unit = null;
    TextView origNumberResTV, origIncDecText, finSizeLabel, conditionsLabel, newNumberLabel, newNumberResTV;

    //data captured in spinner selection for use in calculations
    //double pws = 0.0, pwi = 0.0, plr = 0.0, pli = 0.0, gwi = 0.0, gli = 0.0;

    //data captured in layout for use in calculations
    //double origNumberW = 0.0, origNumberL = 0.0;
    //double customSize = 0.0;
    //static double multiple = 0.0;
    //static double plus = 0.0;
    //static double minus = 0.0;

    //calculated values for use in calculations
    //DecimalFormat origNumberDec;
    //Double pwM = 0.0, plM = 0.0, gwStperI = 0.0, glRperI = 0.0;
    //static int sameWidth = 0;
    //static int sameLength = 0;
    //static int diffWidth = 0;
    //static int diffLength = 0;
    //String type = null;
    //int result = 0, resultIncDec = 0;

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

        origNumberET = findViewById(R.id.origNumberET);
        origNumberET.setVisibility(View.GONE);
        origNumberNextBtn = findViewById(R.id.origNumberNextBtn);
        origNumberNextBtn.setVisibility(View.GONE);
        origNumberResTV = findViewById(R.id.origNumberResTV);
        origNumberResTV.setVisibility(View.GONE);
        calcPurposeRG = findViewById(R.id.calcPurposeRG);
        castOnRB = findViewById(R.id.castOnRB);
        incDecRB = findViewById(R.id.incDecRB);
        lengthRB = findViewById(R.id.lengthRB);
        widthRB = findViewById(R.id.widthRB);
        finSizeLabel = findViewById(R.id.finSizeLabel);
        finSizeLabel.setVisibility(View.GONE);
        sameDiffRG = findViewById(R.id.sameDiffRG);
        sameRB = findViewById(R.id.sameRB);
        sameRB.setVisibility(View.GONE);
        diffRB = findViewById(R.id.diffRB);
        diffRB.setVisibility(View.GONE);
        custSizeET = findViewById(R.id.custSizeET);
        custSizeET.setVisibility(View.GONE);
        custNumberNextBtn = findViewById(R.id.custNumberNextBtn);
        custNumberNextBtn.setVisibility(View.GONE);
        //changeCustNumberBtn = findViewById(R.id.changeCustNumberBtn);
        //changeCustNumberBtn.setVisibility(View.GONE);
        condRG = findViewById(R.id.condRG);
        evenRB = findViewById(R.id.evenRB);
        evenRB.setVisibility(View.GONE);
        oddRB = findViewById(R.id.oddRB);
        oddRB.setVisibility(View.GONE);
        multRB = findViewById(R.id.multRB);
        multRB.setVisibility(View.GONE);
        noneRB = findViewById(R.id.noneRB);
        noneRB.setVisibility(View.GONE);
        conditionsLabel = findViewById(R.id.conditionsLabel);
        conditionsLabel.setVisibility(View.GONE);
        baseET = findViewById(R.id.baseET);
        baseET.setVisibility(View.GONE);
        plusET = findViewById(R.id.plusET);
        plusET.setVisibility(View.GONE);
        minusET = findViewById(R.id.minusET);
        minusET.setVisibility(View.GONE);
        newNumberLabel = findViewById(R.id.newNumberLabel);
        newNumberLabel.setVisibility(View.GONE);
        newNumberResTV = findViewById(R.id.newNumberResTV);
        newNumberResTV.setVisibility(View.GONE);
        calcBtn = findViewById(R.id.calcBtn);
        calcBtn.setVisibility(View.GONE);
        calcResBtn = findViewById(R.id.calcResBtn);
        calcResBtn.setVisibility(View.GONE);
        //changeSameDiffBtn = findViewById(R.id.changeSameDiffBtn);
        //changeSameDiffBtn.setVisibility(View.GONE);
        //origNumberLText = findViewById(R.id.origNumberLText);
        //origNumberLText.setVisibility(View.GONE);
        //origIncDecText = findViewById(R.id.origIncDecText);
        //origIncDecText.setVisibility(View.GONE);
        //origNumberLResultText = findViewById(R.id.origNumberLResultText);
        //origNumberLResultText.setVisibility(View.GONE);
        //nextOrigNumbersLButton = findViewById(R.id.nextOrigNumbersLButton);
        //nextOrigNumbersLButton.setVisibility(View.GONE);
        //calc2Button = findViewById(R.id.calc2Button);
        //calc2Button.setVisibility(View.GONE);
        //calc3Button = findViewById(R.id.calc3Button);
        //calc3Button.setVisibility(View.GONE);
        //calcResButton = findViewById(R.id.calcResButton);
        //calcResButton.setVisibility(View.GONE);

        //set spinner for choose project
        spinner = findViewById(R.id.chooseProjectSpinner);
        projectsDbHelper = new ProjectsDbHelper(this);
        db = projectsDbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + ProjectsDbHelper.TABLE_NAME + " ORDER BY " + ProjectsDbHelper.ID + " DESC", null);
        sAdapter = new SpinnerAdapter(this, cursor);
        spinner.setAdapter(sAdapter);

        spinner.setOnItemSelectedListener(onChooseProject);
        calcPurposeRG.setOnCheckedChangeListener(onChoosePurpose);
        origNumberNextBtn.setOnClickListener(onClickOrigNumberNextBtn);
        sameDiffRG.setOnCheckedChangeListener(onChangeSameDiff);
        custSizeET.addTextChangedListener(onChangeCustomSize);
        custNumberNextBtn.setOnClickListener(onClickCustNumberNextBtn);
        //changeCustNumberBtn.setOnClickListener(onClickChangeCustNumberBtn);
        //condRG.setOnCheckedChangeListener(onChangeConditions);
        calcBtn.setOnClickListener(onClickCalcBtn);
        calcResBtn.setOnClickListener(onClickResetButton);
        //nextOrigNumbersLButton.setOnClickListener(onClickNextOrigNumbersLButton);
    }

    public void backToCalculator() {
        backToCalculator = new Intent(CalculatorLayout.this, CalculatorLayout.class);
        backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(backToCalculator);
    }

    /*public String unitUnit() {
        return unit;
    }*/

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //retrieve project data from spinner selection
                pws = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWS)));
                pwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWI)));
                plr = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLR)));
                pli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLI)));
                gwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GWI)));
                gli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GLI)));*/

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

                /*String unitS = cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.UNIT));
                if (unitS != null && !unitS.equals("")) {
                    unit = unitS;
                } else {
                    unit = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    //handle RadioGroup for question 2
    //calcPurposeRadioGroup = findViewById(R.id.calcPurposeRadioGroup);
        /*calcPurposeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/

    //handle RadioGroup for question 3
        
        /*sameDiffRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.sameRB) {
                    sameSize = "same";
                    same = true;
                    diff = false;

                    custSizeET.setVisibility(View.GONE);
                    customSize = 0.0;
                    custNumberNextBtn.setVisibility(View.GONE);
                    changeCustNumberBtn.setVisibility(View.GONE);

                    blankConditions();

                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);

                } else if (checkedId == R.id.diffRB) {
                    sameSize = "diff";
                    same = false;
                    diff = true;

                    custSizeET.setVisibility(View.VISIBLE);
                    custSizeET.setText("");
                    custNumberNextBtn.setVisibility(View.VISIBLE);

                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);
                    calcButton.setVisibility(View.GONE);
                    calc3Button.setVisibility(View.GONE);
                    calcResButton.setVisibility(View.GONE);
                }
            }
        });*/


    AdapterView.OnItemSelectedListener onChooseProject = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //retrieve project data from spinner selection
            pws = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWS)));
            pwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PWI)));
            plr = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLR)));
            pli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.PLI)));
            gwi = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GWI)));
            gli = gen.dblFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.GLI)));

            unit = gen.stringFromSource(cursor.getString(cursor.getColumnIndex(ProjectsDbHelper.UNIT)));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    RadioGroup.OnCheckedChangeListener onChoosePurpose = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.castOnRB) {

                incDecRB.setVisibility(View.GONE);
                lengthRB.setVisibility(View.GONE);
                widthRB.setVisibility(View.GONE);

                origNumberET.setVisibility(View.VISIBLE);
                origNumberNextBtn.setVisibility(View.VISIBLE);

                widthLength = "width";

            } else if (checkedId == R.id.incDecRB) {

                castOnRB.setVisibility(View.GONE);
                lengthRB.setVisibility(View.GONE);
                widthRB.setVisibility(View.GONE);

                origNumberET.setVisibility(View.VISIBLE);
                calcBtn.setVisibility(View.VISIBLE);
                //origIncDecText.setVisibility(View.VISIBLE);

                widthLength = "width";
                sameDiff = "same";

                calcBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        calcBtn.setVisibility(View.GONE);
                        newNumberLabel.setVisibility(View.VISIBLE);
                        newNumberResTV.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.VISIBLE);

                        if (unit == null) {
                            Toast.makeText(getBaseContext(), R.string.no_data_warning, Toast.LENGTH_LONG).show();
                            calcBtn.setVisibility(View.VISIBLE);
                            newNumberLabel.setVisibility(View.GONE);
                            newNumberResTV.setVisibility(View.GONE);
                            calcResBtn.setVisibility(View.GONE);
                        } else {
                            origNumber = gen.dblFromSource(origNumberET.getText().toString());

                            /*origIncDecS = origIncDecText.getText().toString();
                            if (!origIncDecS.equals("")) {
                                origNumberW = Double.valueOf(origIncDecS);
                            } else {*/
                            if (origNumber == 0) {
                                Toast.makeText(getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                                calcBtn.setVisibility(View.VISIBLE);
                                newNumberLabel.setVisibility(View.GONE);
                                newNumberResTV.setVisibility(View.GONE);
                                calcResBtn.setVisibility(View.GONE);
                            }

                            resultIncDec = (int) Math.round((origNumber / (pws / pwi)) * (10 / gwi));

                            newNumberLabel.setText(calculateType());
                            newNumberResTV.setText(String.valueOf(resultIncDec));
                        }

                        calcResBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                backToCalculator();
                            }
                        });
                    }
                });

            } else if (checkedId == R.id.widthRB) {

                castOnRB.setVisibility(View.GONE);
                incDecRB.setVisibility(View.GONE);
                lengthRB.setVisibility(View.GONE);

                origNumberET.setVisibility(View.VISIBLE);
                origNumberNextBtn.setVisibility(View.VISIBLE);

                widthLength = "width";

            } else if (checkedId == R.id.lengthRB) {

                castOnRB.setVisibility(View.GONE);
                incDecRB.setVisibility(View.GONE);
                widthRB.setVisibility(View.GONE);

                origNumberET.setVisibility(View.VISIBLE);
                origNumberNextBtn.setVisibility(View.VISIBLE);
                //origNumberLText.setVisibility(View.VISIBLE);
                //nextOrigNumbersLButton.setVisibility(View.VISIBLE);

                widthLength = "length";
            }
        }
    };

    RadioGroup.OnCheckedChangeListener onChangeSameDiff = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.sameRB) {
                sameDiff = "same";
                //same = true;
                //diff = false;

                custSizeET.setVisibility(View.GONE);
                customSize = 0.0;
                custNumberNextBtn.setVisibility(View.GONE);
                //changeCustNumberBtn.setVisibility(View.GONE);

                blankConditions();

                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);

            } else if (checkedId == R.id.diffRB) {
                sameDiff = "diff";
                //same = false;
                //diff = true;

                custSizeET.setVisibility(View.VISIBLE);
                custSizeET.setText("");
                custNumberNextBtn.setVisibility(View.VISIBLE);

                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.GONE);
                //calc3Button.setVisibility(View.GONE);
                calcResBtn.setVisibility(View.GONE);
            }
        }
    };

    /*public Double spinnerSel(String str1) {
        String string = str1;
        if (string != null && !string.equals("")) {
            spinnerSel = Double.valueOf(string);
        } else {
            spinnerSel = 0.0;
        }
        return spinnerSel;
    }*/

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

        multiple = gen.dblFromSource(baseET.getText().toString());
        plus = gen.dblFromSource(plusET.getText().toString());
        minus = gen.dblFromSource(minusET.getText().toString());

        /*multipleS = numberLabel.getText().toString();
        if (!multipleS.equals("")) {
            multiple = Double.valueOf(multipleS);
        } else {
            multiple = 0.0;
        }

        plusS = plusET.getText().toString();
        if (!plusS.equals("")) {
            plus = Double.valueOf(plusS);
        } else {
            plus = 0.0;
        }

        minusS = minusET.getText().toString();
        if (!minusS.equals("")) {
            minus = Double.valueOf(minusS);
        } else {
            minus = 0.0;
        }*/

        gwStperI = 10 / gwi;
        glRperI = 10 / gli;

        sameWidth = (int) Math.round(pwM * gwStperI);
        sameLength = (int) Math.round(plM * glRperI);
        diffWidth = (int) Math.round(customSize * gwStperI);
        diffLength = (int) Math.round(customSize * glRperI);

        if (sameDiff.equals("same")) {
            if (widthLength.equals("width")) {
                if (evenOdd.equals("even")) {
                    if (sameWidth % 2 != 0) {
                        result = sameWidth + 1;
                    } else {
                        result = sameWidth;
                    }
                } else if (evenOdd.equals("odd")) {
                    if (sameWidth % 2 == 0) {
                        result = sameWidth + 1;
                    } else {
                        result = sameWidth;
                    }
                } else if (mustBeMultiple.equals("yes")) {
                    if (baseET.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.VISIBLE);
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        //SameWidthPlus
                        result = gen.ultimateResult(
                                gen.getResult1(sameWidth, plus, multiple),
                                gen.getResult2(sameWidth, plus, multiple),
                                sameWidth);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        //sameWidthMinus
                        result = gen.ultimateResult(
                                gen.getResult3(sameWidth, minus, multiple),
                                gen.getResult4(sameWidth, minus, multiple),
                                sameWidth);
                        minusET.setVisibility(View.VISIBLE);
                        plusET.setVisibility(View.GONE);
                    } else {
                        //sameWidthNone
                        result = gen.ultimateResult(
                                gen.getResult1(sameWidth, plus, multiple),
                                gen.getResult2(sameWidth, plus, multiple),
                                sameWidth);
                    }
                } else {
                    result = sameWidth;
                }
            } else if (widthLength.equals("length")) {
                if (evenOdd.equals("even")) {
                    if (sameLength % 2 != 0) {
                        result = sameLength + 1;
                    } else {
                        result = sameLength;
                    }
                } else if (evenOdd.equals("odd")) {
                    if (sameLength % 2 == 0) {
                        result = sameLength + 1;
                    } else {
                        result = sameLength;
                    }
                } else if (mustBeMultiple.equals("yes")) {
                    if (baseET.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.VISIBLE);
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        //sameLengthPlus
                        result = gen.ultimateResult(
                                gen.getResult1(sameLength, plus, multiple),
                                gen.getResult2(sameLength, plus, multiple),
                                sameLength);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        //sameLengthMinus
                        result = gen.ultimateResult(
                                gen.getResult3(sameLength, minus, multiple),
                                gen.getResult4(sameLength, minus, multiple),
                                sameLength);
                        minusET.setVisibility(View.VISIBLE);
                        plusET.setVisibility(View.GONE);
                    } else {
                        //sameLengthNone
                        result = gen.ultimateResult(
                                gen.getResult1(sameLength, plus, multiple),
                                gen.getResult2(sameLength, plus, multiple),
                                sameLength);
                    }
                } else {
                    result = sameLength;
                }
            }
        } else if (sameDiff.equals("diff")) {
            if (widthLength.equals("width")) {
                if (evenOdd.equals("even")) {
                    if (diffWidth % 2 != 0) {
                        result = diffWidth + 1;
                    } else {
                        result = diffWidth;
                    }
                } else if (evenOdd.equals("odd")) {
                    if (diffWidth % 2 == 0) {
                        result = diffWidth + 1;
                    } else {
                        result = diffWidth;
                    }
                } else if (mustBeMultiple.equals("yes")) {
                    if (baseET.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.VISIBLE);
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        //diffWidthPlus
                        result = gen.ultimateResult(
                                gen.getResult1(diffWidth, plus, multiple),
                                gen.getResult2(diffWidth, plus, multiple),
                                diffWidth);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        //diffWidthMinus
                        result = gen.ultimateResult(
                                gen.getResult3(diffWidth, minus, multiple),
                                gen.getResult4(diffWidth, minus, multiple),
                                diffWidth);
                        minusET.setVisibility(View.VISIBLE);
                        plusET.setVisibility(View.GONE);
                    } else {
                        //diffWidthNone
                        result = gen.ultimateResult(
                                gen.getResult1(diffWidth, plus, multiple),
                                gen.getResult2(diffWidth, plus, multiple),
                                diffWidth);
                    }
                } else {
                    result = diffWidth;
                }
            } else if (widthLength.equals("length")) {
                if (evenOdd.equals("even")) {
                    if (diffLength % 2 != 0) {
                        result = diffLength + 1;
                    } else {
                        result = diffLength;
                    }
                } else if (evenOdd.equals("odd")) {
                    if (diffLength % 2 == 0) {
                        result = diffLength + 1;
                    } else {
                        result = diffLength;
                    }
                } else if (mustBeMultiple.equals("yes")) {
                    if (baseET.getText().toString().matches("")) {
                        Toast.makeText(getApplication().getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                    } else if (plus > 0 && minus > 0) {
                        result = 0;
                        Toast.makeText(getApplication().getBaseContext(), R.string.plus_minus_toast, Toast.LENGTH_LONG).show();
                        newNumberLabel.setVisibility(View.GONE);
                        newNumberResTV.setVisibility(View.GONE);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.VISIBLE);
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
                    } else if (plus > 0) {
                        //doffLengthPlus
                        result = gen.ultimateResult(
                                gen.getResult1(diffLength, plus, multiple),
                                gen.getResult2(diffLength, plus, multiple),
                                diffLength);
                        plusET.setVisibility(View.VISIBLE);
                        minusET.setVisibility(View.GONE);
                    } else if (minus > 0) {
                        //diffLengthMinus
                        result = gen.ultimateResult(
                                gen.getResult3(diffLength, minus, multiple),
                                gen.getResult4(diffLength, minus, multiple),
                                diffLength);
                        minusET.setVisibility(View.VISIBLE);
                        plusET.setVisibility(View.GONE);
                    } else {
                        //doffLengthNone
                        result = gen.ultimateResult(
                                gen.getResult1(diffLength, plus, multiple),
                                gen.getResult2(diffLength, plus, multiple),
                                diffLength);
                    }
                } else {
                    result = diffLength;
                }
            }
        }

        return result;
    }

    private String calculateType() {

        if (widthLength.equals("width")) {
            type = getResources().getString(R.string.result_stitches);

        } else if (widthLength.equals("length")) {
            type = getResources().getString(R.string.result_rows);
        }
        return type;
    }

    public void blankSameDiff() {
        finSizeLabel.setVisibility(View.VISIBLE);
        sameRB.setVisibility(View.VISIBLE);
        diffRB.setVisibility(View.VISIBLE);
    }

    /*public void goneSameDiff() {
        finishedSizeLabel.setVisibility(View.GONE);
        sameRadioButton.setVisibility(View.GONE);
        diffRadioButton.setVisibility(View.GONE);
        changeSameDiffButton.setVisibility(View.GONE);
    }*/

    public void goneCustomSize() {
        custSizeET.setText("");
        custSizeET.setVisibility(View.GONE);
        custNumberNextBtn.setVisibility(View.GONE);
        //changeCustNumberBtn.setVisibility(View.GONE);
    }

    public void blankConditions() {
        conditionsLabel.setVisibility(View.VISIBLE);
        evenRB.setVisibility(View.VISIBLE);
        oddRB.setVisibility(View.VISIBLE);
        multRB.setVisibility(View.VISIBLE);
        noneRB.setVisibility(View.VISIBLE);
        evenRB.setChecked(false);
        oddRB.setChecked(false);
        multRB.setChecked(false);
        noneRB.setChecked(false);

        condRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.evenRB) {
                    evenRB.setChecked(true);
                    evenOdd = "even";
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);
                    calcBtn.setVisibility(View.VISIBLE);
                    //calc3Button.setVisibility(View.GONE);
                    calcResBtn.setVisibility(View.GONE);

                } else if (checkedId == R.id.oddRB) {
                    oddRB.setChecked(true);
                    evenOdd = "odd";
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);
                    calcBtn.setVisibility(View.VISIBLE);
                    //calc3Button.setVisibility(View.GONE);
                    calcResBtn.setVisibility(View.GONE);

                } else if (checkedId == R.id.multRB) {
                    multRB.setChecked(true);
                    mustBeMultiple = "yes";
                    evenOdd = null;
                    baseET.setVisibility(View.VISIBLE);
                    plusET.setVisibility(View.VISIBLE);
                    minusET.setVisibility(View.VISIBLE);
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);
                    //calc3Button.setVisibility(View.VISIBLE);
                    calcBtn.setVisibility(View.VISIBLE);
                    calcResBtn.setVisibility(View.GONE);

                    //calc3Button.setOnClickListener(onClickCalc3Button);

                } else if (checkedId == R.id.noneRB) {
                    noneRB.setChecked(true);
                    evenOdd = null;
                    mustBeMultiple = null;
                    goneMultiple();
                    newNumberLabel.setVisibility(View.GONE);
                    newNumberResTV.setVisibility(View.GONE);
                    calcBtn.setVisibility(View.VISIBLE);
                    //calc3Button.setVisibility(View.GONE);
                    calcResBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    public void goneConditions() {
        conditionsLabel.setVisibility(View.GONE);
        evenRB.setVisibility(View.GONE);
        oddRB.setVisibility(View.GONE);
        multRB.setVisibility(View.GONE);
        noneRB.setVisibility(View.GONE);
    }

    public void goneMultiple() {
        mustBeMultiple = null;
        baseET.setText("");
        plusET.setText("");
        minusET.setText("");
        baseET.setVisibility(View.GONE);
        plusET.setVisibility(View.GONE);
        minusET.setVisibility(View.GONE);
    }

    View.OnClickListener onClickOrigNumberNextBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            origNumberNextBtn.setVisibility(View.INVISIBLE);
            origNumberResTV.setVisibility(View.VISIBLE);

            origNumberDec = new DecimalFormat("#.#");

            //if (unitUnit() == null) {
            if (unit == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_data_warning), Toast.LENGTH_LONG).show();
            } else {
                if (widthLength.equals("width")) {
                    pwM = gen.detMeasurement(
                            pws,
                            pwi,
                            origNumberET,
                            origNumberResTV,
                            finSizeLabel,
                            sameRB,
                            diffRB,
                            origNumberNextBtn);

                    if (pwM == 0.0) {
                        visible = false;
                        /*Toast.makeText(getBaseContext(), R.string.no_number_warning,
                                Toast.LENGTH_LONG).show();*/
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        //origNumberResTV.setVisibility(View.GONE);
                        //goneSameDiff();
                        goneCustomSize();
                        goneConditions();
                        goneMultiple();
                    } else if (pwM != 0.0) {
                        origNumberET.setVisibility(View.GONE);
                        blankSameDiff();
                        //if (unitUnit().equals("cm")) {
                        //} else if (unitUnit().equals("inch")
                        String stmtCm = R.string.cast_on_result + " " + origNumberDec.format(pwM) + " " + R.string.cm_label;
                        String stmtInch = R.string.cast_on_result + " " + origNumberDec.format(pwM) + " " + R.string.inches_label;
                        if (unit.equals("cm")) {
                            origNumberResTV.setText(stmtCm);
                            //origNumberResTV.setText(R.string.cast_on_result + " " + origNumberDec.format(pwM) + " " + R.string.cm_label);
                            //} else if (unitUnit().equals("inch")) {

                        } else if (unit.equals("inch")) {
                            origNumberResTV.setText(stmtInch);
                            //origNumberResTV.setText(getString(R.string.cast_on_result)
                            //+ " " + origNumberDec.format(pwM) + " " + getString(R.string.inches_label));
                        }
                    }
                } else if (widthLength.equals("length")) {
                    plM = gen.detMeasurement(
                            plr,
                            pli,
                            origNumberET,
                            origNumberResTV,
                            finSizeLabel,
                            sameRB,
                            diffRB,
                            origNumberNextBtn);

                    if (plM == 0.0) {
                        visible = false;
                        /*Toast.makeText(getBaseContext(), R.string.no_number_warning,
                                Toast.LENGTH_LONG).show();*/
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        //origNumberResTV.setVisibility(View.GONE);
                        //goneSameDiff();
                        goneCustomSize();
                        goneConditions();
                        goneMultiple();
                    } else if (plM != 0.0) {
                        origNumberET.setVisibility(View.GONE);
                        //origNumberLText.setVisibility(View.GONE);
                        blankSameDiff();
                        //if (unitUnit() == null) {
                        String stmtCm = R.string.cast_on_result + " " + origNumberDec.format(plM) + " " + R.string.cm_label;
                        String stmtInch = R.string.cast_on_result + " " + origNumberDec.format(plM) + " " + R.string.inches_label;
                        if (unit.equals("cm")) {
                            origNumberResTV.setText(stmtCm);
                            //} else if (unitUnit().equals("inch")) {

                        } else if (unit.equals("inch")) {
                            origNumberResTV.setText(stmtInch);
                        }
                    }
                }
            }
        }
    };

                    //pwM();

            /*if (unitUnit() == null) {
                Toast.makeText(getApplicationContext(), "You must create a project", Toast.LENGTH_LONG).show();
            } else if (pwM == 0.0) {
                visible = false;
                Toast.makeText(getBaseContext(), R.string.no_number_warning,
                        Toast.LENGTH_LONG).show();
                origNumberNextBtn.setVisibility(View.VISIBLE);
                origNumberResTV.setVisibility(View.GONE);
                goneSameDiff();
                goneCustomSize();
                goneConditions();
                goneMultiple();
            } else if (pwM != 0.0) {
                origNumberET.setVisibility(View.GONE);
                blankSameDiff();
                if (unitUnit().equals("cm")) {
                    origNumberResTV.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(pwM) + " " + getString(R.string.cm_label));
                } else if (unitUnit().equals("inch")) {
                    origNumberResTV.setText(getString(R.string.cast_on_result)
                            + " " + origNumberDec.format(pwM) + " " + getString(R.string.inches_label));
                }
            }*/

    /*View.OnClickListener onClickNextOrigNumbersLButton = new View.OnClickListener() {
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
                origNumberET.setVisibility(View.GONE);
                //origNumberLText.setVisibility(View.GONE);
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
    };*/

    public void customNumbersStuff() {
        //custNumberNextBtn.setVisibility(View.VISIBLE);
        custNumberNextBtn.setText(R.string.change_button);
        //changeCustNumberBtn.setVisibility(View.VISIBLE);

        blankConditions();

        customSize = gen.dblFromSource(custSizeET.getText().toString());

        /*customSizeS = customSizeText.getText().toString();
        if (!customSizeS.equals("")) {
            customSize = Double.valueOf(customSizeS);
        } else {
            customSize = 0.0;
        }*/

        if (customSize == 0.0) {
            Toast.makeText(getBaseContext(), R.string.no_number_warning,
                    Toast.LENGTH_LONG).show();
            custNumberNextBtn.setText(R.string.next);
            //changeCustNumberBtn.setVisibility(View.GONE);
            goneConditions();
            goneMultiple();
            newNumberLabel.setVisibility(View.GONE);
            newNumberResTV.setVisibility(View.GONE);
            calcBtn.setVisibility(View.GONE);
            //calc3Button.setVisibility(View.GONE);
            calcResBtn.setVisibility(View.GONE);
        }
    }

    View.OnClickListener onClickCustNumberNextBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            customNumbersStuff();
        }
    };

    /*View.OnClickListener onClickChangeCustNumberBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            customNumbersStuff();
        }
    };*/

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
            calcBtn.setVisibility(View.GONE);
            //calc3Button.setVisibility(View.GONE);
        }
    };

    /*View.OnClickListener onClickCalc3Button = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calc3Button.setVisibility(View.GONE);
            calcBtn.setVisibility(View.GONE);
            newNumberLabel.setVisibility(View.VISIBLE);
            newNumberResTV.setVisibility(View.VISIBLE);
            calcResBtn.setVisibility(View.VISIBLE);
            calculateResult();

            calcResBtn.setOnClickListener(onClickResetButton);

            if (multiple == 0.0) {
                calc3Button.setVisibility(View.VISIBLE);
                plusET.setVisibility(View.VISIBLE);
                minusET.setVisibility(View.VISIBLE);
                calcResBtn.setVisibility(View.GONE);
            }

            newNumberLabel.setText(calculateType());
            newNumberResTV.setText(String.valueOf(calculateResult()));
        }
    };*/

    View.OnClickListener onClickCalcBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcBtn.setVisibility(View.GONE);
            //calc3Button.setVisibility(View.GONE);
            newNumberLabel.setVisibility(View.VISIBLE);
            newNumberResTV.setVisibility(View.VISIBLE);
            calcResBtn.setVisibility(View.VISIBLE);

            if (mustBeMultiple.equals("yes")) {
                if (multiple == 0.0) {
                    calcBtn.setVisibility(View.VISIBLE);
                    plusET.setVisibility(View.VISIBLE);
                    minusET.setVisibility(View.VISIBLE);
                    calcResBtn.setVisibility(View.GONE);
                }
            }

            //calculateResult();

            //calcResBtn.setOnClickListener(onClickResetButton);

            newNumberLabel.setText(calculateType());
            newNumberResTV.setText(String.valueOf(calculateResult()));
        }
    };

    View.OnClickListener onClickResetButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backToCalculator();
        }
    };
}
