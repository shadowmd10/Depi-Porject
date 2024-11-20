package Gym.Management.people;

import java.util.List;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

import Gym.Management.facility.Equipment;
import Gym.Management.facility.GymHall;
import Gym.Management.papers.ExercisePlan;

public interface AdminControl {

//	static List<GymHall> allHalls = new ArrayList<GymHall>(Arrays.asList());
//	
//	static List<List<Equipment>> allEquipments = new ArrayList<List<Equipment>>(Arrays.asList());
//	
//	static List<List<Trainer>> allTrainers = new ArrayList<List<Trainer>>(Arrays.asList());
//	
//	static List<List<Trainee>> allTrainees = new ArrayList<List<Trainee>>(Arrays.asList());
	
	///////////Control Methods////////////////////
	void addHall(GymHall hall);
	void removeHall(int hallID);
	
	void addEquipment(int hallID, Equipment equipment);
	void removeEquipment(int equipmentID);
	
	void addTrainer(Trainer trainer);
	void removeTrainer(int trainerID);
	void displayAllTrainers();
	void assigneTrainer(Trainer trainer, GymHall hall);
	
	void addTrainee(Trainee trainee);
	void removeTrainee(int traineeID);
	List<Trainee> displayAllTrainees();
	void changeTraineePlan(Trainee trainee, ExercisePlan plan);
	
	boolean sendEmailNotification(Trainee t);
	
}
