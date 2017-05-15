Feature: Validate the price of Vehicle
	@integration
		Scenario: Price validation
			Given I have the value "500" for a price
			When I validate the price of the Vehicle
			Then I receive the error message