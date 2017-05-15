/*
 * VehicleType.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package domainapp.dom.carrental;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "vehicleType"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Unique(name="VehicleType_brand_type_UNQ", members = {"brand", "type"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class VehicleType implements Comparable<VehicleType> 
{
	
	public String brand;
	
	public String type;
	
	public VehicleType(String brand, String type)
	{
		this.brand = brand;
		this.type = type;
	}

	@Override
	public int compareTo(VehicleType t) 
	{
		return ObjectContracts.compare(this, t, "id");
	}
	
}
