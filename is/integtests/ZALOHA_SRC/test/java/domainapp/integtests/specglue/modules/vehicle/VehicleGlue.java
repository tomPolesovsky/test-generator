package domainapp.integtests.specglue.modules.vehicle;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domainapp.dom.vehicle.Vehicle;
import java.util.List;
import junit.framework.Assert;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;


public class VehicleGlue extends CukeGlueAbstract
{

	@Given("^I have (\\d+) Vehicle objects$")
	public void I_have_N_Vehicle_objects(int n) throws Throwable
	{
		try {
			final List<Vehicle> findAll = service(RepositoryService.class).allInstances(Vehicle.class);
			assertThat(findAll.size(), is(n));
			putVar("list", "all", findAll);
		} finally {
			assertMocksSatisfied();
		}
	}

	@When("^I create a Vehicle object with vvOPSWJE \"(.*)\" and vkSDsnGX \"(\\d+)\"$")
	public void I_create_a_Vehicle_object(java.lang.String vvOPSWJE, int vkSDsnGX) throws Throwable
	{
		final Vehicle object = new Vehicle(vvOPSWJE, vkSDsnGX);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}
	
	@Given("^I have the value \"([^\"]*)\" for a price$")
	public void I_have_the_value_N_for_a_price(String value) throws Throwable
	{
		Integer price = Integer.parseInt(value);
		putVar("Integer", "price", price);
	}
	
	@When("^I validate the price of the Vehicle$")
	public void I_validate_the_price_of_the_Vehicle() throws Throwable
	{
		final Vehicle object = new Vehicle("Skoda", 250);
		String errorMessage = object.validatePrice(getVar("Integer", "price", Integer.class));
		putVar("String", "errorMessage", errorMessage);
	}
	
	
	@Then("^I receive the error message$")
	public void I_receive_the_error_message() throws Throwable
	{
		Assert.assertEquals(getVar("String", "errorMessage", String.class), null);
	}
	
	

}
