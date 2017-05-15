Feature: Validate the price of the Vehicle
	@integration
		Scenario: Validate the price and receive error message
			Given I have the value "-500" for a "price"
			When I validate the "price" of the Vehicle
			Then I receive the error message
