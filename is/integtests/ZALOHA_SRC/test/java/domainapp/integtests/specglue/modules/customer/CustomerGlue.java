package domainapp.integtests.specglue.modules.customer;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.dom.customer.Customer;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;


public class CustomerGlue extends CukeGlueAbstract
{

	@Given("^I have (\\d+) Customer objects$")
	public void I_have_N_Customer_objects(int n) throws Throwable
	{
		try {
			final List<Customer> findAll = service(RepositoryService.class).allInstances(Customer.class);
			assertThat(findAll.size(), is(n));
			putVar("list", "all", findAll);
		} finally {
			assertMocksSatisfied();
		}
	}

	@When("^I create a Customer object with v1jwIirs \"(.*)\" and vOjNSHyM \"(.*)\" and vgAoYRdi \"(.*)\"$")
	public void I_create_a_Customer_object(java.lang.String v1jwIirs, java.lang.String vOjNSHyM, java.lang.String vgAoYRdi) throws Throwable
	{
		final Customer object = new Customer(v1jwIirs, vOjNSHyM, vgAoYRdi);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

}
