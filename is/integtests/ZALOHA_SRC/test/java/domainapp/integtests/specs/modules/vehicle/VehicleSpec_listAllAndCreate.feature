Feature: List and Create new Vehicle Objects
	@integration
		Scenario: Existing Vehicle objects can be listed and new ones created
			Given I have 0 Vehicle objects
			When I create a Vehicle object with vvOPSWJE "v032S" and vkSDsnGX "5447"
			When I create a Vehicle object with vvOPSWJE "vLRJt" and vkSDsnGX "1671"
			Then I have 2 Vehicle objects
