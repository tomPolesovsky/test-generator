package domainapp.integtests.specglue.modules.vehicle;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;;
import domainapp.dom.vehicle.Vehicle;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;
import changed.annotation.TestChanged;


public class VehicleGlue extends CukeGlueAbstract
{

	@Given("^I have the object Vehicle$")
	public void I_have_the_object()
	{
		domainapp.dom.vehicle.Vehicle vehicle;

		if (service(RepositoryService.class).isPersistent(domainapp.dom.vehicle.Vehicle.class)
			&& service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).size() > 0){
			vehicle = service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).get(0);
		}else{
			domainapp.dom.generator.fixture.VehicleCreate fix = new domainapp.dom.generator.fixture.VehicleCreate();
			vehicle = fix.execute();
		}

		putVar("domainapp.dom.vehicle.Vehicle", "vehicle", vehicle);
	}

	@When("^I set the title of the Vehicle$")
	public void I_set_the_title()
	{
		domainapp.dom.vehicle.Vehicle vehicle = getVar("domainapp.dom.vehicle.Vehicle", "vehicle", domainapp.dom.vehicle.Vehicle.class);
		String title = vehicle.title();
		putVar("String", "title", title);
	}

	@Then("^I receive the string title$")
	public void I_receive_the_string_title()
	{
		junit.framework.TestCase.assertEquals(getVar("String", "title", String.class), "Moje auto");
	}

	private int instances;

	@Given("^I have (\\d+) Vehicle objects$")
	public void I_have_N_Vehicle_objects(int n) throws Throwable
	{
		final List<Vehicle> findAll = service(RepositoryService.class).allInstances(Vehicle.class);
		instances = findAll.size()+n;
	}

	@When("^I create a Vehicle object with type \"(.*)\" and price \"(\\d+)\"$")
	public void I_create_a_Vehicle_object(java.lang.String type, int price) throws Throwable
	{
		final Vehicle object = new Vehicle(type, price);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

	@Then("^I receive (\\d+) Vehicle objects")
	public void I_receive_N_Vehicle_objects(int n)
	{
		try {
			final List<Vehicle> findAll = service(RepositoryService.class).allInstances(Vehicle.class);
			assertThat(findAll.size(), is(this.instances + n));
		} finally {
			assertMocksSatisfied();
		}
	}

	@Given("^I have the value \"([^\"]*)\" for a \"([^\"]*)\"$")
	public void I_have_the_value_N(int n, java.lang.String attribute)
	{
		putVar("Integer", attribute, n);
	}

	@When("^I validate the \"(.*)\" of the Vehicle")
	public void I_validate_item(java.lang.String attribute)
	{
		domainapp.dom.vehicle.Vehicle vehicle;

		if (service(RepositoryService.class).isPersistent(domainapp.dom.vehicle.Vehicle.class)
			&& service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).size() > 0){
			vehicle = service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).get(0);
		}else{
			domainapp.dom.generator.fixture.VehicleCreate fix = new domainapp.dom.generator.fixture.VehicleCreate();
			vehicle = fix.execute();
		}

		try {
			String errorMessage = (String) vehicle.getClass().getMethod("validate"+attribute.substring(0, 1).toUpperCase() + attribute.substring(1)+"", int.class).invoke(vehicle,
			getVar("Integer", attribute, Integer.class));
			putVar("String", "errorMessage", errorMessage);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException ex) {
		java.util.logging.Logger.getLogger(VehicleGlue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			System.err.println("Cannot invoke method");
		}

	}

	@Then("^I receive the error message")
	public void I_receive_the_error_message()
	{
		getVar("String", "errorMessage", String.class);
	}

}