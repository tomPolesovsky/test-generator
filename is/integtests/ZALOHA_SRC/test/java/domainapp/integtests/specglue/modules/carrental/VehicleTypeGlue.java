package domainapp.integtests.specglue.modules.carrental;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.dom.carrental.VehicleType;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;


public class VehicleTypeGlue extends CukeGlueAbstract
{

	@Given("^I have (\\d+) VehicleType objects$")
	public void I_have_N_VehicleType_objects(int n) throws Throwable
	{
		try {
			final List<VehicleType> findAll = service(RepositoryService.class).allInstances(VehicleType.class);
			assertThat(findAll.size(), is(n));
			putVar("list", "all", findAll);
		} finally {
			assertMocksSatisfied();
		}
	}

	@When("^I create a VehicleType object$")
	public void I_create_a_VehicleType_object() throws Throwable
	{
		final VehicleType object = new VehicleType();
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

}
