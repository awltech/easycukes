Feature: register in github
  In order to create account in github.com

  @register
  Scenario: Access to join url and enter informations to create account
    #Access to the home page
    Given navigate to path "/"
    #Click in button "Sign up for GitHub" to register
    Then click element having xpath "//div[@id='site-container']/div/div/form/button[@class='button primary button-block']"
    #Checks that we are in registration page
    And the element having id "user_login" should be present
    #Retrieves the username from properties file (target.properties) and put it in the "username" input field
    And send a text "$__username__$" to element having id "user_login"
    #Retrieves the email from properties file (target.properties) and put it in the "email" input field
    And send a text "$__email__$" to element having id "user_email"
    #Retrieves the password from properties file (target.properties) and put it in the "password"/"password confirmation" input fields
    And send a text "$__password__$" to element having id "user_password"
    And send a text "$__password__$" to element having id "user_password_confirmation"
    #Click in button "create an account"
    And submit element having id "signup_button"
    #Waiting 10 seconds to load all elements
    And wait for 10 seconds
    #Click in button "Finich sign up"
    And submit element having xpath "//button[@class='button primary js-choose-plan-submit']"
    #Checks that the regisration was made successfully by checking the contents of the link "username" and cheking the presence of "Logout" link
    Then the element having xpath "//ul[@id='user-links']/li[1]/a/span/span[@class='css-truncate-target']" should contain "$__username__$"
    And the element having xpath "//ul[@id='user-links']/li[5]/form/button[@aria-label='Sign out']" should be present
