/*
 * CustomerMenu.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.customer;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Customer.class
)
@DomainServiceLayout(
        named = "Zákazníci",
        menuOrder = "10"
)
public class CustomerMenu 
{
	
	@Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
				  named="Seznam zákazníků")
    @MemberOrder(sequence = "1")
    public List<Customer> list() 
	{
        return customerRepository.listAll();
    }
	
	public static class CreateDomainEvent extends ActionDomainEvent<CustomerMenu> {}
	
    @Action(domainEvent = CreateDomainEvent.class)
	@ActionLayout(named="Nový zákazník")
    @MemberOrder(sequence = "2")
    public Customer create(
           @ParameterLayout(named="Jméno")
			final String name, 
		   @ParameterLayout(named="Příjmení")
			final String surname,
		   @ParameterLayout(named="Email")
			final String email) 
	{
        return customerRepository.create(name, surname, email);
    }
	
	@javax.inject.Inject
    CustomerRepository customerRepository;
	
}
