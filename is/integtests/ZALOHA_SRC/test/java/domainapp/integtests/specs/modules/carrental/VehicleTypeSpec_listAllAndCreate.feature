Feature: List and Create new VehicleType Objects
	@integration
		Scenario: Existing VehicleType objects can be listed and new ones created
			Given I have 0 VehicleType objects
			#@changed
			When I create a VehicleType object
			When I create a VehicleType object
			Then I have 2 VehicleType objects
