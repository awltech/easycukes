package com.worldline.easycukes.commons.config.beans;

import com.worldline.easycukes.commons.config.InjectableConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Simple POJO mapping with the EasyCukes default configuration file
 *
 * @author aneveux
 * @version 1.0
 */
public class CommonConfigurationBean extends InjectableConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CommonConfigurationBean.class);

    /**
     * Proxy definition
     */
    public ProxyBean proxy;

    /**
     * SSL parameters definition
     */
    public SSLBean ssl;

    /**
     * Map of variables to be used freely
     */
    public Map<String, String> variables;

    /**
     * Main credentials to be used for testing
     */
    public CredentialsBean credentials;

    public ProxyBean getProxy() {
        return proxy;
    }

    public void setProxy(ProxyBean proxy) {
        this.proxy = proxy;
    }

    public SSLBean getSsl() {
        return ssl;
    }

    public void setSsl(SSLBean ssl) {
        this.ssl = ssl;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public CredentialsBean getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsBean credentials) {
        this.credentials = credentials;
    }

    @Override
    public String inject(String token) {
        String res = null;
        for (Field f : this.getClass().getFields()) {
            if (res == null && InjectableConfiguration.class.isAssignableFrom(f.getType())) {
                try {
                    res = ((InjectableConfiguration) f.get(this)).inject(token);
                } catch (IllegalAccessException iae) {
                    LOG.warn("Error while accessing Configuration field value...", iae);
                }
            }
        }
        return res;
    }
}
