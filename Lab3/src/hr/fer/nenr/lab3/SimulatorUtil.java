package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.*;

public class SimulatorUtil {
    public static final IDomain angleDomain = new SimpleDomain(-90, 91);
    public static final IDomain distanceDomain = new SimpleDomain(0, 1301);
    public static final IDomain velocityDomain = new SimpleDomain(0, 91);
    public static final IDomain accelerationDomain = new SimpleDomain(-40, 41);

    public static int[] closeToShoreBounds = new int[]{55, 70};
    public static int[] reallyCloseToShoreBounds = new int[]{20, 50};
    public static int[] goingTooFastBounds = new int[]{70, 90};
    public static int[] goingTooSlowBounds = new int[]{30, 70};
    public static int[] turnLeftSlightlyBounds = new int[]{150, 165};
    public static int[] turnLeftSharplyBounds = new int[]{160, 180};
    public static int[] turnRightSlightlyBounds = new int[]{20, 30};
    public static int[] turnRightSharplyBounds = new int[]{0, 20};
    public static int[] speedUpBounds = new int[]{30, 40};
    public static int[] slowDownBounds = new int[]{10, 20};


    // Udaljenosti od obale
    public static final IFuzzySet CLOSE_TO_SHORE = new CalculatedFuzzySet(
            distanceDomain, StandardFuzzySets.lFunction(closeToShoreBounds[0], closeToShoreBounds[1]));
    public static final IFuzzySet REALLY_CLOSE_TO_SHORE = new CalculatedFuzzySet(
            distanceDomain, StandardFuzzySets.lFunction(reallyCloseToShoreBounds[0], reallyCloseToShoreBounds[1]));

    // Tocnost smjera
    public static final IFuzzySet WRONG_WAY = new CalculatedFuzzySet(distanceDomain, StandardFuzzySets.lFunction(0, 1));
    public static final IFuzzySet RIGHT_WAY = new CalculatedFuzzySet(distanceDomain, StandardFuzzySets.gammaFunction(0, 1));

    // Brzine
    public static final IFuzzySet GOING_TOO_FAST = new CalculatedFuzzySet(
            velocityDomain, StandardFuzzySets.gammaFunction(goingTooFastBounds[0], goingTooFastBounds[1]));
    public static final IFuzzySet GOING_TOO_SLOW = new CalculatedFuzzySet(
            velocityDomain, StandardFuzzySets.lFunction(goingTooSlowBounds[0], goingTooSlowBounds[1]));

    // Kut za lijevo skretanje
    public static final IFuzzySet TURN_LEFT_SLIGHTLY = new CalculatedFuzzySet(
            angleDomain, StandardFuzzySets.gammaFunction(turnLeftSlightlyBounds[0], turnLeftSlightlyBounds[1]));
    public static final IFuzzySet TURN_LEFT_SHARPLY = new CalculatedFuzzySet(
            angleDomain, StandardFuzzySets.gammaFunction(turnLeftSharplyBounds[0], turnLeftSharplyBounds[1]));

    // Kut za desno skretanje
    public static final IFuzzySet TURN_RIGHT_SLIGHTLY = new CalculatedFuzzySet(
            angleDomain, StandardFuzzySets.lFunction(turnRightSlightlyBounds[0], turnRightSlightlyBounds[1]));
    public static final IFuzzySet TURN_RIGHT_SHARPLY = new CalculatedFuzzySet(
            angleDomain, StandardFuzzySets.lFunction(turnRightSharplyBounds[0], turnRightSharplyBounds[1]));

    // Akceleracija
    public static final IFuzzySet SPEED_UP = new CalculatedFuzzySet(
            accelerationDomain, StandardFuzzySets.gammaFunction(speedUpBounds[0], speedUpBounds[1]));
    public static final IFuzzySet SLOW_DOWN = new CalculatedFuzzySet(
            accelerationDomain, StandardFuzzySets.lFunction(slowDownBounds[0], slowDownBounds[1]));
}
