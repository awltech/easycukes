Feature: load configuration
  load configutaion properties
  Setting base url

  @conf
  Scenario: Load of the Cucumber configuration
    #Get the content of env.properties file
    Given I load the environment configuration file "/env"
    #Get the content of target.properties file
    And I load the application configuration file "/target"

  @conf
  Scenario: Configuration setting
    #Get the base url from target.properties and save
    Given the base url is "$__baseurl__$"
