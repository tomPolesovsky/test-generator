package domainapp.integtests.specglue.modules.customer;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;;
import domainapp.dom.customer.Customer;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;
import changed.annotation.TestChanged;


public class CustomerGlue extends CukeGlueAbstract
{

	private int instances;

	@Given("^I have (\\d+) Customer objects$")
	public void I_have_N_Customer_objects(int n) throws Throwable
	{
		final List<Customer> findAll = service(RepositoryService.class).allInstances(Customer.class);
		instances = findAll.size()+n;
	}

	@When("^I create a Customer object with v1yNviAA \"(.*)\" and vmlQ8TkI \"(.*)\" and vbleDKPF \"(.*)\"$")
	public void I_create_a_Customer_object(java.lang.String v1yNviAA, java.lang.String vmlQ8TkI, java.lang.String vbleDKPF) throws Throwable
	{
		final Customer object = new Customer(v1yNviAA, vmlQ8TkI, vbleDKPF);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

	@Then("^I receive (\\d+) Customer objects")
	public void I_receive_N_Customer_objects(int n)
	{
		try {
			final List<Customer> findAll = service(RepositoryService.class).allInstances(Customer.class);
			assertThat(findAll.size(), is(this.instances + n));
		} finally {
			assertMocksSatisfied();
		}
	}

}