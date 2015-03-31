package co.atlas_tech.atlasandroid.activesupport;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import co.atlas_tech.atlasandroid.activesupport.inflector.DefaultInflections;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class InflectorTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DefaultInflections.initializeDefaultInflections();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        DefaultInflections.getDefaultInflections().clear();
    }

    public void testCamelize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testCapitalize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testClassify() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testConstantize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testDasherize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testDeconstantize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testDemodulize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testForeignKey() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testHumanize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testOrdinal() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testOrdinalize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testParameterize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testPluralize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testSafeConstantize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testSingularize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testTableize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testTitlecase() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testTitleize() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }

    public void testUnderscore() {
        boolean testImplemented = false;
        Assert.assertTrue("Test not implemented yet.", testImplemented);
    }
}
