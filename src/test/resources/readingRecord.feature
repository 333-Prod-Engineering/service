Feature: all reading records are displayed on /readingrecords
    @E2E
    Scenario: client makes call to GET /readingrecords
        When the client calls /readingrecords
        Then the client receives a status code of 200
        And the response contains all reading records