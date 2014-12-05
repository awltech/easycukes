Feature: logout in github
  In order to logout in github.com

  @logout
  Scenario: logout
    #Click in link "Sign out"
    Given click element having xpath "//ul[@id='user-links']/li[5]/form/button[@aria-label='Sign out']"
    #Checks that the logout was made successfully by checking the contents of the link "username" and cheking the presence of "Sign up for GitHib" button
    Then the element having xpath "//div[@id='site-container']/div/div/form/button[@class='button primary button-block']" should be present
