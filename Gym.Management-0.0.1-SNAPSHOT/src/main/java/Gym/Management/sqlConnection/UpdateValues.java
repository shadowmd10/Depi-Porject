package Gym.Management.sqlConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;

import Gym.Management.people.Trainee;
import Gym.Management.people.Trainer;

public class UpdateValues {

	// Database connection details
	static String url = "jdbc:mysql://localhost:3306/gym";
	static String username = "Amr";
	static String password = "Amr_12saber";

	public static void updatePoints(Trainee trainee) 
	{
		String query = "UPDATE Trainee " +
					   "SET Points = ? " +
					   "WHERE PersonId = (SELECT Id FROM Person WHERE Email = ?)";


		// Establish connection to the database
		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			// Set the values for the placeholder
			preparedStatement.setInt(1, trainee.getPoints());          // Assuming newPoints is an integer
			preparedStatement.setString(2, trainee.getPersonEmail());           // Assuming email is a String variable

			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			System.out.println(rowsAffected + " row(s) updated.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void updateRating(Trainer trainer) 
	{
		String query = "UPDATE Trainer " +
					   "SET Rating = ? " +
					   "WHERE PersonId = (SELECT Id FROM Person WHERE Email = ?)";


		// Establish connection to the database
		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			// Set the values for the placeholder
			preparedStatement.setDouble(1, trainer.getRating());          
			preparedStatement.setString(2, trainer.getPersonEmail());           
			
			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			System.out.println(rowsAffected + " row(s) updated.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void renewSubscription(Trainee trainee) 
	{
		int endDays = 0;
		String query = "UPDATE Subscription " +
					   "SET Type = ? , StartDate = ?, EndDate = ? " +
					   "WHERE Id = (SELECT SubscriptionId FROM Trainee WHERE Email = ?)";

		switch (trainee.getSubcription().getSubscriptionType().toLowerCase()) {
		case "gold": {
			endDays = 90;
			break;
		}
		case "silver": {
			endDays = 60;
			break;
		}
		case "bronze": {
			endDays = 30;
			break;
		}
		default:{
			System.out.println("Invalid Value for Subscription Type");
		}
		}

		// Establish connection to the database
		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			// Set the values for the placeholder
			preparedStatement.setString(1, trainee.getSubcription().getSubscriptionType());          // Assuming newPoints is an integer
			preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));           // Assuming email is a String variable
			preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now().plusDays(endDays)));
			preparedStatement.setString(4, trainee.getPersonEmail());
			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			System.out.println(rowsAffected + " row(s) updated.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
