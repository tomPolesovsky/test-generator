/*
 * VehicleCreate.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.generator.fixture;

import domainapp.dom.vehicle.Vehicle;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(
		nature = NatureOfService.DOMAIN
)
public class VehicleCreate {

	public static int index = 0;

	public Object[][] getParams() {
		return new Object[][]{
			{"Agfasdgk 1120", 1200},
			{"Afapsfk45s asg4ghr", 1400},
			{"dogjds sg4ghr", 2000},
			{"gfiosdg jisdhg ", 2050},
			{"gfdgiosdg jisdgsdhg", 2080},
			{"BMW M3", 2090}
		};
	}
 
	public Vehicle execute() {
		index++;
		
		Object[][] params = this.getParams();
		
		return new Vehicle((String)params[index][0], (Integer)params[index][1]);
	}

}
