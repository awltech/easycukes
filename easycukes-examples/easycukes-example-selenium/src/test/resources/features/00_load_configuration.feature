Feature: load configuration
  load configutaion properties
  Setting base url

  @conf
  Scenario: Configuration setting
    #Get the base url from target.properties and save
    Given the base url is "$__baseurl__$"
