package com.worldline.easycukes.commons.config.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple POJO allowing to store the proxy definition in the configuration
 *
 * @author aneveux
 * @version 1.0
 */
@NoArgsConstructor
@Data
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
    
    /**
     * Proxy bypass
     */
    public String byPassHost;

}
