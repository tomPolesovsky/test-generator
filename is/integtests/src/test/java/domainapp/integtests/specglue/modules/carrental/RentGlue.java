package domainapp.integtests.specglue.modules.carrental;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;;
import domainapp.dom.carrental.Rent;
import java.util.List;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;
import changed.annotation.TestChanged;


public class RentGlue extends CukeGlueAbstract
{

	private int instances;

	@Given("^I have (\\d+) Rent objects$")
	public void I_have_N_Rent_objects(int n) throws Throwable
	{
		final List<Rent> findAll = service(RepositoryService.class).allInstances(Rent.class);
		instances = findAll.size()+n;
	}

	@When("^I create a Rent object$")
	public void I_create_a_Rent_object() throws Throwable
	{
		domainapp.dom.customer.Customer vlS3v6y1;

		if (service(RepositoryService.class).isPersistent(domainapp.dom.customer.Customer.class)
			&& service(RepositoryService.class).allInstances(domainapp.dom.customer.Customer.class).size() > 0){
			vlS3v6y1 = service(RepositoryService.class).allInstances(domainapp.dom.customer.Customer.class).get(0);
		}else{
			domainapp.dom.generator.fixture.CustomerCreate fi0x = new domainapp.dom.generator.fixture.CustomerCreate();
			vlS3v6y1 = fi0x.execute();
		}

		domainapp.dom.vehicle.Vehicle vDD3yObp;

		if (service(RepositoryService.class).isPersistent(domainapp.dom.vehicle.Vehicle.class)
			&& service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).size() > 0){
			vDD3yObp = service(RepositoryService.class).allInstances(domainapp.dom.vehicle.Vehicle.class).get(0);
		}else{
			domainapp.dom.generator.fixture.VehicleCreate fi1x = new domainapp.dom.generator.fixture.VehicleCreate();
			vDD3yObp = fi1x.execute();
		}

		org.joda.time.LocalDate vRRReVD6;

		if (service(RepositoryService.class).isPersistent(org.joda.time.LocalDate.class)
			&& service(RepositoryService.class).allInstances(org.joda.time.LocalDate.class).size() > 0){
			vRRReVD6 = service(RepositoryService.class).allInstances(org.joda.time.LocalDate.class).get(0);
		}else{
			domainapp.dom.generator.fixture.LocalDateCreate fi2x = new domainapp.dom.generator.fixture.LocalDateCreate();
			vRRReVD6 = fi2x.execute();
		}

		org.joda.time.LocalDate vunAqOrn;

		if (service(RepositoryService.class).isPersistent(org.joda.time.LocalDate.class)
			&& service(RepositoryService.class).allInstances(org.joda.time.LocalDate.class).size() > 0){
			vunAqOrn = service(RepositoryService.class).allInstances(org.joda.time.LocalDate.class).get(0);
		}else{
			domainapp.dom.generator.fixture.LocalDateCreate fi3x = new domainapp.dom.generator.fixture.LocalDateCreate();
			vunAqOrn = fi3x.execute();
		}

		final Rent object = new Rent(vlS3v6y1, vDD3yObp, vRRReVD6, vunAqOrn);
		ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);
		serviceRegistry.injectServicesInto(object);
		service(RepositoryService.class).persist(object);
	}

	@Then("^I receive (\\d+) Rent objects")
	public void I_receive_N_Rent_objects(int n)
	{
		try {
			final List<Rent> findAll = service(RepositoryService.class).allInstances(Rent.class);
			assertThat(findAll.size(), is(this.instances + n));
		} finally {
			assertMocksSatisfied();
		}
	}

}