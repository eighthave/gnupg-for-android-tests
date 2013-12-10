
package info.guardianproject.gpg.tests.junit;

import info.guardianproject.gpg.tests.NativeHelper;

import java.io.File;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lookout.keyswap.gpg.GPGCli;
import com.lookout.keyswap.gpg.GPGKey;

public class GpgCliTests extends AndroidTestCase {
    public static final String TAG = "GpgCliTests";

    private final GPGCli gpgcli = GPGCli.getInstance();
    private File testFilePath;

    protected void setUp() throws Exception {
        Log.i(TAG, "setUp");
        super.setUp();
        NativeHelper.setup(getContext());
        testFilePath = NativeHelper.app_test_files;
        assert testFilePath.canRead();
        assert testFilePath.canWrite();
        assert testFilePath.isDirectory();
    }

    protected void tearDown() throws Exception {
        Log.i(TAG, "tearDown");
        super.tearDown();
    }

    public void testImportPublicKeys() {
        Log.i(TAG, "testImportPublicKeys");
        Log.i(TAG, "BEFORE");
        List<GPGKey> before = gpgcli.getPublicKeys();
        assertTrue("the keyring should be empty!", before.size() == 0);
        for (GPGKey key : before)
            Log.i(TAG, "key: " + key.getKeyId());
        gpgcli.importKey(new File(testFilePath, "public-keys.pkr"));
        Log.i(TAG, "AFTER");
        List<GPGKey> after = gpgcli.getPublicKeys();
        assertTrue("the keyring should have keys in it", after.size() == 12);
        for (GPGKey key : after)
            Log.i(TAG, "key: " + key.getKeyId());

    }

    public void testForKeyPresence() {
        Log.i(TAG, "testForKeyPresence");
        List<GPGKey> keys = gpgcli.getPublicKeys();
        for (GPGKey key : keys)
            if (key.getFingerprint().equals("5E61C8780F86295CE17D86779F0FE587374BBE81")) {
                assert true;
                return;
            }
        boolean notfound = true;
        for (GPGKey key : keys) {
            String fp = key.getFingerprint();
            if (fp.equals("")
                    || fp.equals("0")
                    || fp.equals("0000000000000000000000000000000000000000")
                    || fp.equals("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                    // one off of real fp
                    || fp.equals("5E61C8780F86295CE17D86779F0FE587374BBE82"))
                notfound = false;
        }
        assert notfound;

    }

    public void testFileVerify() {
        Log.i(TAG, "testFileVerify");
        File sig = new File(testFilePath, "icon.png.sig");
        assert gpgcli.verify(sig);
        File asc = new File(testFilePath, "icon.png.asc");
        assert gpgcli.verify(asc);
    }
}
