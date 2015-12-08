Feature: Example 1
    
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
    When I send get to "/sqlrest/CUSTOMER/8"
    Then the http response code is 200
    And the response contains "<FIRSTNAME>Andrew</FIRSTNAME>"
    And the response contains "<LASTNAME>Miller</LASTNAME>"