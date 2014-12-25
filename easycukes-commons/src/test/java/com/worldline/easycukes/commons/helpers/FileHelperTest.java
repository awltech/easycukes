package com.worldline.easycukes.commons.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.worldline.easycukes.commons.Configuration;

/**
 * The class <code>FileHelperTest</code> contains tests for the class
 * <code>{@link FileHelper}</code>.
 * 
 * @author m.echikhi
 */
public class FileHelperTest {

	private String locDir;

	/**
	 * Run the void download(String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testDownload() throws Exception {
		String from = "https://jsf-examples.googlecode.com/files/simple-jsf-example.zip";
		FileHelper.download(from, locDir);
		String zipFilePath = null;

		// Get a file zip in local repository
		for (final String filePath : new File(locDir).list())
			if (filePath.contains(".zip")) {
				zipFilePath = locDir + "/" + filePath;
				break;
			}
		assertNotNull(zipFilePath);

	}

	/**
	 * Run the void unzip(String,String,boolean) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testUnzip() throws Exception {
		String to = locDir;
		boolean isRemote = true;

		FileHelper.unzip("target/test-classes/simple-jsf-example.zip", to,
				isRemote);

	}

	/**
	 * Run the boolean deleteDirectory(File) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testDeleteDirectory() throws Exception {

		boolean result = FileHelper.deleteDirectory(new File(locDir));

		assertEquals(true, result);
	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 * @generatedBy CodePro at 25/12/14 16:03
	 */
	@Before
	public void setUp() throws Exception {
		locDir = "target/tmp";

		Configuration.configureProxy();
	}

}