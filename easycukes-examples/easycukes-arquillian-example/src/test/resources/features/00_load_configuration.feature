Feature: load configuration
  load configutaion properties
  Setting base url

  @conf
  Scenario: Load of the Cucumber configuration
    Given I load the environment configuration file "/env"
    And I load the application configuration file "/target"

  @conf
  Scenario: Configuration setting
    Given the base url is "$__baseurl__$"
 
