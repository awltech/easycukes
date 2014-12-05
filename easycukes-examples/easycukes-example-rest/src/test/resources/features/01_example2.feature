Feature: Example 2
 
  @conf
  Scenario: Setting the base url
    Given the base url is "https://kenai.com"
  
  @lookup
  Scenario: Lookup value
    When I call the "/home/live_lookup?val=cucumber" resource
    Then the http response code is 200
    And the response property "[0].name" equals "cucumber (1) "