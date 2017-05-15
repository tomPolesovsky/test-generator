/*
 * CustomerCreate.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.generator.fixture;

import domainapp.dom.customer.Customer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CustomerCreate
{

	public static int index = 0;

	private String[][] params = {
		{"Pavel", "Novák", "pavel.novak@seznam.cz"},
		{"Josef", "Dobrovsky", "josef.dobrovsky@seznam.cz"},
		{"Jan", "Palacky", "jan.palacky@seznam.cz"}
	};

	public Customer execute() {
		index++;
		
		return new Customer(this.params[index][0], this.params[index][1], this.params[index][2]);
	}

}
