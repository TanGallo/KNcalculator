package ca.gotchasomething.knitfits;

public class CalculateMultiple extends CalculatorLayout {

    public static int sameWidthplusplus = 0;
    public static int sameWidthplusminus = 0;
    public static int sameWidthplusClosest = 0;
    public static int sameWidthminusplus = 0;
    public static int sameWidthminusminus = 0;
    public static int sameWidthminusClosest = 0;
    public static int sameWidthnoneplus = 0;
    public static int sameWidthnoneminus = 0;
    public static int sameWidthnoneClosest = 0;
    public static int sameLengthplusplus = 0;
    public static int sameLengthplusminus = 0;
    public static int sameLengthplusClosest = 0;
    public static int sameLengthminusplus = 0;
    public static int sameLengthminusminus = 0;
    public static int sameLengthminusClosest = 0;
    public static int sameLengthnoneplus = 0;
    public static int sameLengthnoneminus = 0;
    public static int sameLengthnoneClosest = 0;
    public static int diffWidthplusplus = 0;
    public static int diffWidthplusminus = 0;
    public static int diffWidthplusClosest = 0;
    public static int diffWidthminusplus = 0;
    public static int diffWidthminusminus = 0;
    public static int diffWidthminusClosest = 0;
    public static int diffWidthnoneplus = 0;
    public static int diffWidthnoneminus = 0;
    public static int diffWidthnoneClosest = 0;
    public static int diffLengthplusplus = 0;
    public static int diffLengthplusminus = 0;
    public static int diffLengthplusClosest = 0;
    public static int diffLengthminusplus = 0;
    public static int diffLengthminusminus = 0;
    public static int diffLengthminusClosest = 0;
    public static int diffLengthnoneplus = 0;
    public static int diffLengthnoneminus = 0;
    public static int diffLengthnoneClosest = 0;

    public static int multipleSameWidthPlusPlus() {
        sameWidthplusplus = sameWidth - 1;

        do {
            sameWidthplusplus++;
        } while (((sameWidthplusplus - plus) % multiple) != 0);
        return sameWidthplusplus;
    }

    public static int multipleSameWidthPlusMinus() {
        sameWidthplusminus = sameWidth + 1;

        do {
            sameWidthplusminus--;
        } while (((sameWidthplusminus - plus) % multiple) != 0);
        return sameWidthplusminus;
    }

    public static int multipleSameWidthPlusResult() {
        sameWidthplusClosest = 0;

        if (multipleSameWidthPlusPlus() - sameWidth < sameWidth - multipleSameWidthPlusMinus()) {
            sameWidthplusClosest = multipleSameWidthPlusPlus();
        } else if (multipleSameWidthPlusPlus() - sameWidth == sameWidth - multipleSameWidthPlusMinus()) {
            sameWidthplusClosest = multipleSameWidthPlusPlus();
        } else {
            sameWidthplusClosest = multipleSameWidthPlusMinus();
        }
        return sameWidthplusClosest;
    }

    public static int multipleSameWidthMinusPlus() {
        sameWidthminusplus = sameWidth - 1;

        do {
            sameWidthminusplus++;
        } while (((sameWidthminusplus + minus) % multiple) != 0);
        return sameWidthminusplus;
    }

    public static int multipleSameWidthMinusMinus() {
        sameWidthminusminus = sameWidth + 1;

        do {
            sameWidthminusminus--;
        } while (((sameWidthminusminus + minus) % multiple) != 0);
        return sameWidthminusminus;
    }

    public static int multipleSameWidthMinusResult() {
        sameWidthminusClosest = 0;

        if (multipleSameWidthMinusPlus() - sameWidth < sameWidth - multipleSameWidthMinusMinus()) {
            sameWidthminusClosest = multipleSameWidthMinusPlus();
        } else if (multipleSameWidthMinusPlus() - sameWidth == sameWidth - multipleSameWidthMinusMinus()) {
            sameWidthminusClosest = multipleSameWidthMinusPlus();
        } else {
            sameWidthminusClosest = multipleSameWidthMinusMinus();
        }
        return sameWidthminusClosest;
    }

    public static int multipleSameWidthNonePlus() {
        sameWidthnoneplus = sameWidth - 1;

        do {
            sameWidthnoneplus++;
        } while (((sameWidthnoneplus - plus) % multiple) != 0);
        return sameWidthnoneplus;
    }

    public static int multipleSameWidthNoneMinus() {
        sameWidthnoneminus = sameWidth + 1;

        do {
            sameWidthnoneminus--;
        } while (((sameWidthnoneminus - plus) % multiple) != 0);
        return sameWidthnoneminus;
    }

    public static int multipleSameWidthNoneResult() {
        sameWidthnoneClosest = 0;

        if (multipleSameWidthNonePlus() - sameWidth < sameWidth - multipleSameWidthNoneMinus()) {
            sameWidthnoneClosest = multipleSameWidthNonePlus();
        } else if (multipleSameWidthNonePlus() - sameWidth == sameWidth - multipleSameWidthNoneMinus()) {
            sameWidthnoneClosest = multipleSameWidthNonePlus();
        } else {
            sameWidthnoneClosest = multipleSameWidthNoneMinus();
        }
        return sameWidthnoneClosest;
    }

    public static int multipleSameLengthPlusPlus() {
        sameLengthplusplus = sameLength - 1;

        do {
            sameLengthplusplus++;
        } while (((sameLengthplusplus - plus) % multiple) != 0);
        return sameLengthplusplus;
    }

    public static int multipleSameLengthPlusMinus() {
        sameLengthplusminus = sameLength + 1;

        do {
            sameLengthplusminus--;
        } while (((sameLengthplusminus - plus) % multiple) != 0);
        return sameLengthplusminus;
    }

    public static int multipleSameLengthPlusResult() {
        sameLengthplusClosest = 0;

        if (multipleSameLengthPlusPlus() - sameLength < sameLength - multipleSameLengthPlusMinus()) {
            sameLengthplusClosest = multipleSameLengthPlusPlus();
        } else if (multipleSameLengthPlusPlus() - sameLength == sameLength - multipleSameLengthPlusMinus()) {
            sameLengthplusClosest = multipleSameLengthPlusPlus();
        } else {
            sameLengthplusClosest = multipleSameLengthPlusMinus();
        }
        return sameLengthplusClosest;
    }

    public static int multipleSameLengthMinusPlus() {
        sameLengthminusplus = sameLength - 1;

        do {
            sameLengthminusplus++;
        } while (((sameLengthminusplus + minus) % multiple) != 0);
        return sameLengthminusplus;
    }

    public static int multipleSameLengthMinusMinus() {
        sameLengthminusminus = sameLength + 1;

        do {
            sameLengthminusminus--;
        } while (((sameLengthminusminus + minus) % multiple) != 0);
        return sameLengthminusminus;
    }

    public static int multipleSameLengthMinusResult() {
        sameLengthminusClosest = 0;

        if (multipleSameLengthMinusPlus() - sameLength < sameLength - multipleSameLengthMinusMinus()) {
            sameLengthminusClosest = multipleSameLengthMinusPlus();
        } else if (multipleSameLengthMinusPlus() - sameLength == sameLength - multipleSameLengthMinusMinus()) {
            sameLengthminusClosest = multipleSameLengthMinusPlus();
        } else {
            sameLengthminusClosest = multipleSameLengthMinusMinus();
        }
        return sameLengthminusClosest;
    }

    public static int multipleSameLengthNonePlus() {
        sameLengthnoneplus = sameLength - 1;

        do {
            sameLengthnoneplus++;
        } while (((sameLengthnoneplus - plus) % multiple) != 0);
        return sameLengthnoneplus;
    }

    public static int multipleSameLengthNoneMinus() {
        sameLengthnoneminus = sameLength + 1;

        do {
            sameLengthnoneminus--;
        } while (((sameLengthnoneminus - plus) % multiple) != 0);
        return sameLengthnoneminus;
    }

    public static int multipleSameLengthNoneResult() {
        sameLengthnoneClosest = 0;

        if (multipleSameLengthNonePlus() - sameLength < sameLength - multipleSameLengthNoneMinus()) {
            sameLengthnoneClosest = multipleSameLengthNonePlus();
        } else if (multipleSameLengthNonePlus() - sameLength == sameLength - multipleSameLengthNoneMinus()) {
            sameLengthnoneClosest = multipleSameLengthNonePlus();
        } else {
            sameLengthnoneClosest = multipleSameLengthNoneMinus();
        }
        return sameLengthnoneClosest;
    }

    public static int multipleDiffWidthPlusPlus() {
        diffWidthplusplus = diffWidth - 1;

        do {
            diffWidthplusplus++;
        } while (((diffWidthplusplus - plus) % multiple) != 0);
        return diffWidthplusplus;
    }

    public static int multipleDiffWidthPlusMinus() {
        diffWidthplusminus = diffWidth + 1;

        do {
            diffWidthplusminus--;
        } while (((diffWidthplusminus - plus) % multiple) != 0);
        return diffWidthplusminus;
    }

    public static int multipleDiffWidthPlusResult() {
        diffWidthplusClosest = 0;

        if (multipleDiffWidthPlusPlus() - diffWidth < diffWidth - multipleDiffWidthPlusMinus()) {
            diffWidthplusClosest = multipleDiffWidthPlusPlus();
        } else if (multipleDiffWidthPlusPlus() - diffWidth == diffWidth - multipleDiffWidthPlusMinus()) {
            diffWidthplusClosest = multipleDiffWidthPlusPlus();
        } else {
            diffWidthplusClosest = multipleDiffWidthPlusMinus();
        }
        return diffWidthplusClosest;
    }

    public static int multipleDiffWidthMinusPlus() {
        diffWidthminusplus = diffWidth - 1;

        do {
            diffWidthminusplus++;
        } while (((diffWidthminusplus + minus) % multiple) != 0);
        return diffWidthminusplus;
    }

    public static int multipleDiffWidthMinusMinus() {
        diffWidthminusminus = diffWidth + 1;

        do {
            diffWidthminusminus--;
        } while (((diffWidthminusminus + minus) % multiple) != 0);
        return diffWidthminusminus;
    }

    public static int multipleDiffWidthMinusResult() {
        diffWidthminusClosest = 0;

        if (multipleDiffWidthMinusPlus() - diffWidth < diffWidth - multipleDiffWidthMinusMinus()) {
            diffWidthminusClosest = multipleDiffWidthMinusPlus();
        } else if (multipleDiffWidthMinusPlus() - diffWidth == diffWidth - multipleDiffWidthMinusMinus()) {
            diffWidthminusClosest = multipleDiffWidthMinusPlus();
        } else {
            diffWidthminusClosest = multipleDiffWidthMinusMinus();
        }
        return diffWidthminusClosest;
    }

    public static int multipleDiffWidthNonePlus() {
        diffWidthnoneplus = diffWidth - 1;

        do {
            diffWidthnoneplus++;
        } while (((diffWidthnoneplus - plus) % multiple) != 0);
        return diffWidthnoneplus;
    }

    public static int multipleDiffWidthNoneMinus() {
        diffWidthnoneminus = diffWidth + 1;

        do {
            diffWidthnoneminus--;
        } while (((diffWidthnoneminus - plus) % multiple) != 0);
        return diffWidthnoneminus;
    }

    public static int multipleDiffWidthNoneResult() {
        diffWidthnoneClosest = 0;

        if (multipleDiffWidthNonePlus() - diffWidth < diffWidth - multipleDiffWidthNoneMinus()) {
            diffWidthnoneClosest = multipleDiffWidthNonePlus();
        } else if (multipleDiffWidthNonePlus() - diffWidth == diffWidth - multipleDiffWidthNoneMinus()) {
            diffWidthnoneClosest = multipleDiffWidthNonePlus();
        } else {
            diffWidthnoneClosest = multipleDiffWidthNoneMinus();
        }
        return diffWidthnoneClosest;
    }

    public static int multipleDiffLengthPlusPlus() {
        diffLengthplusplus = diffLength - 1;

        do {
            diffLengthplusplus++;
        } while (((diffLengthplusplus - plus) % multiple) != 0);
        return diffLengthplusplus;
    }

    public static int multipleDiffLengthPlusMinus() {
        diffLengthplusminus = diffLength + 1;

        do {
            diffLengthplusminus--;
        } while (((diffLengthplusminus - plus) % multiple) != 0);
        return diffLengthplusminus;
    }

    public static int multipleDiffLengthPlusResult() {
        diffLengthplusClosest = 0;

        if (multipleDiffLengthPlusPlus() - diffLength < diffLength - multipleDiffLengthPlusMinus()) {
            diffLengthplusClosest = multipleDiffLengthPlusPlus();
        } else if (multipleDiffLengthPlusPlus() - diffLength == diffLength - multipleDiffLengthPlusMinus()) {
            diffLengthplusClosest = multipleDiffLengthPlusPlus();
        } else {
            diffLengthplusClosest = multipleDiffLengthPlusMinus();
        }
        return diffLengthplusClosest;
    }

    public static int multipleDiffLengthMinusPlus() {
        diffLengthminusplus = diffLength - 1;

        do {
            diffLengthminusplus++;
        } while (((diffLengthminusplus + minus) % multiple) != 0);
        return diffLengthminusplus;
    }

    public static int multipleDiffLengthMinusMinus() {
        diffLengthminusminus = diffLength + 1;

        do {
            diffLengthminusminus--;
        } while (((diffLengthminusminus + minus) % multiple) != 0);
        return diffLengthminusminus;
    }

    public static int multipleDiffLengthMinusResult() {
        diffLengthminusClosest = 0;

        if (multipleDiffLengthMinusPlus() - diffLength < diffLength - multipleDiffLengthMinusMinus()) {
            diffLengthminusClosest = multipleDiffLengthMinusPlus();
        } else if (multipleDiffLengthMinusPlus() - diffLength == diffLength - multipleDiffLengthMinusMinus()) {
            diffLengthminusClosest = multipleDiffLengthMinusPlus();
        } else {
            diffLengthminusClosest = multipleDiffLengthMinusMinus();
        }
        return diffLengthminusClosest;
    }

    public static int multipleDiffLengthNonePlus() {
        diffLengthnoneplus = diffLength - 1;

        do {
            diffLengthnoneplus++;
        } while (((diffLengthnoneplus - plus) % multiple) != 0);
        return diffLengthnoneplus;
    }

    public static int multipleDiffLengthNoneMinus() {
        diffLengthnoneminus = diffLength + 1;

        do {
            diffLengthnoneminus--;
        } while (((diffLengthnoneminus - plus) % multiple) != 0);
        return diffLengthnoneminus;
    }

    public static int multipleDiffLengthNoneResult() {
        diffLengthnoneClosest = 0;

        if (multipleDiffLengthNonePlus() - diffLength < diffLength - multipleDiffLengthNoneMinus()) {
            diffLengthnoneClosest = multipleDiffLengthNonePlus();
        } else if (multipleDiffLengthNonePlus() - diffLength == diffLength - multipleDiffLengthNoneMinus()) {
            diffLengthnoneClosest = multipleDiffLengthNonePlus();
        } else {
            diffLengthnoneClosest = multipleDiffLengthNoneMinus();
        }
        return diffLengthnoneClosest;
    }
}
