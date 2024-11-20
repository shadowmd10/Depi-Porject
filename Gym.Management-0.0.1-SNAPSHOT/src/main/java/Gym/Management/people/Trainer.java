package Gym.Management.people;


public class Trainer extends Person{

	private int workingHours = 0;
	private int assignedHall = 0;
	private double rating = 0.0;
	private double salary = 0.0;
	
	
	////////////Constructor//////////////
	public Trainer(String personName, int personAge, double salary) {
		super(personName, personAge);
		this.setSalary(salary);
		this.setRating(1.0);
	}
	
	public Trainer(String personName, int personAge,double salary, String personEmail, String personPhone, String personPassword, int workingHours, int assignedHall) {
		super(personName, personAge, personEmail, personPhone, personPassword);
		this.setWorkingHours(workingHours);
		this.setAssignedHall(assignedHall);
		this.setSalary(salary);
		this.setRating(1.0);
	}
/////////////////////////////////////////////////

	public int getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(int workingHours) {
		if(workingHours <= 6) {
			System.out.println("Invalid Working Hours => Working Hours must be greater than 6 hours");
		}
		else {
			this.workingHours = workingHours;
		}
	}

	public int getAssignedHall() {
		return assignedHall;
	}

	public void setAssignedHall(int assignedHall) {
		if(assignedHall <= 0) {
			System.out.println("Invalid Gym Hall");
		}
		else {
			this.assignedHall = assignedHall;
		}
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		if(rating <= 0.0 || rating > 5.0) {
			System.out.println("Invalid Value for Rating");
		}
		else {
			this.rating = rating;
		}
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		if(salary <= 0.0) {
			System.out.println("Invalid Value for Salary");
		}
		else {
			this.salary = salary;
		}
	}

	/////////////////Additional Methods///////////////
	public void increaseRating(double addRating) {
		if(addRating <= 0.0 || addRating >= 1.5) {
			System.out.println("Invalid Value for adding Rating");
		}
		else {
			this.setRating(this.getRating() + addRating);
		}
	}
	
	@Override
	public String toString() 
	{
		return "Mr/Ms " + this.getPersonName() + " : " + this.getPersonAge() + " years old. His/Her Email address : "
				+ this.getPersonEmail() + " with Rating : " + this.getRating();
	}
}
