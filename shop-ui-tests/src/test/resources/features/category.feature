Feature: Login

  Scenario Outline: Successful Add category
    Given I open browser
    When I navigate to login.html page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    And I open category page
    And I click on add category button
    And I provide category name as "<name>"
    And I click on submit button
    Then I see new category "<name>"

    Examples:
      | username | password | name |
      | admin | admin | admin |



