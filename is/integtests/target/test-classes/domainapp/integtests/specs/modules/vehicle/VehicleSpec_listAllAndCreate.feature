Feature: List and Create new Vehicle Objects
	@integration
		Scenario: Existing Vehicle objects can be listed and new ones created
			Given I have 0 Vehicle objects
			When I create a Vehicle object with type "Škoda 120" and price "10000"
			When I create a Vehicle object with type "Škoda Fabia" and price "50000"
			Then I have 2 Vehicle objects
