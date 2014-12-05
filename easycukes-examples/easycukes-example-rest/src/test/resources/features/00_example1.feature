Feature: Example 1
 
  @conf
  Scenario: Load the configuration files
    #Given I load the default configuration files
    Given I load the environment configuration file "/env"
    And I load the application configuration file "/target"
    
  @conf
  Scenario: Setting the base url
    Given the base url is "http://www.thomas-bayer.com"
    And I set the request header "Connection" to "Keep-Alive"
  
  @resources
  Scenario: List resources
    When I send get to "/sqlrest"
    Then the http response code is 200
    And the response contains "CUSTOMERList"
    
  @customer
  Scenario: List customers
    When I send get to "/sqlrest/CUSTOMER"
    Then the http response code is 200
    And the response contains "<CUSTOMER xlink:href"
    
  @customer
  Scenario: Consult customer
    When I send get to "/sqlrest/CUSTOMER/9"
    Then the http response code is 200
    And the response contains "<FIRSTNAME>James</FIRSTNAME>"
    And the response contains "<LASTNAME>Schneider</LASTNAME>"