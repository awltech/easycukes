package com.worldline.easycukes.dbunit.config.beans;

import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;

/**
 * Extension of CommonConfigurationBean allowing to store some DBUnit configuration
 *
 * @author aneveux
 * @version 1.0
 */
public class DBUnitConfigurationBean extends CommonConfigurationBean {

    public DBUnitBean dbunit;

    public DBUnitBean getDbunit() {
        return dbunit;
    }

    public void setDbunit(DBUnitBean dbunit) {
        this.dbunit = dbunit;
    }
}
