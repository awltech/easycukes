package com.worldline.easycukes.dbunit.config.beans;

/**
 * Simple POJO containing information about DBUnit configuration
 *
 * @author aneveux
 * @version 1.0
 */
public class DBUnitBean {

    /**
     * Connection URL for the database
     */
    public String connection_url;

    /**
     * Driver class to use (depends on which database engine is used)
     */
    public String driver_class;

    /**
     * Username for the database connection
     */
    public String username;

    /**
     * Password for the database connection
     */
    public String password;

    public String getConnection_url() {
        return connection_url;
    }

    public void setConnection_url(String connection_url) {
        this.connection_url = connection_url;
    }

    public String getDriver_class() {
        return driver_class;
    }

    public void setDriver_class(String driver_class) {
        this.driver_class = driver_class;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
