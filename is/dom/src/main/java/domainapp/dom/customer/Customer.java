/*
 * Customer.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.customer;

import javax.jdo.annotations.IdentityType;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "customer"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Unique(name="Customer_email_UNQ", members = {"email"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Customer implements Comparable<Customer>  
{
	@Getter @Setter
	@javax.jdo.annotations.Column(allowsNull = "false", length = 128)
	@Title(sequence = "1")
	private String name;
	
	@Getter @Setter
	@javax.jdo.annotations.Column(allowsNull = "false", length = 128)
	@Title(sequence = "2")
	private String surname;
	
	@Getter @Setter
	@javax.jdo.annotations.Column(allowsNull = "false", length = 128)
	private String email;
	
	public Customer(String name, String surname, String email) 
	{
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	
	@Override
	public int compareTo(Customer t) 
	{
		return ObjectContracts.compare(this, t, "id");
	}
}
