package application.classes;

public class Car implements Transport {

	private String message;
	private String name;
	private Integer wheelCount;

	public Car() {
		this.message = "I'm the Car";
	}

	public Car( int wheelCount) {
		this.message = "I'm the Car";
		
		this.name = "Default Car";
		this.wheelCount = wheelCount;
	}

	public Car(String name, String wheelCount) {
		this.message = "I'm the Car";
		
		this.name = name;
		this.wheelCount = Integer.decode(wheelCount);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWheelCount() {
		return wheelCount;
	}

	public void setWheelCount(Integer wheelCount) {
		this.wheelCount = wheelCount;
	}
	
	public void getTransport() {
		System.out.println(message);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Car [message=").append(message).append(", name=").append(name).append(", wheelCount=")
				.append(wheelCount).append("] ");
		return builder.toString();
	}

		

	
}
