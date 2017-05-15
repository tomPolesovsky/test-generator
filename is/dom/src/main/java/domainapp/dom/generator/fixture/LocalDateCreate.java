/*
 * LocalDateCreate.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.generator.fixture;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.joda.time.LocalDate;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class LocalDateCreate
{

	public static int index = 0;
	
	private String[] params = {
		"2017-12-05",
		"2017-12-08",
		"2017-12-04",
		"2017-10-04",
		"2017-12-02",
		"2017-08-20",
	};

	public LocalDate execute() {
		index++;
		
		return new LocalDate(this.params[index]);
	}

}
