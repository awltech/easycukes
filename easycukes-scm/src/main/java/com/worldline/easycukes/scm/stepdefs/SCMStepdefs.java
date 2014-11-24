package com.worldline.easycukes.scm.stepdefs;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.context.DataInjector;
import com.worldline.easycukes.commons.context.ExecutionContext;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.scm.utils.GitHelper;
import com.worldline.easycukes.scm.utils.MercurialHelper;
import com.worldline.easycukes.scm.utils.SvnHelper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * This class aims at containing all the operations that should be needed during
 * the tests in order to deal with SCMs operations (no matter which SCM is
 * impacted)
 *
 * @author aneveux
 * @version 1.0
 */
public class SCMStepdefs {

	/**
	 * A logger...
	 */
	private static final Logger LOGGER = Logger.getLogger(SCMStepdefs.class);

	/**
	 * Allows to clone a remote Git repository into the specified location
	 *
	 * @param url
	 *            the URL of the Git repository to be cloned
	 * @param target
	 *            the location in which the repository should be cloned
	 * @throws Exception
	 *             if anything's going wrong...
	 */
	@Given("^the git repository located in \"([^\"]*)\" is cloned into \"([^\"]*)\"$")
	public void the_git_repository_located_in_is_cloned_into(String url,
			String target) throws Exception {
		final String baseUrl = ExecutionContext.get(Constants.BASE_URL_KEY);
		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);

		final String fullPath = baseUrl + DataInjector.injectData(url);
		// then clone
		GitHelper.clone(fullPath, username, password,
				DataInjector.injectData(target));
	}

	/**
	 * Allows to clone a remote Hg repository into the specified location
	 *
	 * @param url
	 *            the URL of the Hg repository to be cloned
	 * @param target
	 *            the location in which the repository should be cloned
	 * @throws Exception
	 *             if anything's going wrong...
	 */
	@Given("^the mercurial repository located in \"([^\"]*)\" is cloned into \"([^\"]*)\"$")
	public void the_mercurial_repository_located_in_is_cloned_into(String url,
			String target) throws Exception {
		final String baseUrl = ExecutionContext.get(Constants.BASE_URL_KEY);
		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);

		final String fullPath = baseUrl + DataInjector.injectData(url);
		// then clone
		MercurialHelper.clone(fullPath, username, password,
				DataInjector.injectData(target));
	}

	/**
	 * Allows to execute a git commit & a git push command in the specified
	 * repository using the message provided as a commit message
	 *
	 * @param repository
	 *            the location in which the git repository is located
	 * @param message
	 *            the message to be used as a commit message
	 * @throws GitAPIException
	 *             if something's going wrong while interacting with Git
	 *             commands
	 * @throws IOException
	 *             if there's something wrong while committing the files
	 */
	@Then("^I commit and push the Git repository located in \"([^\"]*)\" with the message \"([^\"]*)\"$")
	public void I_commit_and_push_the_git_repository_located_in_with_the_message(
			String repository, String message) throws GitAPIException,
			IOException {
		LOGGER.info("Commiting the project to Git repository ");
		final File gitworkDir = new File(DataInjector.injectData(repository));

		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);

		GitHelper.commitAndPush(gitworkDir, username, password,
				DataInjector.injectData(message));
		LOGGER.info("Commit OK");
	}

	/**
	 * Allows to execute a hg commit command & a hg push in the specified
	 * repository using the message provided as a commit message
	 *
	 * @param localRepository
	 *            the location in which the hg repository is located
	 * @param remoteRepository
	 *            the URL of the remote hg repository on which we want to push
	 *            the changes
	 * @param message
	 *            the message to be used for the commit
	 * @throws Throwable
	 *             if something's going wrong
	 */
	@Then("^I commit and push the Mercurial repository located in \"([^\"]*)\" to \"([^\"]*)\" with the message \"([^\"]*)\"$")
	public void I_commit_and_push_the_mercurial_repository_located_in_to_with_the_message(
			String localRepository, String remoteRepository, String message)
					throws Throwable {
		LOGGER.info("Commiting the project to Mercurial repository ");

		final String baseUrl = ExecutionContext.get(Constants.BASE_URL_KEY);
		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);

		final String fullPath = baseUrl
				+ DataInjector.injectData(remoteRepository);
		final File repoLoc = new File(DataInjector.injectData(localRepository));

		// add, commit and push
		MercurialHelper.commitAndPush(fullPath, username, password, repoLoc,
				DataInjector.injectData(message));
		LOGGER.info("Commit OK");
	}

	/**
	 * Allows to checkout a SVN repository into the specified location
	 *
	 * @param url
	 *            the URL of the SVN repository to be checked out
	 * @param target
	 *            the location in which the repository should be checked out
	 * @throws Throwable
	 *             if something's going wrong...
	 */
	@Given("^the svn repository located in \"([^\"]*)\" is checked out into \"([^\"]*)\"$")
	public void the_svn_repository_located_in_is_checked_out_into(String url,
			String target) throws Throwable {
		LOGGER.info("Checkouting from " + url + " to " + target);

		final String baseUrl = ExecutionContext.get(Constants.BASE_URL_KEY);
		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);

		final String fullPath = baseUrl + DataInjector.injectData(url);
		// then checkout
		SvnHelper.checkout(fullPath, username, password,
				DataInjector.injectData(target));
		LOGGER.info("Checkout done!");
	}

	/**
	 * Allows to execute a svn commit command from a specified location using
	 * the provided message as a commit message
	 *
	 * @param repository
	 *            the location in which the local SVN repository can be found
	 * @param message
	 *            the message to be used as a commit message
	 * @throws Throwable
	 *             if something's going wrong
	 */
	@Then("^I commit the SVN repository located in \"([^\"]*)\" with the message \"([^\"]*)\"$")
	public void I_commit_the_svn_repository_located_in_with_the_message(
			String repository, String message) throws Throwable {
		LOGGER.info("Commiting the project to Svn repository ");
		final File svnWorkDir = new File(DataInjector.injectData(repository));

		// add and commit
		final String username = Configuration.get(Constants.USERNAME_KEY);
		final String password = Configuration.get(Constants.PASSWORD_KEY);
		SvnHelper.commit(svnWorkDir, username, password,
				DataInjector.injectData(message));
		LOGGER.info("Commit OK");
	}

}
