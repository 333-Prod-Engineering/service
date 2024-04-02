Feature: author list is displayed on get authors endpoint
  @E2E
  Scenario: client makes call to GET /authors
    When the client calls /authors
    Then the client receives status code of 200
    And the client receives a response in JSON format with author "Marin Preda"
