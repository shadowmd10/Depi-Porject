package Gym.Management.sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;
import Gym.Management.people.Trainee;
import Gym.Management.people.Trainer;

public class SearchData {
	
	// Database connection details
	static String url = "jdbc:mysql://localhost:3306/gym";
    static String username = "Amr";
    static String password = "Amr_12saber";

    //Method to search for a Trainee using his/her Email
    public static Trainee searchTrainee(String traineeEmail) 
    {
    	String tname = "";
    	int tage = 0;
    	String tphone = "";
    	String tpassword = "";
    	int sID = 0;
    	String sType = "";
    	LocalDate sStartDate = null;
    	LocalDate sEndDate = null;
    	int exID = 0;
    	int exDuration = 0;
    	int tpoints = 0;
    	
    	// SQL SELECT query
        String query = "SELECT Person.Name, Person.Age, Person.PhoneNumber, Person.Password,"
        		+ " Trainee.SubscriptionId, Trainee.ExercisePlanId, Trainee.Points "
        		+ "FROM Person INNER JOIN Trainee ON Person.Id = Trainee.PersonId WHERE Person.Email = ?";
        
        
        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);
            
            // Create a statement to execute the query
            Statement statement = connection.createStatement();
            
            // Execute the query and store the result
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, traineeEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Iterate over the result set and print the results
            while (resultSet.next()) {
                tname = resultSet.getString("Name");
                tage = resultSet.getInt("Age");
                tphone = resultSet.getString("PhoneNumber");
                tpassword = resultSet.getString("Password");
                sID = resultSet.getInt("SubscriptionId");
                exID = resultSet.getInt("ExercisePlanId");
                tpoints = resultSet.getInt("Points");
            }
            String querySub = "SELECT Subscription.Type, Subscription.StartDate, Subscription.EndDate"
            		+ " FROM Subscription WHERE Subscription.Id = ?";
            
            String queryExplan = "SELECT ExercisePlan.Duration"
            		+ " FROM ExercisePlan WHERE ExercisePlan.Id = ?";
            
            preparedStatement = connection.prepareStatement(querySub);
            preparedStatement.setInt(1, sID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	sType = resultSet.getString("Type");
            	sStartDate = resultSet.getDate("StartDate").toLocalDate();
            	sEndDate = resultSet.getDate("EndDate").toLocalDate();
            }
            
            preparedStatement = connection.prepareStatement(queryExplan);
            preparedStatement.setInt(1, exID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	exDuration = resultSet.getInt("Duration");
            }
            Subscription sub = new Subscription(sType);
            sub.setStartDate(sStartDate);
            sub.setEndtDate(sEndDate);
            ExercisePlan plan = new ExercisePlan(exDuration);
            Trainee t = new Trainee(tname, tage, traineeEmail, tphone, tpassword, sub, plan);
            t.setPoints(tpoints);
            // Close the connection and other resources
            resultSet.close();
            statement.close();
            connection.close();
            return t;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return null;
        }
    }
  //*****************************************************************************************************************//
    //Method to display all Trainees
    public static List<Trainee> getAllTraineesAsTable() {
    	
    	List<Trainee> trainees = new ArrayList<>();
        String tname = "";
        int tage = 0;
        String temail = "";
        String tphone = "";
        String tpassword = "";
        int sID = 0;
        String sType = "";
        LocalDate sStartDate = null;
        LocalDate sEndDate = null;
        int exID = 0;
        int exDuration = 0;
        int tpoints = 0;

        // SQL SELECT query to fetch all trainees
        String query = "SELECT Person.Name, Person.Age, Person.Email, Person.PhoneNumber, Person.Password,"
                + " Trainee.SubscriptionId, Trainee.ExercisePlanId, Trainee.Points "
                + "FROM Person INNER JOIN Trainee ON Person.Id = Trainee.PersonId";

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement to execute the query
            Statement statement = connection.createStatement();

            // Execute the query and store the result
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.printf("%-20s %-5s %-25s %-15s %-15s %-15s %-15s %-10s %-10s\n", 
                "Name", "Age", "Email", "Phone", "Subscription", "Sub. Start", "Sub. End", "Exercise", "Points");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

            // Iterate over the result set and process each trainee
            while (resultSet.next()) {
                tname = resultSet.getString("Name");
                tage = resultSet.getInt("Age");
                temail = resultSet.getString("Email");
                tphone = resultSet.getString("PhoneNumber");
                tpassword = resultSet.getString("Password");
                sID = resultSet.getInt("SubscriptionId");
                exID = resultSet.getInt("ExercisePlanId");
                tpoints = resultSet.getInt("Points");

                // Fetch subscription details
                String querySub = "SELECT Subscription.Type, Subscription.StartDate, Subscription.EndDate"
                        + " FROM Subscription WHERE Subscription.Id = ?";
                preparedStatement = connection.prepareStatement(querySub);
                preparedStatement.setInt(1, sID);
                ResultSet resultSub = preparedStatement.executeQuery();
                if (resultSub.next()) {
                    sType = resultSub.getString("Type");
                    sStartDate = resultSub.getDate("StartDate").toLocalDate();
                    sEndDate = resultSub.getDate("EndDate").toLocalDate();
                }

                // Fetch exercise plan details
                String queryExplan = "SELECT ExercisePlan.Duration FROM ExercisePlan WHERE ExercisePlan.Id = ?";
                preparedStatement = connection.prepareStatement(queryExplan);
                preparedStatement.setInt(1, exID);
                ResultSet resultEx = preparedStatement.executeQuery();
                if (resultEx.next()) {
                    exDuration = resultEx.getInt("Duration");
                }

                // Create the objects for Subscription and ExercisePlan
                Subscription sub = new Subscription(sType);
                sub.setStartDate(sStartDate);
                sub.setEndtDate(sEndDate);
                ExercisePlan plan = new ExercisePlan(exDuration);
                Trainee t = new Trainee(tname, tage, temail, tphone, tpassword, sub, plan);
                t.setPoints(tpoints);

                trainees.add(t);
                // Print the details of each trainee in table format
                System.out.printf("%-20s %-5d %-25s %-15s %-15s %-15s %-15s %-10d %-10d\n",
                    t.getPersonName(), t.getPersonAge(), t.getPersonEmail(), t.getPersonPhone(), 
                    t.getSubcription().getSubscriptionType(), t.getSubcription().getStartDate(),
                    t.getSubcription().getEndtDate(), t.getPlan().getExerciseDuration(), t.getPoints());
            }

            // Close the connection and other resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return trainees;
    }
    
    //*****************************************************************************************************************//
  //Method to search for a Trainer using his/her Email
    public static Trainer searchTrainer(String trainerEmail) 
    {
    	String tname = "";
    	int tage = 0;
    	double tsalary = 0.0;
    	String tphone = "";
    	String tpassword = "";
    	int tWH = 0;
    	int tAssignedHall = 0;
    	// SQL SELECT query
        String query = "SELECT Person.Name, Person.Age, Person.PhoneNumber, Person.Password,"
        		+ " Trainer.Salary, Trainer.WorkingHours, Trainer.GymHallId FROM Person "
        		+ "INNER JOIN Trainer ON Person.Id = Trainer.PersonId WHERE Person.Email = ?";
        
        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);
            
            // Create a statement to execute the query
            Statement statement = connection.createStatement();
            
            // Execute the query and store the result
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, trainerEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Iterate over the result set and print the results
            while (resultSet.next()) {
            	tname = resultSet.getString("Name");
            	tage = resultSet.getInt("Age");
            	tphone = resultSet.getString("PhoneNumber");
            	tpassword = resultSet.getString("Password");
            	tsalary = resultSet.getDouble("Salary");
            	tWH = resultSet.getInt("WorkingHours");
            	tAssignedHall = resultSet.getInt("GymHallId");
            }
            Trainer t = new Trainer(tname, tage, tsalary, trainerEmail, tphone, tpassword, tWH, tAssignedHall);
            // Close the connection and other resources
            resultSet.close();
            statement.close();
            connection.close();
            return t;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
  //*****************************************************************************************************************//
    //Method to display all Trainers
    public static void getAllTrainersAsTable() {
        String tname = "";
        int tage = 0;
        double tsalary = 0.0;
        String temail = "";
        String tphone = "";
//        String tpassword = "";
        int tWH = 0;
        int tAssignedHall = 0;

        // SQL SELECT query to fetch all trainers
        String query = "SELECT Person.Name, Person.Age, Person.Email, Person.PhoneNumber, Person.Password,"
                + " Trainer.Salary, Trainer.WorkingHours, Trainer.GymHallId FROM Person "
                + "INNER JOIN Trainer ON Person.Id = Trainer.PersonId";

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement to execute the query
            Statement statement = connection.createStatement();

            // Execute the query and store the result
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.printf("%-20s %-5s %-25s %-15s %-10s %-15s %-15s\n",
                    "Name", "Age", "Email", "Phone", "Salary", "Working Hours", "Gym Hall ID");
            System.out.println("-------------------------------------------------------------------------------");

            // Iterate over the result set and process each trainer
            while (resultSet.next()) {
                tname = resultSet.getString("Name");
                tage = resultSet.getInt("Age");
                temail = resultSet.getString("Email");
                tphone = resultSet.getString("PhoneNumber");
//                tpassword = resultSet.getString("Password");
                tsalary = resultSet.getDouble("Salary");
                tWH = resultSet.getInt("WorkingHours");
                tAssignedHall = resultSet.getInt("GymHallId");

                // Print the details of each trainer in table format
                System.out.printf("%-20s %-5d %-25s %-15s %-10.2f %-15d %-15d\n",
                    tname, tage, temail, tphone, tsalary, tWH, tAssignedHall);
            }

            // Close the connection and other resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //*****************************************************************************************************************//
    //Method used to check if Admin exists or not
    public static boolean checkAdmin(String adminEmail) 
    {
    	String query = "SELECT 1 FROM Person p JOIN Admin a ON p.Id = a.PersonId WHERE p.email = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the email parameter in the query
            preparedStatement.setString(1, adminEmail);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result exists
            if (resultSet.next()) {
                //System.out.println("The email exists in the database.");
                return true;
            } else {
                //System.out.println("The email does not exist in the database.");
                return false;
            }

        } catch (SQLException e) {
        	System.out.println("Error occured : " + e.getMessage());
            return false;
        }
    }
    //*****************************************************************************************************************//
    //Method to check the password of the admin
    public static boolean checkAdminPass(String adminEmail, String adminPass) 
    {
    	String query = "SELECT Password FROM Person INNER JOIN Admin ON Person.Id = Admin.PersonId WHERE Person.email = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the email parameter in the query
            preparedStatement.setString(1, adminEmail);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result exists
            if (resultSet.next()) {
                //System.out.println("The email exists in the database and password = " + resultSet.getString("Password"));
                if(resultSet.getString("Password").equals(adminPass)) 
                {
                	return true;
                }
                else {
                	return false;
                }
            } else {
                //System.out.println("The email does not exist in the database.");
                return false;
            }

        } catch (SQLException e) {
        	System.out.println("Error occured : " + e.getMessage());
            return false;
        }
    }
    //*****************************************************************************************************************//

    //Main Method to check the running of the above methods
	public static void main(String[] args) {
		
		
		System.out.println(SearchData.checkAdmin("Admin101@gmail.com"));
		System.out.println(SearchData.checkAdminPass("Admin101@gmail.com", "Test123456"));
        
        
	}

}
