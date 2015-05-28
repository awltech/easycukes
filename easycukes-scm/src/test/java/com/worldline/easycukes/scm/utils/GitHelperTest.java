/**
 *
 */
package com.worldline.easycukes.scm.utils;

import java.io.File;

import com.worldline.easycukes.commons.Constants;
import com.worldline.easycukes.commons.config.EasyCukesConfiguration;
import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import com.worldline.easycukes.commons.helpers.FileHelper;

/**
 * The class <code>GitHelperTest</code> contains tests for the class
 * <code>{@link GitHelper}</code>.
 *
 * @author a513260
 */
public class GitHelperTest {

    private String username;
    private String password;
    private String baseurl;

    /**
     * Run the void clone(String,String,String,String) method test.
     *
     * @throws Exception
     */
    // Not a valid unit test
    @Test @Ignore
    public void cloneTest() throws Exception {
        final String fullpath = baseurl + "/easycukes-test/cukes-test";
        final String directory = "/tmp/githelpertest/testclone";
        GitHelper.clone(fullpath, username, password, directory);
        File file = new File(directory);
        File gitFile = new File(directory + "/.git");
        Assert.assertTrue(file.isDirectory());
        Assert.assertTrue(gitFile.isDirectory());
    }

    /**
     * Run the void commitAndPush(File,String,String,String) method test.
     *
     * @throws Exception
     */
    // Not a valid unit test
    @Test @Ignore
    public void commitAndPushTest() throws Exception {
        final String cloneDirectory = "/tmp/githelpertest/testclone";
        File fileToCommit = new File(cloneDirectory + "/test.txt");
        if (!fileToCommit.exists())
            fileToCommit.createNewFile();
        FileUtils.writeStringToFile(fileToCommit,
                "junit test for commit and push");
        GitHelper.commitAndPush(new File(cloneDirectory), username, password,
                "junit test for commit and push");
        // clone repository again and verify if the file commited is present in
        // directory
        final String commitDirectory = "/tmp/githelpertest/testcommit";
        final String fullpath = baseurl + "/easycukes-test/cukes-test";
        GitHelper.clone(fullpath, username, password, commitDirectory);
        File fileCommitted = new File(commitDirectory + "/test.txt");
        Assert.assertTrue(fileCommitted.exists());

    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception if the initialization fails for some reason
     */
    @Before
    public void setUp() throws Exception {
        EasyCukesConfiguration<CommonConfigurationBean> config = new EasyCukesConfiguration<>(CommonConfigurationBean.class);
        if (config.isProxyNeeded())
            config.configureProxy();

        if (config.getValues().credentials != null) {
            username = config.getValues().credentials.getLogin() != null ? config.getValues().credentials.getLogin() : "";
            password = config.getValues().credentials.getPassword() != null ? config.getValues().credentials.getPassword() : "";
        }
        baseurl = config.getValues().getVariables().get(Constants.BASEURL);
    }

    /**
     * Perform clean-up after all tests have been passed.
     *
     * @throws Exception if the clean-up fails for some reason
     */
    @AfterClass
    public static void tearDown() throws Exception {
        FileHelper.deleteDirectory(new File("/tmp/githelpertest/"));
    }
}
