package com.worldline.easycukes.commons.config.beans;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Test cases for Credentials class
 *
 * @author aneveux
 * @version 1.0
 */
public class CredentialsBeanTest {

    /**
     * Validates that injection from an empty object is null
     */
    @Test
    public void testInjectNull() {
        CredentialsBean c = new CredentialsBean();
        assertThat(c.inject("login")).isNullOrEmpty();
        assertThat(c.inject("password")).isNullOrEmpty();
        assertThat(c.inject("")).isNull();
    }

    /**
     * Validates that injection from an existing object returns data
     */
    @Test
    public void testInjectValues() {
        CredentialsBean c = new CredentialsBean();
        c.login = "Chuck";
        c.password = "Norris";
        assertThat(c.inject("login")).isEqualTo("Chuck");
        assertThat(c.inject("password")).isEqualTo("Norris");
        assertThat(c.inject("")).isNull();
    }


}
