/*
 * EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.
 * Copyright (C) 2014 Worldline or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.worldline.easycukes.scm.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.worldline.easycukes.commons.utils.Constants;

/**
 * {@link SvnHelper} allows to ease manipulation of SVN repositories. It uses
 * internally the SVNKit API, and allows to perform all the basic operations
 * linked to a SVN repository (checkout, commit, etc.).
 *
 * @author mechikhi
 * @version 1.0
 */
public class SvnHelper {

	/**
	 * The Logger to be used in the to keep track of the execution
	 */
	private final static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * The internal {@link SVNClientManager} to be used for interacting with the
	 * remote SVN repository
	 */
	private static SVNClientManager svnClientManager;

	/**
	 * creates an instance of SVNClientManager
	 *
	 * @param username
	 *            the username to be used in order to interact with the remote
	 *            SVN repository
	 * @param password
	 *            the password linked with the specified username to be used for
	 *            authentication
	 * @return an instance of {@link SVNClientManager} that can be used in order
	 *         to interact with the remote SVN repository
	 */
	private static SVNClientManager getSVNClientManager(String username,
			String password) {
		if (svnClientManager == null) {
			final ISVNOptions options = SVNWCUtil.createDefaultOptions(
					new File("/tmp/svnconfig"), true);
			final BasicAuthenticationManager basicAuthenticationManager = new BasicAuthenticationManager(
					username, password);
			if (System.getProperty("https.proxyHost") != null
					&& System.getProperty("https.proxyPort") != null)
				basicAuthenticationManager
				.setProxy(System.getProperty("https.proxyHost"),
						Integer.parseInt(System
								.getProperty("https.proxyPort")), null,
								null);
			// Creates an instance of SVNClientManager
			svnClientManager = SVNClientManager.newInstance(options,
					basicAuthenticationManager);
		}
		return svnClientManager;
	}

	/**
	 * Checks out a working copy of the SVN repository located at the specified
	 * URL
	 *
	 * @param url
	 *            the URL of the SVN repository to be checked out
	 * @param username
	 *            the username to be used in order to interact with the remote
	 *            SVN repository
	 * @param password
	 *            the password linked to the specified username to be used for
	 *            the authentication
	 * @param directory
	 *            the directory in which the SVN repository should be checked
	 *            out
	 * @throws SVNException
	 *             if something's going wrong while checking out the repository
	 */
	public static void checkout(String url, String username, String password,
			String directory) throws SVNException {
		final SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
		final SVNUpdateClient updateClient = getSVNClientManager(username,
				password).getUpdateClient();
		LOGGER.info("Checking out SVN repository located in " + url);
		/*
		 * sets externals not to be ignored during the checkout
		 */
		updateClient.setIgnoreExternals(false);
		int maxAttemps = 5;
		while (maxAttemps > 0)
			try {
				// Checkouting a working copy of url
				updateClient.doCheckout(repositoryURL, new File(directory),
						SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,
						true);
				break;
			} catch (final SVNException e) {
				maxAttemps--;
				if (maxAttemps > 0) {
					LOGGER.warn(e.getMessage() + " | Waiting 5 seconds ...");
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException e1) {
					}
				} else {
					LOGGER.error(e.getMessage());
					svnClientManager.dispose();
					throw e;
				}
			}
	}

	/**
	 * Recursively commiting the working copy on the remote SVN repository
	 *
	 * @param directory
	 *            the directory in which the local working copy of the SVN
	 *            repository is located
	 * @param username
	 *            the username to be used in order to interact with the remote
	 *            SVN repository
	 * @param password
	 *            the password associated with the specified username to be used
	 *            for authentication
	 * @param message
	 *            the message to be used while committing the changes
	 * @throws SVNException
	 *             of something's going wrong while committing the changes
	 */
	public static void commit(File directory, String username, String password,
			String message) throws SVNException {
		LOGGER.info("Committing changes on the SVN repository located in "
				+ directory.toString());
		final boolean keepLocks = false;
		final boolean force = true;
		final boolean keepChangelist = false;
		final boolean includeIgnored = false;
		final boolean makeParents = false;
		final boolean climbUnversionedParents = false;
		final boolean mkdir = false;
		final java.lang.String[] changelists = null;
		final SVNProperties revisionProperties = null;
		final SVNClientManager svnClientManager = getSVNClientManager(username,
				password);
		final List<File> listToCommit = new ArrayList<File>();
		// add to version control
		try {
			final SVNWCClient svnwcClient = svnClientManager.getWCClient();
			for (final String filePath : directory.list())
				if (!filePath.equals(".svn")) {
					final File file = new File(directory, filePath);
					listToCommit.add(file);
					svnwcClient.doAdd(file, force, mkdir,
							climbUnversionedParents, SVNDepth.INFINITY,
							includeIgnored, makeParents);
				}
		} catch (final SVNException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		// commit
		// the attempt will berepeated several times in case of failures
		int maxAttemps = 5;
		while (maxAttemps > 0)
			try {
				// commit
				svnClientManager.getCommitClient().doCommit(
						listToCommit.toArray(new File[listToCommit.size()]),
						keepLocks, message, revisionProperties, changelists,
						keepChangelist, force, SVNDepth.INFINITY);
				break;
			} catch (final SVNException e) {
				maxAttemps--;
				if (maxAttemps > 0) {
					LOGGER.warn(e.getMessage() + " | Waiting 5 seconds ...");
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException e1) {
					}
				} else {
					LOGGER.error(e.getMessage());
					svnClientManager.dispose();
					throw e;
				}
			}
		svnClientManager.dispose();
	}

}
