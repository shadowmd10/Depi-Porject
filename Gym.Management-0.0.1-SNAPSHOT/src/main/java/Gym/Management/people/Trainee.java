package Gym.Management.people;

import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;

public class Trainee extends Person{

	private Subscription subcription = null;
	private ExercisePlan plan = null;
	private int points = 0;
	
	//////////Constructors//////////////////////
	public Trainee(String personName, int personAge) {
		super(personName, personAge);
		this.setPoints(50);
	}

	public Trainee(String personName, int personAge, String personEmail, String personPhone, String personPassword, Subscription subcription, ExercisePlan plan) {
		super(personName, personAge, personEmail, personPhone, personPassword);
		this.setSubcription(subcription);
		this.setPlan(plan);
		this.setPoints(50);
	}

	/////////Getters and Setters/////////////////
	public Subscription getSubcription() {
		return subcription;
	}

	public void setSubcription(Subscription subcription) {
		if(subcription == null) {
			System.out.println("Invalid Subscription Data");
		}
		else {
			this.subcription = subcription;
		}
	}

	public ExercisePlan getPlan() {
		return plan;
	}

	public void setPlan(ExercisePlan plan) {
		if(plan == null) {
			System.out.println("Invalid Exercise Plan");
		}
		else {
			this.plan = plan;
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		if(points < 0) {
			System.out.println("Invalid Value for Points");
		}
		else {
			this.points = points;
		}
	}
	
	
	/////////////Additional Methods//////////////
	public void increasePoints(int addPoints) {
		if(addPoints <= 0) {
			System.out.println("Invalid Value for added points");
		}
		else {
			this.setPoints(this.getPoints() + addPoints);
		}
	}
	
	
	@Override
	public String toString() 
	{
		return this.getSubcription().getSubscriptionType() + " Subscriper Mr/Ms " + this.getPersonName() + " : " + this.getPersonAge() + 
				" years old. His/Her Email address : " + this.getPersonEmail() + " has : " + this.getPoints() + "Points";
	}
}
