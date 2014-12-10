Feature: belly

  @eat
  Scenario: Access to join url and enter informations to create account
    Given navigate to path "/faces/belly.xhtml"
    And send a text "10" to element having id "bellyForm:mouth"
    When click element having id "bellyForm:eatCukes"
    Then the element having xpath "//ul[1]/li[1]" should contain "The belly ate 10 cukes!"
    
