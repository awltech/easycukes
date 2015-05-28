package com.worldline.easycukes.commons.config.beans;

import com.worldline.easycukes.commons.config.InjectableConfiguration;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Simple POJO mapping with the EasyCukes default configuration file
 *
 * @author aneveux
 * @version 1.0
 */
@Slf4j
@Data
@NoArgsConstructor
public class CommonConfigurationBean extends InjectableConfiguration {

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

    @Override
    public String inject(@NonNull String token) {
        String res = null;
        for (Field f : this.getClass().getFields()) {
            if (res == null && InjectableConfiguration.class.isAssignableFrom(f.getType())) {
                try {
                    res = ((InjectableConfiguration) f.get(this)).inject(token);
                } catch (IllegalAccessException iae) {
                    log.warn("Error while accessing Configuration field value...", iae);
                }
            }
        }
        return res;
    }
}
