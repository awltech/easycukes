Feature: Example dbunit
    
  @conf
  Scenario: Setting the base url
    Given the base url is "http://localhost:8080"
    And I set the request header "Connection" to "Keep-Alive"
    
  @dbunit
  Scenario: Setting the operations
    Given the operation before executing tests is CLEAN_INSERT
    And the operation after executing tests is NONE
    And fill the table "TITLE" with data:
	  | CODE	| LABEL	|
	  | 1		| mrs	|
	  | 2		| Mr	|
	  | 3		| Ms	|
	  | 4		| Miss	|
	And fill the table "NATIONALITY" with data:
	  |	CODE	|	LABEL		|
	  |	1		|	French		|
	  |	2		|	English		|
	  |	3		|	American	|
  
  Scenario: New person
  	When I post to "/cukes/person/new" the parameters:
  	"""
      {
      "code": "302",
      "name": "James BOND",
      "title": "2",
      "nationality": "3"
      }
      """
    Then the http response code is 204
    
  Scenario: Validate the addition
  	When I send get to "/cukes/person/get?code=302"
  	Then the http response code is 200
  	And the response property "name" equals "James BOND"
  	
  @dbunit
  Scenario: Setting the operations
    Given the operation before executing tests is INSERT
    And the operation after executing tests is DELETE_ALL
    And fill the table "PERSON" with data:
	  | CODE	|	NAME		|	TITLE	|	NATIONALITY	|
	  |	520		|	John Terry	|	2		|	2			|
	  
  Scenario: Consult ALL
  	When I send get to "/cukes/person/list"
  	Then the http response code is 200
  	And the response property "person[code=520].name" equals "John Terry"
    
  	
 