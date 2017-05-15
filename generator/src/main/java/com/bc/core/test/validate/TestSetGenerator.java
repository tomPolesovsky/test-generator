/*
 * TestSetGenerator.java
 *
 * Copyright (c) 2017 Tomáš Polešovský, all Rights Reserved.
 * Faculty of Information Technology, Brno University of Technology
 */
package com.bc.core.test.validate;

import com.bc.core.generator.GeneratorTable;
import com.bc.core.generator.ITestSetGenerator;
import com.bc.core.generator.TestMethod;
import com.bc.core.generator.TestSet;
import com.bc.core.generator.TestSetWriter;
import com.bc.core.parser.ClassType;
import com.bc.core.parser.MetaClass;
import com.bc.core.parser.Method;
import com.bc.core.parser.Parser;
import java.util.HashMap;
import java.util.List;
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

	private boolean isValidateObject(MetaClass metaCls) {
		List<Method> methods = metaCls.getMethods();

		for (Method method : methods) {
			if (Generator.isValidateMethod(metaCls, method.getName())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void generateTestSets(TestSetWriter writer) {
		MetaClass metaCls;
		TestSet testSet;
		boolean writeObject;

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

			testSet.addMethod("public void I_have_the_value_N(int n, java.lang.String attribute)")
					.addAnnot("@Given(\"^I have the value \\\"([^\\\"]*)\\\" for a \\\"([^\\\"]*)\\\"$\")")
					.addLine("putVar(\"Integer\", attribute, n);");

			TestMethod method = testSet.addMethod("public void I_validate_item(java.lang.String attribute)");
			method.addAnnot("@When(\"^I validate the \\\"(.*)\\\" of the " + metaCls.getName() + "\")");

			// if fixture script {Object}Create exists
			if (this.classesTable.containsKey("domainapp.dom.generator.fixture." + metaCls.getName() + "Create")) {
				fullName = metaCls.getPack() + "." + metaCls.getName();
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

			method.addLine("try {");
			method.addLine("\tString errorMessage = (String) " + metaCls.getName().toLowerCase() + "."
					+ "getClass().getMethod(\"validate\"+attribute.substring(0, 1).toUpperCase() + attribute.substring(1)+\"\", int.class)"
					+ ".invoke(" + metaCls.getName().toLowerCase() + ",");
			method.addLine("\tgetVar(\"Integer\", attribute, Integer.class));");
			method.addLine("\tputVar(\"String\", \"errorMessage\", errorMessage);");
			method.addLine("} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException "
					+ "| java.lang.reflect.InvocationTargetException ex) {");
			method.addLine("java.util.logging.Logger.getLogger(" + metaCls.getName() + "Glue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);");
			method.addLine("\tSystem.err.println(\"Cannot invoke method\");");
			method.addLine("}\n");

			testSet.addMethod("public void I_receive_the_error_message()")
					.addAnnot("@Then(\"^I receive the error message\")")
					.addLine("getVar(\"String\", \"errorMessage\", String.class);");

			if (metaCls.getType() == ClassType.DOMAIN_OBJECT && this.isValidateObject(metaCls) && writeObject) {
				writer.writeTestSet(testSet, metaCls);
			}
		}
	}

}
