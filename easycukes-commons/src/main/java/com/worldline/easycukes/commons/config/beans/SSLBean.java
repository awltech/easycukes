package com.worldline.easycukes.commons.config.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple POJO allowing to store the SSL parameters definition
 *
 * @author aneveux
 * @version 1.0
 */
@NoArgsConstructor
@Data
public class SSLBean {

    /**
     * Reference to the keystore to be used
     */
    public String keystore;

    /**
     * Password related to the keystore
     */
    public String keystore_password;

    /**
     * Reference to the truststore to be used
     */
    public String truststore;

    /**
     * Password related to the truststore
     */
    public String truststore_password;

}
