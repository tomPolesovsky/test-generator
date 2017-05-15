Feature: List and Create new Rent Objects
	@integration
		Scenario: Existing Rent objects can be listed and new ones created
			Given I have 0 Rent objects
			When I create a Rent object
			When I create a Rent object
			Then I have 2 Rent objects
