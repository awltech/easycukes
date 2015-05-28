package com.worldline.easycukes.commons.helpers;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class <code>DomHelperTest</code> contains tests for the class
 * <code>{@link DomHelper}</code>.
 *
 * @author m.echikhi
 */
public class DomHelperTest {

    DomHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new DomHelper(new File(getClass().getResource("/project-template-pom.xml").toURI()));
    }

    /**
     * Run the String getElementContent(String,String) method test.
     *
     * @throws Exception
     */
    @Test
    public void testGetElementContent() throws Exception {
        String result = helper.getElementContent("artifactId");
        assertThat(helper.getElementContent("artifactId")).isEqualTo("tests.project-template");
    }

    /**
     * Run the void setElementContent(String,String,String) method test.
     *
     * @throws Exception
     */
    @Test
    public void testSetElementContent() throws Exception {
        String name = "name";
        String value = "Project Example - To be used for testing / Demo";
        helper.setElementContent(name, value);
        assertThat(helper.getElementContent(name)).isEqualTo(value);
    }

}