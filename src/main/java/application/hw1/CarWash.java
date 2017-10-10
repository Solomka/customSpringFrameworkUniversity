package application.hw1;

import application.classes.Car;

public class CarWash {

	private Car car;

	public CarWash() {

	}

	public CarWash(Car car) {
		this.car = car;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void washCar() {
		System.out.println("Car is being washed =)");
	}
}
