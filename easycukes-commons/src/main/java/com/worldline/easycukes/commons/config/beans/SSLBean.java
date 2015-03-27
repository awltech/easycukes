package com.worldline.easycukes.commons.config.beans;

/**
 * Simple POJO allowing to store the SSL parameters definition
 *
 * @author aneveux
 * @version 1.0
 */
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

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getKeystore_password() {
        return keystore_password;
    }

    public void setKeystore_password(String keystore_password) {
        this.keystore_password = keystore_password;
    }

    public String getTruststore() {
        return truststore;
    }

    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    public String getTruststore_password() {
        return truststore_password;
    }

    public void setTruststore_password(String truststore_password) {
        this.truststore_password = truststore_password;
    }
}
