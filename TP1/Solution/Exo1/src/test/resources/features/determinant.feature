Feature: Matrix Determinant Calculation
  As a user
  I want to calculate the determinant of a matrix
  So that I can perform mathematical operations

  Scenario: Calculate determinant of a 3x3 matrix
    Given I have a 3x3 matrix with the following values:
      | 6 | 1 | 1  |
      | 4 | -2 | 5 |
      | 2 | 8 | 7  |
    When I calculate the determinant
    Then the determinant should be -306.0
