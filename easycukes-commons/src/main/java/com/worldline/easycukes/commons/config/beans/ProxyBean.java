package com.worldline.easycukes.commons.config.beans;

/**
 * Simple POJO allowing to store the proxy definition in the configuration
 *
 * @author aneveux
 * @version 1.0
 */
public class ProxyBean {

    /**
     * Proxy host
     */
    public String host;

    /**
     * Proxy port
     */
    public int port;

    /**
     * Defines if the proxy should be activated or not
     */
    public boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
