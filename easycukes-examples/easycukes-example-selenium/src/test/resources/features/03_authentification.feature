Feature: authenticate in github
  In order to access to created account in guthub

  @auth
  Scenario: authentification
    #Click in link "Sign"
    Given navigate to path "/login"
    #Checks if the login page is loaded
    Then the element having id "login_field" should be present
    #Retrieves the username from properties file (target.properties) and put it in the "username"input fields
    And send a text "$__username__$" to element having id "login_field"
    #Retrieves the password from properties file (target.properties) and put it in the "password"input fields
    And send a text "$__password__$" to element having id "password"
    #Click to button "Sign" connect
    And submit element having name "commit"

  @auth
  Scenario: Validation of the authentication thanks to the presence of menu items
    #Click in "setting" item
    When click element having id "account_settings"
    #Waiting for 10 seconds
    And wait for 10 seconds
    #Click in "Emails" item
    And click element having xpath "//div[@class='column one-fourth']/nav/a[@href='/settings/emails']"
    #Checks that email users appears in Emails page
    Then the element having xpath "//ul[@id='settings-emails']/li" should contain "$__email__$"
