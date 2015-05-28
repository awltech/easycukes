package com.worldline.easycukes.dbunit.config.beans;

import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Extension of CommonConfigurationBean allowing to store some DBUnit configuration
 *
 * @author aneveux
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class DBUnitConfigurationBean extends CommonConfigurationBean {

    /**
     * Addition to default configuration file in order to get information about DBUnit
     */
    public DBUnitBean dbunit;

}
