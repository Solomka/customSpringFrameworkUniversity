package application.classes;

public class Bus implements Transport {
	private String message;

	public Bus() {
		message = "I am the Bus!";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void getTransport() {
		System.out.println(message);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bus [message=").append(message).append("] ");
		return builder.toString();
	}

}
