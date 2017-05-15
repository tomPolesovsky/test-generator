/*
 * CustomerRepository.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.customer;

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
        repositoryFor = Customer.class
)
public class CustomerRepository {

    public List<Customer> listAll() {
        return repositoryService.allInstances(Customer.class);
    }

    public Customer create(final String name, final String surname, final String email)
	{
        final Customer customer = new Customer(name, surname, email);
		
        serviceRegistry.injectServicesInto(customer);
        repositoryService.persist(customer);
        return customer;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
	
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}