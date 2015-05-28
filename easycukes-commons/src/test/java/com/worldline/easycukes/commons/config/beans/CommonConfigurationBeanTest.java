package com.worldline.easycukes.commons.config.beans;

import com.worldline.easycukes.commons.config.InjectableConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Test cases for CommonConfigurationBean
 *
 * @author aneveux
 * @version 1.0
 */
public class CommonConfigurationBeanTest {

    /**
     * Object we'll manipulate for the tests
     */
    CommonConfigurationBean bean;

    /**
     * Simple initiatialization of the test object
     */
    @Before
    public void setUp() {
        bean = new CommonConfigurationBean();
        CredentialsBean c = new CredentialsBean();
        c.login = "Chuck";
        c.password = "Norris";
        ProxyBean p = new ProxyBean();
        p.enabled = true;
        p.host = "myproxy";
        p.port = 1234;
        SSLBean s = new SSLBean();
        s.keystore_password = "kpass";
        s.keystore = "kstore";
        s.truststore = "tstore";
        s.truststore_password = "tpass";
        bean.setSsl(s);
        bean.setProxy(p);
        bean.setCredentials(c);
    }

    /**
     * Allows to test injection on a basic CommonConfigurationBean object
     */
    @Test
    public void testBasicInjection() {
        assertThat(bean.inject("login")).isEqualTo("Chuck");
        assertThat(bean.inject("password")).isEqualTo("Norris");
        assertThat(bean.inject("host")).isNullOrEmpty();
        assertThat(bean.inject("")).isNullOrEmpty();
    }

    /**
     * Tests injection on an extension of CommonConfigurationBean
     */
    @Test
    public void testExtensionInjection() {
        ExtendedConfigurationTest ect = new ExtendedConfigurationTest();
        ect.ssl = bean.ssl;
        ect.proxy = bean.proxy;
        ect.credentials = bean.credentials;
        ect.test = new InjectableFieldTest();
        assertThat(ect.inject("login")).isEqualTo("Chuck");
        assertThat(ect.inject("password")).isEqualTo("Norris");
        assertThat(ect.inject("test")).isEqualTo("OK");
        assertThat(ect.inject("")).isNullOrEmpty();
    }

    /**
     * Simple class to be used for tests purpose
     */
    class InjectableFieldTest extends InjectableConfiguration {

        @Override
        public String inject(String token) {
            if (token.equalsIgnoreCase("test"))
                return "OK";
            else
                return null;
        }
    }

    /**
     * Simple CommonConfigurationBean extension allowing to validate injection
     */
    class ExtendedConfigurationTest extends CommonConfigurationBean {
        public InjectableFieldTest test;
    }
}
