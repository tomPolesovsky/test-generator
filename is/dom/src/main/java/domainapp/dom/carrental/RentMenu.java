/*
 * RentMenu.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.carrental;

import domainapp.dom.vehicle.Vehicle;
import domainapp.dom.customer.Customer;
import domainapp.dom.customer.CustomerRepository;
import domainapp.dom.vehicle.VehicleRepository;
import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.joda.time.LocalDate;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Rent.class
)
@DomainServiceLayout(
        named = "Vypůjčene vozy",
        menuOrder = "10"
)
public class RentMenu 
{
	
	@Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
				  named="Seznam vypůjčených vozů")
    @MemberOrder(sequence = "1")
    public List<Rent> list() 
	{
        return rentRepository.listAll();
    }
	
	public List<Customer> choices0Create()
	{
		return customerRepository.listAll();
	}
	
	public List<Vehicle> choices1Create()
	{
		return vehicleRepository.listAll();
	}
	
    public static class CreateDomainEvent extends ActionDomainEvent<RentMenu> {}
	
    @Action(domainEvent = CreateDomainEvent.class)
	@ActionLayout(named="Nová vypůjčka")
    @MemberOrder(sequence = "2")
    public Rent create(
           @ParameterLayout(named="Zakazník")
			final Customer customer, 
		   @ParameterLayout(named="Vozidlo")
			final Vehicle vehicle,
		   @ParameterLayout(named="Datum od")
			final LocalDate from,
		   @ParameterLayout(named="Datum do")
			final LocalDate to,
		   @ParameterLayout(named="Poznámka") @Parameter(optionality = Optionality.OPTIONAL)
			final String note) 
	{
        return rentRepository.create(customer, vehicle, from, to, note);
    }


    @javax.inject.Inject
    RentRepository rentRepository;
	
	@javax.inject.Inject
	CustomerRepository customerRepository;
	
	@javax.inject.Inject
	VehicleRepository vehicleRepository;
	
}
