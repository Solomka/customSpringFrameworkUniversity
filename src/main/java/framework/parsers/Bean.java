package framework.parsers;

import java.util.ArrayList;
import java.util.List;

public class Bean {
	private String name;// id
	private String className;
	private String value;

	private List<Bean> constructorArg = new ArrayList<>();
	private List<PropertyBean> properties = new ArrayList<>();

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

	public List<Bean> getConstructorArg() {
		return constructorArg;
	}

	public void setConstructorArg(List<Bean> constructorArg) {
		this.constructorArg = constructorArg;
	}

	public List<PropertyBean> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyBean> properties) {
		this.properties = properties;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return name + " : " + (className != null ? className.toString() : "im not lucky") + constructorArg.toString()
				+ ", " + properties.toString();
	}
}
