Feature: Add product to cart on Amazon
  As a customer
  I want to search for a product and add it to my cart
  So that I can purchase it later

  Scenario: Successfully add a book to the cart
    Given I am on the Amazon homepage
    When I select "Books" category from the search dropdown
    And I search for "python in easy steps"
    And I click on the first search suggestion
    And I click on the "Python in easy steps" book from results
    And I select quantity "2" from the dropdown
    And I click the "Add to Cart" button
    Then I should see "Added to cart" confirmation message
    When I go to the cart
    Then I should see "Python in easy steps" in the cart
    And the quantity should be "2"
    And the price should be displayed
