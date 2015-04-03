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
package com.worldline.easycukes.scm.stepdefs;

import com.worldline.easycukes.commons.Constants;
import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.config.EasyCukesConfiguration;
import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import com.worldline.easycukes.scm.utils.GitHelper;
import com.worldline.easycukes.scm.utils.MercurialHelper;
import com.worldline.easycukes.scm.utils.SvnHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

/**
 * SCMStepdefs are all the step definitions you can use in your cucumber
 * features in order to interact with SCMs. It allows you to retrieve Git, Hg,
 * and SVN repositories, and to perform various actions with them.
 *
 * @author aneveux
 * @version 1.0
 */
@Slf4j
public class SCMStepdefs {

    protected static final EasyCukesConfiguration<CommonConfigurationBean> configuration = new EasyCukesConfiguration<>(CommonConfigurationBean.class);

    /**
     * Allows to clone a remote Git repository into the specified location
     *
     * @param url    the URL of the Git repository to be cloned
     * @param target the location in which the repository should be cloned
     * @throws Exception if anything's going wrong...
     */
    @Given("^the git repository located in \"([^\"]*)\" is cloned into \"([^\"]*)\"$")
    public void cloneAGitRepository(String url, String target) throws Exception {
        final String baseUrl = ExecutionContext.get(Constants.BASEURL);
        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }
        final String fullPath = baseUrl + DataInjector.injectData(url);
        // then clone
        GitHelper.clone(fullPath, username, password,
                DataInjector.injectData(target));
    }

    /**
     * Allows to clone a remote Hg repository into the specified location
     *
     * @param url    the URL of the Hg repository to be cloned
     * @param target the location in which the repository should be cloned
     * @throws Exception if anything's going wrong...
     */
    @Given("^the mercurial repository located in \"([^\"]*)\" is cloned into \"([^\"]*)\"$")
    public void cloneAMercurialRepository(String url, String target)
            throws Exception {
        final String baseUrl = ExecutionContext.get(Constants.BASEURL);
        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }
        final String fullPath = baseUrl + DataInjector.injectData(url);
        // then clone
        MercurialHelper.clone(fullPath, username, password,
                DataInjector.injectData(target));
    }

    /**
     * Allows to execute a git commit & a git push command in the specified
     * repository using the message provided as a commit message
     *
     * @param repository the location in which the git repository is located
     * @param message    the message to be used as a commit message
     * @throws GitAPIException if something's going wrong while interacting with Git
     *                         commands
     * @throws IOException     if there's something wrong while committing the files
     */
    @Then("^I commit and push the Git repository located in \"([^\"]*)\" with the message \"([^\"]*)\"$")
    public void commitAndPushInAGitRepository(String repository, String message)
            throws GitAPIException, IOException {
        log.info("Commiting the project to Git repository ");
        final File gitworkDir = new File(DataInjector.injectData(repository));

        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }
        GitHelper.commitAndPush(gitworkDir, username, password,
                DataInjector.injectData(message));
        log.info("Commit OK");
    }

    /**
     * Allows to execute a hg commit command & a hg push in the specified
     * repository using the message provided as a commit message
     *
     * @param localRepository  the location in which the hg repository is located
     * @param remoteRepository the URL of the remote hg repository on which we want to push
     *                         the changes
     * @param message          the message to be used for the commit
     * @throws Throwable if something's going wrong
     */
    @Then("^I commit and push the Mercurial repository located in \"([^\"]*)\" to \"([^\"]*)\" with the message \"([^\"]*)\"$")
    public void commitAndPushInAMercurialRepository(String localRepository,
                                                    String remoteRepository, String message) throws Throwable {
        log.info("Commiting the project to Mercurial repository ");

        final String baseUrl = ExecutionContext.get(Constants.BASEURL);
        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }

        final String fullPath = baseUrl
                + DataInjector.injectData(remoteRepository);
        final File repoLoc = new File(DataInjector.injectData(localRepository));

        // add, commit and push
        MercurialHelper.commitAndPush(fullPath, username, password, repoLoc,
                DataInjector.injectData(message));
        log.info("Commit OK");
    }

    /**
     * Allows to checkout a SVN repository into the specified location
     *
     * @param url    the URL of the SVN repository to be checked out
     * @param target the location in which the repository should be checked out
     * @throws Throwable if something's going wrong...
     */
    @Given("^the svn repository located in \"([^\"]*)\" is checked out into \"([^\"]*)\"$")
    public void checkoutAnSVNRepository(String url, String target)
            throws Throwable {
        log.info("Checkouting from " + url + " to " + target);

        final String baseUrl = ExecutionContext.get(Constants.BASEURL);
        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }
        final String fullPath = baseUrl + DataInjector.injectData(url);
        // then checkout
        SvnHelper.checkout(fullPath, username, password,
                DataInjector.injectData(target));
        log.info("Checkout done!");
    }

    /**
     * Allows to execute a svn commit command from a specified location using
     * the provided message as a commit message
     *
     * @param repository the location in which the local SVN repository can be found
     * @param message    the message to be used as a commit message
     * @throws Throwable if something's going wrong
     */
    @Then("^I commit the SVN repository located in \"([^\"]*)\" with the message \"([^\"]*)\"$")
    public void commitInAnSVNRepository(String repository, String message)
            throws Throwable {
        log.info("Commiting the project to Svn repository ");
        final File svnWorkDir = new File(DataInjector.injectData(repository));

        // add and commit
        String username = "";
        String password = "";
        if (configuration.getValues().credentials != null) {
            username = configuration.getValues().credentials.getLogin() != null ? configuration.getValues().credentials.getLogin() : "";
            password = configuration.getValues().credentials.getPassword() != null ? configuration.getValues().credentials.getPassword() : "";
        }
        SvnHelper.commit(svnWorkDir, username, password,
                DataInjector.injectData(message));
        log.info("Commit OK");
    }

}
