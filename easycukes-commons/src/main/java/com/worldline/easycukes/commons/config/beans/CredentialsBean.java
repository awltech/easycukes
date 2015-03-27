package com.worldline.easycukes.commons.config.beans;

import com.worldline.easycukes.commons.config.InjectableConfiguration;

/**
 * * Simple POJO allowing to store the main credentials used in the application to be tested.
 *
 * @author aneveux
 * @version 1.0
 */
public class CredentialsBean extends InjectableConfiguration {

    /**
     * Main login for the application
     */
    public String login;

    /**
     * Main password for the application
     */
    public String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String inject(String token) {
        switch (token) {
            case "login":
                return login;
            case "password":
                return password;
        }
        return null;
    }
}
