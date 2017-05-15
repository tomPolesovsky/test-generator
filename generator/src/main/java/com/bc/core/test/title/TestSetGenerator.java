/*
 * TestSetGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.title;

import com.bc.core.generator.GeneratorTable;
import com.bc.core.generator.ITestSetGenerator;
import com.bc.core.generator.TestMethod;
import com.bc.core.generator.TestSet;
import com.bc.core.generator.TestSetWriter;
import com.bc.core.parser.ClassType;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Parser;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomáš Polešovský
 */
public class TestSetGenerator implements ITestSetGenerator {

	private final HashMap<String, MetaClass> parserTable;

	private GeneratorTable table;

	private final HashMap<String, Class> classesTable;

	public TestSetGenerator(Parser parser, GeneratorTable table) {
		this.classesTable = parser.getClasses();
		this.parserTable = parser.getTable();
		this.table = table;
	}

	@Override
	public void generateTestSets(TestSetWriter writer) {
		boolean writeObject;
		MetaClass metaCls;
		TestSet testSet;

		String fullName;

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

			TestMethod method = testSet.addMethod("public void I_have_the_object()");
			method.addAnnot("@Given(\"^I have the object Vehicle$\")");
			fullName = metaCls.getPack() + "." + metaCls.getName();

			// if fixture script {Object}Create exists
			if (this.classesTable.containsKey("domainapp.dom.generator.fixture." + metaCls.getName() + "Create")) {
				method.addLine(fullName + " " + metaCls.getName().toLowerCase() + ";\n");

				method.addLine("if (service(RepositoryService.class).isPersistent(" + fullName + ".class)"
						+ "\n\t\t\t&& service(RepositoryService.class).allInstances(" + fullName + ".class).size() > 0){");
				method.addLine("\t" + metaCls.getName().toLowerCase() + " = service(RepositoryService.class).allInstances(" + fullName + ".class).get(0);");
				method.addLine("}else{");
				method.addLine("\tdomainapp.dom.generator.fixture." + metaCls.getName() + "Create fix = new domainapp.dom.generator.fixture." + metaCls.getName() + "Create();");
				method.addLine("\t" + metaCls.getName().toLowerCase() + " = fix.execute();");
				method.addLine("}\n");
			} else {
				writeObject = false;
			}

			method.addLine("putVar(\"" + fullName + "\", \"" + metaCls.getName().toLowerCase() + "\", " + metaCls.getName().toLowerCase() + ");");

			testSet.addMethod("public void I_set_the_title()")
					.addAnnot("@When(\"^I set the title of the " + metaCls.getName() + "$\")")
					.addLine(fullName + " " + metaCls.getName().toLowerCase() + " = getVar(\"" + fullName + "\", \"" + metaCls.getName().toLowerCase() + "\", " + fullName + ".class);")
					.addLine("String title = " + metaCls.getName().toLowerCase() + ".title();")
					.addLine("putVar(\"String\", \"title\", title);");

			testSet.addMethod("public void I_receive_the_string_title()")
					.addAnnot("@Then(\"^I receive the string title$\")")
					.addLine("junit.framework.TestCase.assertEquals(getVar(\"String\", \"title\", String.class), \"" + table.getMethodDefaultValue(metaCls) + "\");");

			if (metaCls.getType() == ClassType.DOMAIN_OBJECT && Generator.isValidateObject(metaCls) && writeObject) {
				writer.writeTestSet(testSet, metaCls);
			}
		}
	}

}
