Feature: List and Create new Customer Objects
	@integration
		Scenario: Existing Customer objects can be listed and new ones created
			Given I have 0 Customer objects
			When I create a Customer object with v1yNviAA "v6ULk" and vmlQ8TkI "v4OYd" and vbleDKPF "vzeHp"
			When I create a Customer object with v1yNviAA "vL8U2" and vmlQ8TkI "v2f6y" and vbleDKPF "v8H2B"
			Then I have 2 Customer objects
