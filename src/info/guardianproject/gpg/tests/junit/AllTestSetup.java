
package info.guardianproject.gpg.tests.junit;

import info.guardianproject.gpg.tests.NativeHelper;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * This is here only to unpack the test files from assets, and any other pre-test prep.
 * @author hans
 *
 */
public class AllTestSetup extends AndroidTestCase {
    public static final String TAG = "AllTestSetup";

    protected void setUp() throws Exception {
        Log.i(TAG, "setUp");
        super.setUp();
        NativeHelper.setup(getContext());
        NativeHelper.recreateGnupgHome();
        NativeHelper.unpackAssets();
    }

    protected void tearDown() throws Exception {
        Log.i(TAG, "tearDown");
        super.tearDown();
    }
}
