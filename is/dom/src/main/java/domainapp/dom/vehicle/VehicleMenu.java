/*
 * VehicleMenu.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.vehicle;

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
		repositoryFor = Vehicle.class
)
@DomainServiceLayout(
		named = "Vozidla",
		menuOrder = "10"
)
public class VehicleMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
			named = "Seznam vozidel")
	@MemberOrder(sequence = "1")
	public List<Vehicle> list() {
		return vehicleRepository.listAll();
	}

	public static class CreateDomainEvent extends ActionDomainEvent<VehicleMenu> {
	}

	@Action(domainEvent = CreateDomainEvent.class)
	@ActionLayout(named = "Nové vozidlo")
	@MemberOrder(sequence = "2")
	public Vehicle create(
			@ParameterLayout(named = "Typ")
			final String type,
			@ParameterLayout(named = "Cena")
			final int price) {
		return vehicleRepository.create(type, price);
	}

	@javax.inject.Inject
	VehicleRepository vehicleRepository;

}
