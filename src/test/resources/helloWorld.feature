Feature: greeting is displayed on hello-world
  @E2E
  Scenario: client makes call to GET /hello-world
    When the client calls /hello-world
    Then the client receives status code of 200
    And the client receives response Hello, George!
