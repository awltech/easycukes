Feature: delete created account in github
  In order to delete created account in github.com

  @delete
  Scenario: accept and confirm deletion of account
    #Navigates to url "https://github.com/settings/admin"
    Given navigate to path "/settings/admin"
    #Click in button "Delete your account"    
    Then click element having xpath "//a[@href='#delete_account_confirmation']"
    #Enter username in input "Enter your username:" field     
    And send a text "$__username__$" to element having xpath "//div[@id='facebox']/div/div/form/p[1]/input[@id='sudo_login']"
   	#Enter "delete my account" in input "Type in the following phrase: delete my account" field  
    And send a text "delete my account" to element having xpath "//div[@id='facebox']/div/div/form/p[2]/input[@id='confirmation_phrase']"
    #Waiting 2 seconds to load all elements
    And wait for 2 seconds
    #Click in button "Cancel plan and delete this account"
    And click element having xpath "//div[@id='facebox']/div/div/form/button[@class='button button-block danger']"
    #Checks that the delete account was made successfully by checking the presence of "Sign up for GitHib" button
    And the element having xpath "//div[@id='site-container']/div/div/form/button[@class='button primary button-block']" should be present
    
  @close
  Scenario: Close the browser
    #Close the browser
    Given close the browser
