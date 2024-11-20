package Gym.Management.papers;

import Gym.Management.people.Trainee;
import Gym.Management.people.Trainer;

public class TestExcel {

	public static void main(String[] args) {

		//ExcelSheets.createSheet();
		
		Trainer trainer = new Trainer("Mahmoud", 26, 4000, "mahmoud.alaa@gmail.com", "01550141236", "123456789", 8, 1);
		
		
		Subscription sub = new Subscription("Silver");
		ExercisePlan plan = new ExercisePlan(20);
		Trainee trainee = new Trainee("Alaa", 22, "alaa45@gmail.com", "01243147635", "123456789", sub, plan);
		
		
		ExcelSheets.takeAttendace(trainer);
		ExcelSheets.takeAttendace(trainee);
		
		ExcelSheets.createCard(trainer);
		ExcelSheets.createCard(trainee);
	}

}
