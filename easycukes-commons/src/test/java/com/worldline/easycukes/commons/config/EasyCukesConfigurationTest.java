package com.worldline.easycukes.commons.config;

import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Test cases for EasyCukesConfiguration
 *
 * @author aneveux
 * @version 1.0
 */
public class EasyCukesConfigurationTest {

    /**
     * Test object to be used
     */
    static EasyCukesConfiguration<CommonConfigurationBean> configuration;

    /**
     * Simple initialization
     */
    @BeforeClass
    public static void setUp() {
        configuration = new EasyCukesConfiguration<>(CommonConfigurationBean.class);
    }

    /**
     * Validates initialization of the configuration object
     */
    @Test
    public void testInitialization() {
        assertThat(configuration).isNotNull();
    }

    /**
     * Validates the proxy checking
     */
    @Test
    public void testIsProxyNeeded() {
        assertThat(configuration.isProxyNeeded()).isFalse();
    }

    /**
     * Validates the correct file parsing
     */
    @Test
    public void testGetValues() {
        assertThat(configuration.getValues()).isNotNull();
        assertThat(configuration.getValues().credentials).isNotNull();
        assertThat(configuration.getValues().credentials.login).isEqualTo("testLogin");
        assertThat(configuration.getValues().credentials.password).isEqualTo("testPassword");
        assertThat(configuration.getValues().proxy.enabled).isFalse();
        assertThat(configuration.getValues().ssl.keystore).isEqualTo("testKeyStore");
    }

}
