package Gym.Management.people;

public abstract class Person {

	public static int id = 0;
	private int personID = 0;
	private String personName = "";
	private int personAge = 0;
	private String personEmail = ""; 
	private String personPhone = "";
	private String personPassword = "";
	
	//////////////////Constructors/////////////
	public Person(String personName, int personAge) 
	{
		this.setId();
		this.setPersonID(this.getId());
		this.setPersonName(personName);
		this.setPersonAge(personAge);
	}
	
	public Person(String personName, int personAge, String personEmail, String personPhone, String personPassword) 
	{
		this(personName, personAge);
		this.setPersonEmail(personEmail);
		this.setPersonPhone(personPhone);
		this.setPersonPassword(personPassword);
	}

	
	//////////Getters and Setters/////////////
	
	public int getId() {
		return id;
	}

	public void setId() {
		Person.id ++;
	}
	
	public int getPersonID() {
		return personID;
	}

	private void setPersonID(int currentID) {
		this.personID = currentID;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		if(personName == null || personName == "" || personName.length() <= 2) {
			System.out.println("Invalid Name");
		}
		else {
			this.personName = personName;
		}
	}

	public int getPersonAge() {
		return personAge;
	}

	public void setPersonAge(int personAge) {
		if(personAge <= 10 || personAge >= 90) {
			System.out.println("Invalid Age");
		}
		else {
			this.personAge = personAge;
		}
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		if(personEmail == null || personEmail == "" || personEmail.length() <= 12 || !(personEmail.contains("@"))) {
			System.out.println("Invalid Email");
		}
		else {
			this.personEmail = personEmail;
		}
	}

	public String getPersonPhone() {
		return personPhone;
	}

	public void setPersonPhone(String personPhone) {
		if(personPhone == null || personPhone == "" || personPhone.length() < 11) {
			System.out.println("Invalid Phone Number");
		}
		else {
			try{
				Integer.parseInt(personPhone);
				this.personPhone = personPhone;
			}catch (Exception e) {
				System.out.println("Invalid Phone Number : " + e.getMessage());
			}
		}
	}
	
	public String getPersonPassword() {
		return personPassword;
	}


	public void setPersonPassword(String personPassword) {
		if(personPassword == null || personPassword == "" || personPassword.length() < 6) {
			System.out.println("Invalid Password");
		}
		else {
			this.personPassword = personPassword;
		}
	}
	
	///////////////////////////////////////////////
	
	@Override
	public String toString() 
	{
		return "Mr/Ms " + this.getPersonName() + " : " + this.getPersonAge() + " years old. His/Her Email address : " + this.getPersonEmail();
	}


	
}
