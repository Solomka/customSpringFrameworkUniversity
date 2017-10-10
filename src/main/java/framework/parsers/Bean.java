package framework.parsers;

import java.util.ArrayList;

public class Bean {
	String name;
	String className;
	ArrayList<String> constructorArg = new ArrayList<String>();
	ArrayList<String> properties = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ArrayList<String> getConstructorArg() {
		return constructorArg;
	}

	public void setConstructorArg(ArrayList<String> constructorArg) {
		this.constructorArg = constructorArg;
	}

	public ArrayList<String> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<String> properties) {
		this.properties = properties;
	}

	public String toString() {
		return name + " : " + className.toString() + constructorArg.toString() + ", " + properties.toString();
	}
}
