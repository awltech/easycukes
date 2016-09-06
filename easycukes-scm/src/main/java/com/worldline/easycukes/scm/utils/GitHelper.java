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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.DeleteTagCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * {@link GitHelper} allows to ease the manipulation of git repositories. It
 * uses internally the JGit API, and allows to perform all basic operations
 * linked to a Git repository (clone, commit, etc.)
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class GitHelper {
	private static final String PUSH_MESSAGE = "Pushed the changes in remote Git repository...";
	
	private static final String COMMIT_MESSAGE = "Commited the changes in the Git repository...";
	
    /**
     * Clones the specified repository in the specified directory using the
     * provided credentials for authentication
     *
     * @param url       the URL of the git repository to be cloned
     * @param username  username to be used for cloning the repository
     * @param password  password matching with the provided username to be used for
     *                  authentication
     * @param directory the path in which the git repository should be cloned
     * @throws GitAPIException if anything's going wrong while cloning the repository
     */
    public static void clone(@NonNull String url, String username, String password,
                             String directory) throws GitAPIException {
        log.info("Cloning from " + url + " to " + directory);
        try {
            final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
                    username, password);
            Git.cloneRepository().setCredentialsProvider(userCredential)
                    .setURI(url).setDirectory(new File(directory)).call();
            log.info("Repository sucessfully cloned");
        } catch (final InvalidRemoteException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (final TransportException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (final GitAPIException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Adds all the files of the specified directory in the local git repository
     * (git add .), then commits the changes (git commit .), and finally pushes
     * the changes on the remote repository (git push)
     *
     * @param directory the directory in which the local git repository is located
     * @param username  the username to be used while pushing
     * @param password  the password matching with the provided username to be used
     *                  for authentication
     * @param message   the commit message to be used
     * @throws GitAPIException if something's going wrong while interacting with Git
     * @throws IOException     if something's going wrong while manipulating the local
     *                         repository
     */
    public static void commitAndPush(@NonNull File directory, String username,
                                     String password, String message) throws GitAPIException,
            IOException {
        try {
            final Git git = Git.open(directory);
            // run the add
            final AddCommand addCommand = git.add();
            for (final String filePath : directory.list())
                if (!".git".equals(filePath))
                    addCommand.addFilepattern(filePath);
            addCommand.call();
            log.info("Added content of the directory" + directory
                    + " in the Git repository located in "
                    + directory.toString());
            // and then commit
            final PersonIdent author = new PersonIdent(username, "");
            git.commit().setCommitter(author).setMessage(message)
                    .setAuthor(author).call();
            log.info(COMMIT_MESSAGE);
            // and finally push
            final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
                    username, password);
            git.push().setCredentialsProvider(userCredential).call();
            log.info(PUSH_MESSAGE);
        } catch (final GitAPIException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
    /**
	 * Create a new branch in the local git repository
	 * (git checkout -b branchname) and finally pushes new branch on the remote repository (git push)
	 *
	 * @param directory the directory in which the local git repository is located
	 * @param username  the username to be used while pushing
	 * @param password  the password matching with the provided username to be used
	 *                  for authentication
	 * @param message   the commit message to be used	 
	 */
	public static void createBranch(@NonNull File directory, String branchName, String username,
									String password, String message) throws GitAPIException {

		try {
			final Git git = Git.open(directory);

			final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
					username, password);

			CreateBranchCommand branchCommand = git.branchCreate();
			branchCommand.setName(branchName);
			branchCommand.call();
			
			// and then commit
			final PersonIdent author = new PersonIdent(username, "");
			git.commit().setCommitter(author).setMessage(message)
					.setAuthor(author).call();
			log.info(message);

			git.push().setCredentialsProvider(userCredential).call();
			log.info(PUSH_MESSAGE);
		} catch (final GitAPIException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}


	/**
	 * Delete a branch in the local git repository
	 * (git branch -d branchname) and finally pushes new branch on the remote repository (git push)
	 *
	 * @param directory the directory in which the local git repository is located
	 * @param username  the username to be used while pushing
	 * @param password  the password matching with the provided username to be used
	 *                  for authentication
	 * @param message   the commit message to be used	 
	 */
	public static void deleteBranch(@NonNull File directory, String branchName, String username,
									String password, String message) {

		try {
			final Git git = Git.open(directory);

			final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
					username, password);

			DeleteBranchCommand deleteBranchCommand = git.branchDelete();

			deleteBranchCommand.setBranchNames(branchName);
			deleteBranchCommand.call();
			log.info("Develop branch deleted");

			// and then commit
			final PersonIdent author = new PersonIdent(username, "");
			git.commit().setCommitter(author).setMessage(message)
					.setAuthor(author).call();
			log.info(message);

			git.push().setCredentialsProvider(userCredential).call();
			log.info(PUSH_MESSAGE);

		} catch (final GitAPIException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}


	/**
	 * Create a new tag in the local git repository
	 * (git checkout tagname) and finally pushes new branch on the remote repository (git push)
	 *
	 * @param directory the directory in which the local git repository is located
	 * @param username  the username to be used while pushing
	 * @param password  the password matching with the provided username to be used
	 *                  for authentication
	 * @param message   the commit message to be used	 
	 */
	public static void createTag(@NonNull File directory, String tagName, String username,
								 String password, String message) {

		try {
			final Git git = Git.open(directory);

			final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
					username, password);

			TagCommand tagCommand = git.tag();
			tagCommand.setName(tagName);
			tagCommand.setMessage(message);
			tagCommand.call();
			log.info("Tag created");

			// and then commit
			final PersonIdent author = new PersonIdent(username, "");
			git.commit().setCommitter(author).setMessage(message)
					.setAuthor(author).call();
			log.info(message);

			git.push().setCredentialsProvider(userCredential).call();
			log.info(PUSH_MESSAGE);
		} catch (final GitAPIException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}


	/**
	 * Delete a tag in the local git repository
	 * (git tag -d tagname) and finally pushes new branch on the remote repository (git push)
	 *
	 * @param directory the directory in which the local git repository is located
	 * @param username  the username to be used while pushing
	 * @param password  the password matching with the provided username to be used
	 *                  for authentication
	 * @param message   the commit message to be used	 
	 */
	public static void deleteTag(@NonNull File directory, String tagName, String username, String password, String message) {
		try {			
			final Git git = Git.open(directory);

			final UsernamePasswordCredentialsProvider userCredential = new UsernamePasswordCredentialsProvider(
					username, password);
			DeleteTagCommand deleteTagCommand = git.tagDelete();
			deleteTagCommand.setTags(tagName);
			deleteTagCommand.call();
			log.info("Tag deleted");

			// and then commit
			final PersonIdent author = new PersonIdent(username, "");
			git.commit().setCommitter(author).setMessage(message).setAuthor(author).call();
			log.info(message);

			git.push().setCredentialsProvider(userCredential).call();
			log.info(PUSH_MESSAGE);
		} catch (final GitAPIException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}