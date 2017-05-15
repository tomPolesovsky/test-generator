Feature: Set the title of the Vehicle
	@integration
		Scenario: Set the title and receive string
			Given I have the object Vehicle
			When I set the title of the Vehicle
			Then I receive the string title
