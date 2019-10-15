package ca.gotchasomething.knitfits;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class General extends AppCompatActivity {

    public Double d = 0.0, dblFromSource = 0.0, measurement = 0.0;
    public int result1 = 0, result2 = 0, result3 = 0, result4 = 0, ultimateResult = 0;
    public String string = null, unit = null;

    public int getResult1(int int1, double dbl1, double dbl2) {
        //for
        // result1 = sameWidthplusplus (multipleSameWidthPlusPlus) & sameWidthnoneplus (multipleSameWidthNonePlus):
        // int1 = sameWidth
        // dbl1 = plus
        // dbl2 = multiple

        //for
        // result1 = sameLengthplusplus (multipleSameLengthPlusPlus) & sameLengthnoneplus (multipleSameLengthNonePlus):
        // int1 = sameLength
        // dbl1 = plus
        // dbl2 = multiple

        //for
        // result1 = diffWidthplusplus (multipleDiffWidthPlusPlus) & diffWidthnoneplus (multipleDiffWidthNonePlus):
        // int1 = diffWidth
        // dbl1 = plus
        // dbl2 = multiple

        //for
        // result1 = diffLengthplusplus (multipleDiffLengthPlusPlus) &  diffLengthnoneplus(multipleDiffLengthNonePlus):
        // int1 = diffLength
        // dbl1 = plus
        // dbl2 = multiple
        result1 = int1 - 1;

        do {
            result1++;
        } while (((result1 - dbl1) % dbl2) != 0);

        return result1;
    }

    public int getResult2(int int2, double dbl3, double dbl4) {
        //for
        // result2 = sameWidthplusminus (multipleSameWidthPlusMinus) & sameWidthnoneminus (multipleSameWidthNoneMinus):
        // int2 = sameWidth
        // dbl3 = plus
        // dbl4 = multiple

        //for
        // result2 = sameLengthplusminus (multipleSameLengthPlusMinus) & sameLengthnoneminus (multipleSameLengthNoneMinus):
        // int2 = sameLength
        // dbl3 = plus
        // dbl4 = multiple

        //for
        // result2 = diffWidthplusminus (multipleDiffWidthPlusMinus) & diffWidthnoneminus (multipleDiffWidthNoneMinus):
        // int2 = diffWidth
        // dbl3 = plus
        // dbl4 = multiple

        //for
        // result2 = diffLengthplusminus (multipleDiffLengthPlusMinus) & diffLengthnoneminus (multipleDiffLengthNoneMinus):
        // int2 = diffLength
        // dbl3 = plus
        // dbl4 = multiple
        result2 = int2 + 1;

        do {
            result2--;
        } while (((result2 - dbl3) % dbl4) != 0);

        return result2;
    }

    public int getResult3(int int3, double dbl5, double dbl6) {
        //for
        // result3 = sameWidthminusplus (multipleSameWidthMinusPlus):
        // int3 = sameWidth
        // dbl5 = minus
        // dbl6 = multiple

        //for
        // result3 = sameLengthminusplus (multipleSameLengthMinusPlus):
        // int3 = sameLength
        // dbl5 = minus
        // dbl6 = multiple

        //for
        // result3 = diffWidthminusplus (multipleDiffWidthMinusPlus):
        // int3 = diffWidth
        // dbl5 = minus
        // dbl6 = multiple

        //for
        // result3 = diffLengthminusplus (multipleDiffLengthMinusPlus):
        // int3 = diffLength
        // dbl5 = minus
        // dbl6 = multiple
        result3 = int3 - 1;

        do {
            result3++;
        } while (((result3 + dbl5) % dbl6) != 0);

        return result3;
    }

    public int getResult4(int int4, double dbl7, double dbl8) {
        //for
        // result4 = sameWidthminusminus (multipleSameWidthMinusMinus):
        // int4 = sameWidth
        // dbl7 = minus
        // dbl8 = multiple

        //for
        // result4 = sameLengthminusminus (multipleSameLengthMinusMinus):
        // int4 = sameLength
        // dbl7 = minus
        // dbl8 = multiple

        //for
        // result4 = diffWidthminusminus (multipleDiffWidthMinusMinus):
        // int4 = diffWidth
        // dbl7 = minus
        // dbl8 = multiple

        //for
        // result4 = diffLengthminusminus (multipleDiffLengthMinusMinus):
        // int4 = diffLength
        // dbl7 = minus
        // dbl8 = multiple
        result4 = int4 + 1;

        do {
            result4--;
        } while (((result4 + dbl7) % dbl8) != 0);

        return result4;
    }

    public int ultimateResult(int intA, int intB, int intC) {
        //for sameWidthplusClosest & sameWidthnoneClosest:
        //intA = result1
        //intB = result2
        //intC = sameWidth

        //for sameWidthminusClosest:
        //intA = result3
        //intB = result4
        //intC = sameWidth

        //for sameLengthplusClosest & sameLengthnoneClosest:
        //intA = result1
        //intB = result2
        //intC = sameLength

        //for sameLengthminusClosest:
        //intA = result3
        //intB = result4
        //intC = sameLength

        //for diffWidthplusClosest & diffWidthnoneClosest:
        //intA = result1
        //intB = result2
        //intC = diffWidth

        //for diffWidthminusClosest:
        //intA = result3
        //intB = result4
        //intC = diffWidth

        //for diffLengthplusClosest & diffLengthnoneClosest:
        //intA = result1
        //intB = result2
        //intC = diffLength

        //for diffLengthminusClosest:
        //intA = result3
        //intB = result4
        //intC = diffLength
        ultimateResult = 0;

        if (intA - intC < intC - intB) {
            ultimateResult = intA;
        } else if (intA - intC == intC - intB) {
            ultimateResult = intA;
        } else {
            ultimateResult = intB;
        }
        return ultimateResult;
    }

    public Double dblFromSource(String str1) {
        //String string = str1;
        if (str1 != null && !str1.equals("")) {
            dblFromSource = Double.valueOf(str1);
        } else {
            dblFromSource = 0.0;
        }
        return dblFromSource;
    }

    public String stringFromSource(String str2) {
        //String string2 = str2;
        if (str2 != null && !str2.equals("")) {
            unit = str2;
        } else {
            unit = null;
        }
        return unit;
    }

    public Double detMeasurement(Double dbl1, Double dbl2, EditText et1, TextView tv1, TextView tv2, RadioButton rb1, RadioButton rb2, Button btn1) {
        //dbl1 = pws or plr
        //dbl2 = pwi or pli
        //et1 = origNumberET
        //tv1 = origNumberResTV
        //tv2 = finSizeLabel
        //rb1 = sameRB
        //rb2 = diffRB
        //btn1 = origNumberNextBtn
        string = et1.getText().toString();
        if (!string.equals("")) {
            d = Double.valueOf(string);

        } else {
            d = 0.0;
            Toast.makeText(getBaseContext(), R.string.no_number_warning,
                    Toast.LENGTH_LONG).show();
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            rb1.setVisibility(View.GONE);
            rb2.setVisibility(View.GONE);
            btn1.setVisibility(View.VISIBLE);
        }

        measurement = (d / (dbl1 / dbl2));

        return measurement;

    }
}
