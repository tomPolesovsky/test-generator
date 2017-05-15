/*
 * RentRepository.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.carrental;

import domainapp.dom.vehicle.Vehicle;
import domainapp.dom.customer.Customer;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.LocalDate;

@DomainService(
		nature = NatureOfService.DOMAIN,
		repositoryFor = Rent.class
)
public class RentRepository {

	public List<Rent> listAll() {
		return repositoryService.allInstances(Rent.class);
	}

	public Rent create(final Customer customer, final Vehicle vehicle, final LocalDate from, final LocalDate to, final String note) {
		final Rent rent = new Rent(customer, vehicle, from, to);
		rent.setNote(note);

		serviceRegistry.injectServicesInto(rent);
		repositoryService.persist(rent);
		return rent;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
