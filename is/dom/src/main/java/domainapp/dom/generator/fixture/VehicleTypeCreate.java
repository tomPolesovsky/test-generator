/*
 * VehicleTypeCreate.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.generator.fixture;

import domainapp.dom.carrental.VehicleType;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(
		nature = NatureOfService.DOMAIN
)
public class VehicleTypeCreate {

	public static int index = 0;

	private String[][] params = {
		{"Škoda", "120"},
		{"Volkswagen", "Golf"},
		{"Škoda", "Octavia"},
		{"Audi", "A8"},
		{"Škoda", "Fabia"}
	};

	public VehicleType execute() {
		index++;

		return new VehicleType(this.params[index][0], this.params[index][1]);
	}

}
