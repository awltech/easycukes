package com.worldline.easycukes.dbunit.config.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple POJO containing information about DBUnit configuration
 *
 * @author aneveux
 * @version 1.0
 */
@Data
@NoArgsConstructor
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

}
