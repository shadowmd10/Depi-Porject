package Gym.Management.papers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExercisePlan implements ControlSteps{

	private static int ecercisePlanID = 0;
	private List<String> exerciseSteps = null;
	private int exerciseDuration = 0;
	
	//////////Constructor/////////////
	////Initialize the Plan then add steps one by one//////
	public ExercisePlan(int exerciseDuration) 
	{
		setEcercisePlanID() ;
		this.exerciseSteps = new ArrayList<String>(Arrays.asList());
		this.setExerciseDuration(exerciseDuration);
	}
	///////Initialize the Plan with the whole steps////////
	public ExercisePlan(List<String> exerciseSteps, int exerciseDuration) 
	{
		setEcercisePlanID() ;
		this.setExerciseSteps(exerciseSteps);
		this.setExerciseDuration(exerciseDuration);
	}

	/////////Getters and Setters////////////
	public int getEcercisePlanID() {
		return ecercisePlanID;
	}

	private static void setEcercisePlanID() {
		ExercisePlan.ecercisePlanID ++;
	}

	public List<String> getExerciseSteps() {
		return exerciseSteps;
	}

	public void setExerciseSteps(List<String> exerciseSteps) {
		if(exerciseSteps == null) {
			System.out.println("Invalid Steps for the Exercise Plan");
		}
		else {
			this.exerciseSteps = exerciseSteps;
		}
	}

	public int getExerciseDuration() {
		return exerciseDuration;
	}

	public void setExerciseDuration(int exerciseDuration) {
		if(exerciseDuration <= 0) {
			System.out.println("Invalid Duration for the Exercise Plan");
		}
		else {
			this.exerciseDuration = exerciseDuration;
		}
	}
	/////////////////////////////////////////////////
	@Override
	public void addStep(String step) {
		if(step == null || step == "" || step.length() <= 5) {
			System.out.println("Invalid Step");
		}
		else {
			this.exerciseSteps.add(step);
		}
	}
	
	@Override
	public void removeStep(String step) {
		if(this.exerciseSteps.contains(step)) {
			this.exerciseSteps.remove(step);
		}
		else {
			System.out.println("This Step doesn't exist in the Exercise Plan");
		}
	}
	
}
