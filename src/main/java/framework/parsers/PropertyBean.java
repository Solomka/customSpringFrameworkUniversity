package framework.parsers;

public class PropertyBean {

	private String name;
	private Bean bean;

	public PropertyBean(String name, Bean bean) {
		this.name = name;
		this.bean = bean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyBean [name=").append(name).append(", bean=").append(bean).append("] ");
		return builder.toString();
	}
}
