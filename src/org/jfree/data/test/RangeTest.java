package src.org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;



public class RangeTest {
    private Range exampleRange;
    private Range exampleRangeTwo;
    private Range sampleCombine;

    public static double delta = 0.000000001d;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        exampleRange = new Range(-1, 1);
        exampleRangeTwo = new Range(-3, 0);

        sampleCombine = new Range(-3, 1);
    }

    // cant use functions that we are testing in testing
    // test we have been given. DO NOT consider this one of the tests that we get
    // graded for.
    @Test
    public void centralValueShouldBeZero() {
        assertEquals("The central value of -1 and 1 should be 0",
                0, exampleRange.getCentralValue(), delta); // delta is the epsilon, its the value that the numbers can be off by
    }//checked



    @Test
    public void FailsUpperBoundShouldBeOne() {

        assertEquals("The upper bound of -1 and 1 should be 1", // since its a number output, the asset can either
                // equal expected or not
                1, exampleRange.getUpperBound(), delta);
    }//checked

    @Test
    public void LowerBoundShouldBeNegativeOne() {
        assertEquals("The lower bound of -1 and 1 should be -1",
                -1, exampleRange.getLowerBound(), delta);
    } //checked
///

    // testing function combine
    @Test
    public void CombineShouldCombine() {
        Range result = Range.combine(exampleRange, exampleRangeTwo);
        assertEquals("The combination of a range (-1,1) and (-3,0) should be (-3,1)", sampleCombine, result);
    }     // checked

    // testing function contain
    @Test
    public void ContainShouldReturnTrue() {
        double value = 0;
        assertTrue("Value 0 should be contained in range (-1,1)", exampleRange.contains(value));
        // this
    }      // checked

    //new
    @Test
    public void ContainShouldReturnTrueOnUpperBoundary(){
        double upperBound = 1;
        assertTrue("Value 1 should be considered in the range (-1,1)", exampleRange.contains(upperBound));
    }

    //new
    @Test
    public void ContainShouldReturnTrueOnLowerBoundary(){
        double lowerBound = -1;
        assertTrue("Value 1 should be considered in the range (-1,1)", exampleRange.contains(lowerBound));
    }


    @Test
    public void ContainShouldReturnFalse() {
        double value = -5;
        assertFalse("Value -5 should NOT be contained in range (-1,1)", exampleRange.contains(value)); // know when to
        // assert true,
        // false, equals,
        // notEquals, etc.
        // this
    }     // checked

    // this test comes after the tests for lower bound and upper bound so that they
    // catch the errors first
    // testing function intersect


    //NOTE: Intersect has about 10 different cases, will add them later if I have the time
    @Test
    public void IntersectsShouldReturnTrueWhenRangesOverlap() {
        Range range1 = new Range(1, 5);
        assertTrue("Ranges (1,5) and (3,7) should overlap",
                range1.intersects(3, 7));

    }  // checked


    // testing function intersect ... same as above but the other way
    @Test
    public void IntersectsShouldReturnFalseWhenRangesDoNotOverlap() {
        Range range1 = new Range(1, 5);
        assertFalse("Ranges (1,5) and (6,10) should not overlap",
                range1.intersects(6, 10)); // this
    } //checked

    // testing function constrain for a value within a Range
    @Test
    public void ConstrainWithinRangeShouldReturnSameValue() {
        double valueWithinRange = 0.5;
        double constrainedValue = exampleRange.constrain(valueWithinRange);
        assertEquals("Constraining a value within the range should return the same value", valueWithinRange,
                constrainedValue, delta);
    }//checked

    //new
    @Test
    public void ConstrainOnUpperBoundaryShouldReturnBoundary(){
        double value = 1;
        assertEquals("value is the upper bound, therefore constrain should return the upper bound", 1, exampleRange.constrain(value), delta);
    }

    //new
    @Test
    public void ConstrainOnLowerBoundaryShouldReturnBoundary(){
        double value = -1;
        assertEquals("value is the lower bound, therefore constrain should return the lower bound", -1, exampleRange.constrain(value), delta);
    }



    // testing function constrain
//    @Test
//    public void ConstrainAboveUpperBoundShouldReturnUpperBound() {
//        double valueAboveUpperBound = 2.0;
//        double constrainedValue = exampleRange.constrain(valueAboveUpperBound);
//
//        assertEquals("Constraining a value above the upper bound should return the upper bound",
//                exampleRange.getUpperBound(), constrainedValue, delta);
//        // this
//    }
    @Test
    public void ConstrainAboveUpperBoundShouldReturnUpperBound() {
        double valueAboveUpperBound = 2.0;
        double constrainedValue = exampleRange.constrain(valueAboveUpperBound);

        assertEquals("Constraining a value above the upper bound should return the upper bound",
                1, constrainedValue, delta);
        // this
    }     // checked

    // testing function constrain
    //new
    @Test
    public void constrainLessThanLowerBoundReturnsLowerBound(){
       double value = -2.0;
       assertEquals("value is below bounds, therefore should return the lower bound", -1, exampleRange.constrain(value), delta);
    }

//    @Test
//    public void ConstrainBelowLowerBoundShouldReturnCentralValue() {
//        double valueBelowLowerBound = -2.0;
//        double constrainedValue = exampleRange.constrain(valueBelowLowerBound);
//        assertEquals("Constraining a value below the lower bound should return the central value",
//                exampleRange.getCentralValue(), constrainedValue, 0.000000001d);
//    }



    // testing function shift
    @Test
    public void ShiftWithZeroCrossingShouldReturnShiftedRange() {
        double delta = 0.5;
        Range shiftedRange = Range.shift(exampleRange, delta);
        double expectedLower = -1 + delta;
        double expectedUpper = 1 + delta;
        Range expectedRange = new Range(expectedLower, expectedUpper);

        assertEquals("Shifting the range with zero-crossing should return the shifted range", expectedRange,
                shiftedRange);
    }

    // testing function shift
//    @Test
//    public void ShiftWithoutZeroCrossingShouldNotReturnShiftedRange() {
//        double delta = 0.5;
//        Range shiftedRange = Range.shift(exampleRange, delta, false);
//        double expectedLower = exampleRange.getLowerBound() + delta;
//        double expectedUpper = exampleRange.getUpperBound() + delta;
//        Range expectedRange = new Range(expectedLower, expectedUpper);
//
//        assertEquals("Shifting the range without zero-crossing should change the boundary that crosses zero to 0", expectedRange,
//                shiftedRange);
//    }


    //new
    @Test
    public void ShiftWithFalseZeroCrossingLeftBoundCrossing(){
        //range is [-1,1] if we shift by 3 it becomes [1,3] but since we have zero crossing is false, and range stays the same, then [0,2]?
        Range shiftedRange = Range.shift(exampleRange, 3, false);
        Range expectedRange = new Range (0,2);
        assertSame(expectedRange, shiftedRange);
    }

    //new
    @Test
    public void ShiftWithFalseZeroCrossingRightBoundCrossing(){
        //range is [-4,-2] if we shift by 3 it becomes [-1,1] but since we have zero crossing is false, and range stays the same, then [-2,0]?
        Range sample = new Range(-4,-2);
        Range shiftedRange = Range.shift(sample, 3, false);
        Range expectedRange = new Range (-2,0);
        assertSame(expectedRange, shiftedRange);
    }

    //testing Expansion method
    @Test
    public void NoExpansionNecessary() {
        double lowerLimit = -0.5;
        double upperLimit = 0.5;
        assertEquals("Expanding a range with values within that range should return the same range", // this
                exampleRange, Range.expand(exampleRange, lowerLimit, upperLimit));
    } 

    @Test
    public void BothExpansionNecessary() {
        // instead of testing function getLength, use a stub and hardcode that instead
        double lowerLimit = -2.5;
        double higherLimit = 2.5;
        Range expectedOutput = new Range(-2.5, 2.5); // this is hard coded
        assertEquals(
                "Attempting to expand a range using values outside that range should return the new range with both values changed",
                expectedOutput, Range.expand(exampleRange, lowerLimit, higherLimit));
        // this
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
