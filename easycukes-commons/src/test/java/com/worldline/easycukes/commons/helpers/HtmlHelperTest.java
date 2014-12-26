package com.worldline.easycukes.commons.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>HtmlHelperTest</code> contains tests for the class
 * <code>{@link HtmlHelper}</code>.
 * 
 * @author m.echikhi
 */
public class HtmlHelperTest {

	/**
	 * Run the String extractInputValueFromHtmlContent(String,String) method
	 * test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testExtractInputValueFromHtmlContent() throws Exception {
		String input = "login";
		String htmlContent = "<html lang=\"en\">"
				+ "<head>"
				+ "    <title>Sign in · GitHub</title>"
				+ "</head>"
				+ "	<body class=\"logged_out  env-production windows\">"
				+ "		<div class=\"wrapper\">"
				+ "			<div id=\"site-container\" class=\"context-loader-container\" data-pjax-container=\"\">"
				+ "				<div class=\"auth-form\" id=\"login\">"
				+ "					<form accept-charset=\"UTF-8\" action=\"/session\" method=\"post\">"
				+ "						<div style=\"margin:0;padding:0;display:inline\"><input name=\"utf8\" value=\"✓\" type=\"hidden\"><input name=\"authenticity_token\" value=\"4lt8EM+NZyx1ZdAH9fPS/He5sBzMvxDJ0KdVcd7u3BZwugd0+LE+BGtclT52fGP/oRYF47jdJhhTWMhqi8R4AA==\" type=\"hidden\"></div>"
				+ "						<div class=\"auth-form-header\">"
				+ "						  <h1>Sign in</h1>"
				+ "						</div>"
				+ "						<div class=\"auth-form-body\">"
				+ "						  <label for=\"login_field\">"
				+ "							 Username or Email"
				+ "						  </label>"
				+ "						  <input autocapitalize=\"off\" autocorrect=\"off\" autofocus=\"autofocus\" class=\"input-block\" id=\"login_field\" name=\"login\" tabindex=\"1\" type=\"text\" value=\"easycukesuser\">"
				+ "						  <label for=\"password\">"
				+ "							 Password <a href=\"/password_reset\">(forgot password)</a>"
				+ "						  </label>"
				+ "						  <input class=\"input-block\" id=\"password\" name=\"password\" tabindex=\"2\" type=\"password\">"
				+ "						  <input class=\"button\" data-disable-with=\"Signing in…\" name=\"commit\" tabindex=\"3\" value=\"Sign in\" type=\"submit\">"
				+ "						</div>" + "					</form>" + "				</div>" + "			</div>"
				+ "		</div>" + "	</body>" + "</html>";

		String result = HtmlHelper.extractInputValueFromHtmlContent(input,
				htmlContent);

		assertEquals(result, "easycukesuser");
	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 */
	@After
	public void tearDown() throws Exception {
	}
}