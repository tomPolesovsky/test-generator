Feature: List and Create new VehicleType Objects
	@integration
		Scenario: Existing VehicleType objects can be listed and new ones created
			Given I have 0 VehicleType objects
			When I create a VehicleType object with vL7r1rtP "v0qYr" and vr1hujUl "v1M6S"
			When I create a VehicleType object with vL7r1rtP "vnS5K" and vr1hujUl "vCKvH"
			Then I have 2 VehicleType objects
