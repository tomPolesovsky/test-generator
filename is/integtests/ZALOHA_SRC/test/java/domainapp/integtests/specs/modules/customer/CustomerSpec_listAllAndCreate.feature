Feature: List and Create new Customer Objects
	@integration
		Scenario: Existing Customer objects can be listed and new ones created
			Given I have 0 Customer objects
			When I create a Customer object with v1jwIirs "vFd0r" and vOjNSHyM "vtzfo" and vgAoYRdi "vdWqV"
			When I create a Customer object with v1jwIirs "vnH3t" and vOjNSHyM "vcygy" and vgAoYRdi "v0MpX"
			Then I have 2 Customer objects
