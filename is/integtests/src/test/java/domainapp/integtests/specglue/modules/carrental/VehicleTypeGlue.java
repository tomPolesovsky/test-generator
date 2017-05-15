package domainapp.integtests.specglue.modules.carrental;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;;
import domainapp.dom.carrental.VehicleType;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;
import changed.annotation.TestChanged;


public class VehicleTypeGlue extends CukeGlueAbstract
{

	private int instances;

	@Given("^I have (\\d+) VehicleType objects$")
	public void I_have_N_VehicleType_objects(int n) throws Throwable
	{
		final List<VehicleType> findAll = service(RepositoryService.class).allInstances(VehicleType.class);
		instances = findAll.size()+n;
	}

	@When("^I create a VehicleType object with vL7r1rtP \"(.*)\" and vr1hujUl \"(.*)\"$")
	public void I_create_a_VehicleType_object(java.lang.String vL7r1rtP, java.lang.String vr1hujUl) throws Throwable
	{
		final VehicleType object = new VehicleType(vL7r1rtP, vr1hujUl);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

	@Then("^I receive (\\d+) VehicleType objects")
	public void I_receive_N_VehicleType_objects(int n)
	{
		try {
			final List<VehicleType> findAll = service(RepositoryService.class).allInstances(VehicleType.class);
			assertThat(findAll.size(), is(this.instances + n));
		} finally {
			assertMocksSatisfied();
		}
	}

}