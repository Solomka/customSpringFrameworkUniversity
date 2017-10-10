package application.hw1;

import application.classes.Car;

public class CarWashWorkerImpl implements CarWashWorker {

	private Car car;

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public void washCar() {
		System.out.println("Worker is washing a car: " + car.toString());
	}

}
