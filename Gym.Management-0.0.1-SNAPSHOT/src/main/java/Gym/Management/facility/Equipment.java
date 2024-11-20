package Gym.Management.facility;

public class Equipment {

	private static int id = 0;
	private int equipmentID = 0;
	private String equipmentName = "";
	private String equipmentType = "";
	
	public Equipment(String equipmentName, String equipmentType) 
	{
		this.setId();
		setEquipmentID(this.getId());
		this.setEquipmentName(equipmentName);
		this.setEquipmentType(equipmentType);
	}

	
	/////////Getters and Setters//////////////
	public int getId() {
		return id;
	}


	public void setId() {
		Equipment.id ++;
	}
	
	public int getEquipmentID() {
		return equipmentID;
	}

	private void setEquipmentID(int eqID) {
		this.equipmentID = eqID;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		if(equipmentName == null || equipmentName == "" || equipmentName.length() <= 3) {
			System.out.println("Invalid Equipment Name");
		}
		else {
			this.equipmentName = equipmentName;
		}
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		if(equipmentType == null || equipmentType == "" || equipmentType.length() <= 3) {
			System.out.println("Invalid Equipment Name");
		}
		else {
			this.equipmentType = equipmentType;
		}
	}


	
	
	
	
}
