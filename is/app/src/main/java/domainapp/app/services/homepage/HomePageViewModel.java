/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.app.services.homepage;

import domainapp.dom.carrental.Rent;
import domainapp.dom.carrental.RentMenu;
import domainapp.dom.carrental.RentMenu.CreateDomainEvent;
import domainapp.dom.carrental.RentRepository;
import domainapp.dom.customer.Customer;
import domainapp.dom.vehicle.Vehicle;
import domainapp.dom.vehicle.VehicleMenu;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;

import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.joda.time.LocalDate;

@ViewModel
public class HomePageViewModel {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Autopůjčovna");
    }
    //endregion

    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    public List<Rent> getRentalCars() {
        return rentRepository.listAll();
    }
    //endregion
	
    @Action(domainEvent = CreateDomainEvent.class)
	@ActionLayout(named="Nová vypůjčka")
    @MemberOrder(sequence = "2")
    public Rent create(
           @ParameterLayout(named="Zakazník")
			final Customer customer, 
		   @ParameterLayout(named="Vozidlo")
			final Vehicle vehicle,
		   @ParameterLayout(named="Datum od")
			final LocalDate from,
		   @ParameterLayout(named="Datum do")
			final LocalDate to,
		   @ParameterLayout(named="Poznámka") @Parameter(optionality = Optionality.OPTIONAL)
			final String note) 
	{
        return rentRepository.create(customer, vehicle, from, to, note);
    }

    //region > injected services

    @javax.inject.Inject
    RentRepository rentRepository;

    //endregion
}
