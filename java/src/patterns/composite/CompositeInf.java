package patterns.composite;

import java.awt.*;

public abstract class CompositeInf {
    public Color brightenToWhite(Color color) {
        Color clBrighter = Blend(color, Color.white, 0.25f);
        return clBrighter;
    }

    Color Blend(Color clOne, Color clTwo, float fAmount) {
        float fInverse = 1.0f - fAmount;

        // I had to look up getting colour components in java.  Google is good :)
        float afOne[] = new float[3];
        clOne.getColorComponents(afOne);
        float afTwo[] = new float[3];
        clTwo.getColorComponents(afTwo);

        float afResult[] = new float[3];
        afResult[0] = afOne[0] * fAmount + afTwo[0] * fInverse;
        afResult[1] = afOne[1] * fAmount + afTwo[1] * fInverse;
        afResult[2] = afOne[2] * fAmount + afTwo[2] * fInverse;

        return new Color (afResult[0], afResult[1], afResult[2]);
    }
}
