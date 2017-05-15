/*
 * Vehicle.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.vehicle;

import domainapp.dom.carrental.Rent;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;
import test.annotation.TestMethod;
import test.annotation.TestParam;
import test.annotation.TestVar;

@javax.jdo.annotations.PersistenceCapable(
		identityType = IdentityType.DATASTORE,
		schema = "vehicle"
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
public class Vehicle implements Comparable<Rent> {

	@Getter
	@Setter
	@Title(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false", length = 128)
	public String type;

	@Getter
	@Setter
	@javax.jdo.annotations.Column(allowsNull = "false")
	@TestVar(name = "price", def = {"-500"})
	public int price;

	public Vehicle(
			@TestParam(name = "type", def = {"Škoda 120", "Škoda Fabia"}) String type,
			@TestParam(name = "price", def = {"10000", "50000"}) int price) {
		this.type = type;
		this.price = price;
	}

	@TestMethod(name = "title", def = {"Moje auto"})
	public String title() {
		return "Moje auto";
	}

	public String validatePrice(int price) {
		if (price < 0) {
			return "Cena musí být kladné číslo";
		} else {
			return null;
		}
	}

	@Override
	public int compareTo(Rent t) {
		return ObjectContracts.compare(this, t, "id");
	}

}
