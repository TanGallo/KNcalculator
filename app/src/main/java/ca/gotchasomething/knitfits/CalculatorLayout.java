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

    boolean visible = false;
    Button origNumberNextBtn, custNumberNextBtn, calcBtn, calcResBtn;
    Cursor cursor;
    DecimalFormat origNumberDec;
    Double customSize = 0.0, gli = 0.0, glRperI = 0.0, gwi = 0.0, gwStperI = 0.0, minus = 0.0, multiple = 0.0, origNumber = 0.0, pli = 0.0, plM = 0.0,
            plr = 0.0, plus = 0.0, pwi = 0.0, pwM = 0.0, pws = 0.0;
    EditText origNumberET, custSizeET, baseET, plusET, minusET;
    General gen;
    int result = 0, resultIncDec = 0, sameWidth = 0, sameLength = 0, diffWidth = 0, diffLength = 0;
    Intent backToCalculator;
    ProjectsDbHelper projectsDbHelper;
    RadioButton castOnRB, incDecRB, lengthRB, widthRB, sameRB, diffRB, evenRB, oddRB, multRB, noneRB;
    RadioGroup calcPurposeRG, sameDiffRG, condRG;
    SpinnerAdapter sAdapter;
    Spinner spinner;
    SQLiteDatabase db;
    String sameDiff = null, evenOdd = null, widthLength = null, mustBeMultiple = null, type = null, unit = null;
    TextView origNumberResTV, finSizeLabel, conditionsLabel, newNumberLabel, newNumberResTV;

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
        condRG.setOnCheckedChangeListener(onChooseConditions);
        calcBtn.setOnClickListener(onClickCalcBtn);
        calcResBtn.setOnClickListener(onClickResetButton);
    }

    public void backToCalculator() {
        backToCalculator = new Intent(CalculatorLayout.this, CalculatorLayout.class);
        backToCalculator.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(backToCalculator);
    }

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

                widthLength = "length";
            }
        }
    };

    RadioGroup.OnCheckedChangeListener onChangeSameDiff = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.sameRB) {
                sameDiff = "same";

                custSizeET.setVisibility(View.GONE);
                customSize = 0.0;
                custNumberNextBtn.setVisibility(View.GONE);

                blankConditions();

                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);

            } else if (checkedId == R.id.diffRB) {
                sameDiff = "diff";

                custSizeET.setVisibility(View.VISIBLE);
                custSizeET.setText("");
                custNumberNextBtn.setVisibility(View.VISIBLE);
                custNumberNextBtn.setText(R.string.next);
                custNumberNextBtn.setOnClickListener(onClickCustNumberNextBtn);

                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.GONE);
                calcResBtn.setVisibility(View.GONE);
            }
        }
    };

    RadioGroup.OnCheckedChangeListener onChooseConditions = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.evenRB) {
                evenRB.setChecked(true);
                evenOdd = "even";
                mustBeMultiple = "no";
                goneMultiple();
                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.VISIBLE);
                calcResBtn.setVisibility(View.GONE);
            } else if (checkedId == R.id.oddRB) {
                oddRB.setChecked(true);
                evenOdd = "odd";
                mustBeMultiple = "no";
                goneMultiple();
                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.VISIBLE);
                calcResBtn.setVisibility(View.GONE);
            } else if (checkedId == R.id.multRB) {
                multRB.setChecked(true);
                mustBeMultiple = "yes";
                evenOdd = "no";
                baseET.setVisibility(View.VISIBLE);
                plusET.setVisibility(View.VISIBLE);
                minusET.setVisibility(View.VISIBLE);
                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.VISIBLE);
                calcResBtn.setVisibility(View.GONE);
            } else if (checkedId == R.id.noneRB) {
                noneRB.setChecked(true);
                evenOdd = "no";
                mustBeMultiple = "no";
                goneMultiple();
                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.VISIBLE);
                calcResBtn.setVisibility(View.GONE);
            }
        }
    };

    public int calculateResult() {

        multiple = gen.dblFromSource(baseET.getText().toString());
        plus = gen.dblFromSource(plusET.getText().toString());
        minus = gen.dblFromSource(minusET.getText().toString());

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
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
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
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
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
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
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
                        calcBtn.setVisibility(View.VISIBLE);
                        calcResBtn.setVisibility(View.GONE);
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

    public void goneCustomSize() {
        custSizeET.setText("");
        custSizeET.setVisibility(View.GONE);
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
    }

    public void goneConditions() {
        conditionsLabel.setVisibility(View.GONE);
        evenRB.setVisibility(View.GONE);
        oddRB.setVisibility(View.GONE);
        multRB.setVisibility(View.GONE);
        noneRB.setVisibility(View.GONE);
    }

    public void goneMultiple() {
        //mustBeMultiple = "no";
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
            origNumberResTV.setVisibility(View.VISIBLE);

            if (unit == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_data_warning), Toast.LENGTH_LONG).show();
            } else {
                origNumberDec = new DecimalFormat("#.#");
                if (widthLength.equals("width")) {
                    pwM = gen.detMeasurement(
                            pws,
                            pwi,
                            origNumberET);

                    if (pwM == 0.0) {
                        visible = false;
                        Toast.makeText(getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        origNumberResTV.setVisibility(View.GONE);
                        finSizeLabel.setVisibility(View.GONE);
                        sameRB.setVisibility(View.GONE);
                        diffRB.setVisibility(View.GONE);
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        origNumberNextBtn.setText(R.string.next);
                        goneCustomSize();
                        goneConditions();
                        goneMultiple();
                    } else if (pwM != 0.0) {
                        origNumberET.setVisibility(View.GONE);
                        origNumberResTV.setVisibility(View.VISIBLE);
                        blankSameDiff();
                        String stmtCm = getString(R.string.cast_on_result) + " " + origNumberDec.format(pwM) + " " + getString(R.string.cm_label);
                        String stmtInch = getString(R.string.cast_on_result) + " " + origNumberDec.format(pwM) + " " + getString(R.string.inches_label);
                        if (unit.equals("cm")) {
                            origNumberResTV.setText(stmtCm);
                        } else if (unit.equals("inch")) {
                            origNumberResTV.setText(stmtInch);
                        }
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        origNumberNextBtn.setText(R.string.change_button);
                        origNumberNextBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                origNumberResTV.setVisibility(View.GONE);
                                origNumberET.setVisibility(View.VISIBLE);
                                origNumberET.setText("");
                                finSizeLabel.setVisibility(View.GONE);
                                sameRB.setVisibility(View.GONE);
                                diffRB.setVisibility(View.GONE);
                                origNumberNextBtn.setText(R.string.next);
                                origNumberNextBtn.setOnClickListener(onClickOrigNumberNextBtn);
                            }
                        });
                    }
                } else if (widthLength.equals("length")) {
                    plM = gen.detMeasurement(
                            plr,
                            pli,
                            origNumberET);

                    if (plM == 0.0) {
                        visible = false;
                        Toast.makeText(getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                        origNumberResTV.setVisibility(View.GONE);
                        finSizeLabel.setVisibility(View.GONE);
                        sameRB.setVisibility(View.GONE);
                        diffRB.setVisibility(View.GONE);
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        origNumberNextBtn.setText(R.string.next);
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        goneCustomSize();
                        goneConditions();
                        goneMultiple();
                    } else if (plM != 0.0) {
                        origNumberET.setVisibility(View.GONE);
                        origNumberResTV.setVisibility(View.VISIBLE);
                        finSizeLabel.setVisibility(View.VISIBLE);
                        sameRB.setVisibility(View.VISIBLE);
                        diffRB.setVisibility(View.VISIBLE);
                        blankSameDiff();
                        String stmtCm = getString(R.string.cast_on_result) + " " + origNumberDec.format(plM) + " " + getString(R.string.cm_label);
                        String stmtInch = getString(R.string.cast_on_result) + " " + origNumberDec.format(plM) + " " + getString(R.string.inches_label);
                        if (unit.equals("cm")) {
                            origNumberResTV.setText(stmtCm);
                        } else if (unit.equals("inch")) {
                            origNumberResTV.setText(stmtInch);
                        }
                        origNumberNextBtn.setVisibility(View.VISIBLE);
                        origNumberNextBtn.setText(R.string.change_button);
                        origNumberNextBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                origNumberResTV.setVisibility(View.GONE);
                                origNumberET.setVisibility(View.VISIBLE);
                                origNumberET.setText("");
                                finSizeLabel.setVisibility(View.GONE);
                                sameRB.setVisibility(View.GONE);
                                diffRB.setVisibility(View.GONE);
                                origNumberNextBtn.setText(R.string.next);
                                origNumberNextBtn.setOnClickListener(onClickOrigNumberNextBtn);
                            }
                        });
                    }
                }
            }
        }
    };

    View.OnClickListener onClickCustNumberNextBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            blankConditions();

            customSize = gen.dblFromSource(custSizeET.getText().toString());

            if (customSize == 0.0) {
                Toast.makeText(getBaseContext(), R.string.no_number_warning, Toast.LENGTH_LONG).show();
                custNumberNextBtn.setText(R.string.next);
                goneConditions();
                goneMultiple();
                newNumberLabel.setVisibility(View.GONE);
                newNumberResTV.setVisibility(View.GONE);
                calcBtn.setVisibility(View.GONE);
                calcResBtn.setVisibility(View.GONE);
            }

            custNumberNextBtn.setText(R.string.change_button);
            custNumberNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custNumberNextBtn.setText(R.string.next);
                    custSizeET.setText("");
                    custNumberNextBtn.setOnClickListener(onClickCustNumberNextBtn);
                }
            });
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
            calcBtn.setVisibility(View.GONE);
        }
    };

    View.OnClickListener onClickCalcBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcBtn.setVisibility(View.GONE);
            newNumberLabel.setVisibility(View.VISIBLE);
            newNumberResTV.setVisibility(View.VISIBLE);
            calcResBtn.setVisibility(View.VISIBLE);

            calculateResult();

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
