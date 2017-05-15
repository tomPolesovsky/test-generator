/*
 * Rent.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.carrental;

import domainapp.dom.customer.Customer;
import domainapp.dom.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.LocalDate;

@javax.jdo.annotations.PersistenceCapable(
		identityType = IdentityType.DATASTORE,
		schema = "rent"
)
@javax.jdo.annotations.DatastoreIdentity(
		strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
		column = "id")
@javax.jdo.annotations.Version(
		strategy = VersionStrategy.DATE_TIME,
		column = "version")
@DomainObject(
		publishing = Publishing.ENABLED,
		auditing = Auditing.ENABLED
)
public class Rent implements Comparable<Rent> {

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "false")
	private Vehicle vehicle;

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "false")
	private Customer customer;

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "false")
	private LocalDate from;

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "false")
	private LocalDate to;

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "true", length = 512)
	private String note;

	public Rent(Customer customer, Vehicle vehicle, LocalDate from, LocalDate to) {
		this.customer = customer;
		this.vehicle = vehicle;
		this.from = from;
		this.to = to;
	}

	@Override
	public int compareTo(Rent t) {
		return ObjectContracts.compare(this, t, "id");
	}

}
