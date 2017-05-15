/*
 * TestSetGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.object;

import com.bc.core.app.Utils;
import com.bc.core.generator.GeneratorTable;
import com.bc.core.generator.ITestSetGenerator;
import com.bc.core.generator.TestMethod;
import com.bc.core.generator.TestSet;
import com.bc.core.generator.TestSetWriter;
import com.bc.core.parser.ClassType;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Parameter;
import com.bc.core.parser.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tomáš Polešovský
 */
public class TestSetGenerator implements ITestSetGenerator {

	private final HashMap<String, Class> classesTable;

	private final HashMap<String, MetaClass> parserTable;

	private GeneratorTable table;

	public TestSetGenerator(Parser parser, GeneratorTable table) {
		this.classesTable = parser.getClasses();
		this.parserTable = parser.getTable();
		this.table = table;
	}

	@Override
	public void generateTestSets(TestSetWriter writer) {
		MetaClass metaCls;
		TestSet testSet;
		boolean writeObject;
		int i;
		String var;

		List<Parameter> objectParams = new ArrayList();

		for (Map.Entry<String, MetaClass> entry : this.parserTable.entrySet()) {
			writeObject = true;
			metaCls = entry.getValue();
			testSet = new TestSet(metaCls.getName());

			testSet.addLibrary("cucumber.api.java.en.Given");
			testSet.addLibrary("cucumber.api.java.en.When");
			testSet.addLibrary("cucumber.api.java.en.Then;");
			testSet.addLibrary(metaCls.getPack() + "." + metaCls.getName());
			testSet.addLibrary("java.util.List");
			testSet.addLibrary("org.apache.isis.applib.services.registry.ServiceRegistry2");
			testSet.addLibrary("org.apache.isis.applib.services.repository.RepositoryService");
			testSet.addLibrary("org.hamcrest.CoreMatchers.is", true);
			testSet.addLibrary("org.junit.Assert.assertThat", true);
			testSet.addLibrary("org.apache.isis.core.specsupport.specs.CukeGlueAbstract");

			testSet.addAttribute("private int instances");

			testSet.addMethod("public void I_have_N_" + metaCls.getName() + "_objects(int n) throws Throwable")
					.addAnnot("@Given(\"^I have (\\\\d+) " + metaCls.getName() + " objects$\")")
					.addLine("final List<" + metaCls.getName() + "> findAll = service(RepositoryService.class).allInstances(" + metaCls.getName() + ".class);")
					.addLine("instances = findAll.size()+n;");

			TestMethod method = testSet.addMethod("public void I_create_a_" + metaCls.getName() + "_object(" + table.getConstructParams(metaCls) + ") throws Throwable");
			method.addAnnot("@When(\"^I create a " + metaCls.getName() + " object" + table.getConstructAnnotParams(metaCls) + "$\")");

			if (!Generator.isSupportedObject(metaCls)) {
				i = 0;
				for (Parameter param : metaCls.getConstructParams()) {
					if (!Utils.isSupportedType(param.getType())) {
						String[] types = (param.getType()).split("\\.");
						var = Utils.getRandomString(8);

						if (this.classesTable.containsKey("domainapp.dom.generator.fixture." + types[types.length - 1] + "Create")) {
							method.addLine(param.getType() + " " + var + ";\n");

							method.addLine("if (service(RepositoryService.class).isPersistent(" + param.getType() + ".class)"
									+ "\n\t\t\t&& service(RepositoryService.class).allInstances(" + param.getType() + ".class).size() > 0){");
							method.addLine("\t" + var + " = service(RepositoryService.class).allInstances(" + param.getType() + ".class).get(0);");
							method.addLine("}else{");
							method.addLine("\tdomainapp.dom.generator.fixture." + types[types.length - 1] + "Create fi" + i + "x = new domainapp.dom.generator.fixture." + types[types.length - 1] + "Create();");
							method.addLine("\t" + var + " = fi" + i + "x.execute();");
							method.addLine("}\n");
						} else {
							writeObject = false;
							break;
						}

						objectParams.add(new Parameter(param.getType(), var));
					} else {
						objectParams.add(param);
					}

					i++;
				}
			} else {
				objectParams = metaCls.getConstructParams();
			}

			method.addLine("final " + metaCls.getName() + " object = new " + metaCls.getName() + "(" + table.getConstructObjParams(objectParams) + ");")
					.addLine("ServiceRegistry2 serviceRegistry = service(ServiceRegistry2.class);")
					.addLine("serviceRegistry.injectServicesInto(object);")
					.addLine("service(RepositoryService.class).persist(object);");

			testSet.addMethod("public void I_receive_N_" + metaCls.getName() + "_objects(int n)")
					.addAnnot("@Then(\"^I receive (\\\\d+) " + metaCls.getName() + " objects\")")
					.addLine("try {")
					.addLine("\tfinal List<" + metaCls.getName() + "> findAll = service(RepositoryService.class).allInstances(" + metaCls.getName() + ".class);")
					.addLine("\tassertThat(findAll.size(), is(this.instances + n));")
					.addLine("} finally {")
					.addLine("\tassertMocksSatisfied();")
					.addLine("}");

			if (metaCls.getType() == ClassType.DOMAIN_OBJECT && writeObject) {
				writer.writeTestSet(testSet, metaCls);
			}
		}
	}

}
