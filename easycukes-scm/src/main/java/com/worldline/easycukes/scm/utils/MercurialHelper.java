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
import java.io.IOException;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.tmatesoft.hg.core.HgAddRemoveCommand;
import org.tmatesoft.hg.core.HgCloneCommand;
import org.tmatesoft.hg.core.HgCommitCommand;
import org.tmatesoft.hg.core.HgException;
import org.tmatesoft.hg.core.HgPushCommand;
import org.tmatesoft.hg.core.HgRepoFacade;
import org.tmatesoft.hg.core.HgRepositoryNotFoundException;
import org.tmatesoft.hg.repo.HgLookup;
import org.tmatesoft.hg.repo.HgRemoteRepository;
import org.tmatesoft.hg.util.CancelledException;
import org.tmatesoft.hg.util.Outcome;
import org.tmatesoft.hg.util.Path;

/**
 * {@link MercurialHelper} allows to ease manipulation of Hg repositories. It
 * uses internally the Hg4J API, and allows to perform all the basic operations
 * linked to a Mercurial repository (clone, commit, etc.)
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class MercurialHelper {
	/*
	 * To replace string starting with http
	 */
	private static final String HTTP = "http://";
	
	/*
	 * To replace string starting with https
	 */
	private static final String HTTPS = "https://";

	/**
	 * Clones a remote repository in local directory. The attempt will be
	 * repeated several times in case of failures.
	 *
	 * @param url       the URL of the Hg repository to be cloned
	 * @param username  the username to use in order to clone the repository
	 * @param password  the password linked to the specified username to be used for
	 *                  authentication
	 * @param directory the directory in which the Hg repository should be cloned
	 * @throws HgException        if something's going wrong while interacting with the Hg
	 *                            repository
	 * @throws CancelledException if cloning of the repository has been canceled for some
	 *                            reason (and that reason will be explained in the exception
	 *                            message)
	 */
	public static void clone(@NonNull String url, String username, String password, String directory) throws HgException{
		log.info("Cloning from " + url + " to " + directory);
		// lookup a remote repository
		HgRemoteRepository hgRemote;
		try {
			hgRemote = new HgLookup().detectRemote(
					buildRepoUrl(url, username, password), null);
			log.info("Hg repository cloned from: " + url + " to: "
					+ directory);
		} catch (final HgException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		// clone a remote repository
		final HgCloneCommand cloneCmd = new HgCloneCommand();
		cloneCmd.source(hgRemote);
		cloneCmd.destination(new File(directory));
		// the attempt will berepeated several times in case of failures
		int maxAttemps = 5;
		while (maxAttemps > 0)
			try {
				cloneCmd.execute();
				break;
			} catch (final HgException | CancelledException e) {
				maxAttemps--;
				if (maxAttemps > 0) {
					log.warn(e.getMessage() + " | Waiting 5 seconds ...");
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException e1) {
						log.error(e1.getMessage());
					}
				} else {
					log.error(e.getMessage());
				}
			}
	}


	/**
	 * Commits, pushes and sets comment represented by message. The attempt will
	 * be repeated several times in case of failures.
	 *
	 * @param url       the URL of the remote Hg repository
	 * @param username  the username to be used for pushing the changes
	 * @param password  the password linked to the specified username to be used for
	 *                  authentication
	 * @param directory the directory in which the local hg repository is located
	 * @param message   the message to be used for the commit
	 * @throws Exception if something's going wrong in that process. Various kinds of
	 *                   exception can be launched... {@link CancelledException},
	 *                   {@link HgException}, {@link HgRepositoryNotFoundException},
	 *                   {@link IOException}...
	 */
	public static void commitAndPush(@NonNull String url, String username,
									 String password, File directory, String message) throws Exception {
		log.info("Commiting changes in Hg repository located in "
				+ directory.toString());
		final HgRepoFacade hgRepo = new HgRepoFacade();
		HgRemoteRepository hgRemote = null;
		try {
			// To initialize the existing repository:
			if (!hgRepo.initFrom(directory))
				throw new HgRepositoryNotFoundException(
						"Can't find repository in " + directory);
			hgRemote = new HgLookup().detectRemote(
					buildRepoUrl(url, username, password),
					hgRepo.getRepository());
		} catch (final HgException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		// add files
		final HgAddRemoveCommand addRemoveCmd = hgRepo.createAddRemoveCommand();
		addFiles(addRemoveCmd, directory, null);
		addRemoveCmd.execute();
		
		// Commit
		final HgCommitCommand commitCmd = hgRepo.createCommitCommand();
		commitCmd.message(message);
		final Outcome outcome = commitCmd.execute();
		if (!outcome.isOk()) {
			log.error(outcome.getMessage());
			throw outcome.getException();
		}
		// Push
		final HgPushCommand pushCmd = hgRepo.createPushCommand().destination(
				hgRemote);
		// the attempt will berepeated several times in case of failures
		int maxAttemps = 5;
		while (maxAttemps > 0)
			try {
				pushCmd.execute();
				break;
			} catch (final HgException e) {
				maxAttemps--;
				if (maxAttemps > 0) {
					log.warn(e.getMessage() + " | Waiting 5 seconds ...");
					try {
						Thread.sleep(5000);
					} catch (final InterruptedException e1) {}
				} else {
					log.error(e.getMessage());
					throw e;
				}
			}
		log.info("Changes commited and pushed on Hg repository...");
	}


	/**
	 * Add concerned files to commit
	 *
	 * @param addRemoveCmd the {@link HgAddRemoveCommand} to be executed
	 * @param location     the location on which files should be added
	 * @param parentDir    the parent directory in which files should be added
	 */
	private static void addFiles(HgAddRemoveCommand addRemoveCmd,
								 File location, String parentDir) {
		final File current = parentDir != null ? new File(location, parentDir)
				: location;
		for (final String filePath : current.list())
			if (!".hg".equals(filePath)) {
				final File file = new File(current, filePath);
				final String nextDir = parentDir != null ? parentDir + "/"
						+ filePath : filePath;
				if (file.isDirectory())
					addFiles(addRemoveCmd, location, nextDir);
				else
					addRemoveCmd.add(Path.create(nextDir));
			}
	}


	/**
	 * Build URL with authentication parameters
	 *
	 * @param remotePath the URL of the remote Hg repository
	 * @param username   the username to be used for interacting with the repository
	 * @param password   the password corresponding to the specified username
	 * @return an URL of the remote Hg repository integrating both the username
	 * and password
	 */
	private static String buildRepoUrl(String remotePath, String username,
									   String password) {
		if (remotePath.startsWith(HTTPS))
			return remotePath.replace(HTTPS, HTTPS + username + ":"
					+ password + "@");

		if (remotePath.startsWith(HTTP))
			return remotePath.replace(HTTP, HTTP + username + ":"
					+ password + "@");

		return null;
	}
}
