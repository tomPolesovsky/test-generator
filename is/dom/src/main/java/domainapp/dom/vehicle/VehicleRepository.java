/*
 * VehicleRepository.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.vehicle;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

/**
 *
 * @author again
 */
@DomainService(
		nature = NatureOfService.DOMAIN,
		repositoryFor = Vehicle.class
)
public class VehicleRepository {

	public List<Vehicle> listAll() {
		return repositoryService.allInstances(Vehicle.class);
	}

	public Vehicle create(final String type, final int price) {
		final Vehicle vehicle = new Vehicle(type, price);

		serviceRegistry.injectServicesInto(vehicle);
		repositoryService.persist(vehicle);
		return vehicle;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
