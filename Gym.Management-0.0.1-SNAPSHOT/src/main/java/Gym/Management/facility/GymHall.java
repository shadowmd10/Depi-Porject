package Gym.Management.facility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GymHall {

	private static int id = 0;
	private int gymHallID = 0;
	private String gymHallName = "";
	private List<Equipment> equipments;
	
	public GymHall(String gymHallName) 
	{
		this.setId();
		this.setGymHallID(this.getId());
		this.setGymHallName(gymHallName);
		setEquipments();
	}

	//////////Getters and Setters/////////
	public int getId() {
		return id;
	}

	public void setId() {
		GymHall.id ++;
	}
	
	public int getGymHallID() {
		return gymHallID;
	}

	private void setGymHallID(int gymID) {
		this.gymHallID = gymID;
	}

	public String getGymHallName() {
		return gymHallName;
	}

	public void setGymHallName(String gymHallName) {
		if(gymHallName == null || gymHallName == "" || gymHallName.length() <= 3) {
			System.out.println("Invalid Hall Name");
		}
		else {
			this.gymHallName = gymHallName;
		}
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	private void setEquipments() {
		this.equipments = new ArrayList<Equipment>(Arrays.asList());
	}
	
	//////////////////////////////////////////////////////////////////
	@Override
	public String toString() 
	{
		return "Hall No. : " + this.getGymHallID() + " : "+ this.getGymHallName();
	}

	
	
}
