package Gym.Management.sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Gym.Management.facility.Equipment;
import Gym.Management.facility.GymHall;
import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;
import Gym.Management.people.Admin;
import Gym.Management.people.Manager;
import Gym.Management.people.Trainee;
import Gym.Management.people.Trainer;

public class SQLConnection {

	static String url = "jdbc:mysql://localhost:3306/";
	static String user = "Amr";
	static String password = "Amr_12saber";
    
    // Database name to be created
	static String databaseName = "Gym";

    // SQL statement to create a new database
	static String createDatabaseSQL = "CREATE DATABASE " + databaseName;

    // Connection and Statement objects
	static Connection connection = null;
	static Statement statement = null;
    
/////////////////////////////////////////////////////////////////////////////
	
	public static void deleteDB() 
    {
		try {
    		connection = DriverManager.getConnection(url, user, password);
    		// Create a Statement object
            statement = connection.createStatement();
            statement.executeUpdate("DROP DATABASE " + databaseName);
            
            //Close the connection
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            System.out.println("Deleted Successfully");
    	}
    	catch (SQLException e) {
    		System.out.println("Can't connect to the Database" + e.getMessage());
    	}
    }
	
    public static void createDB() 
    {
    	try {
    		connection = DriverManager.getConnection(url, user, password);
    		// Create a Statement object
            statement = connection.createStatement();
            statement.executeUpdate(createDatabaseSQL);
            
            Statement statementGlobal = connection.createStatement();
            statementGlobal.executeUpdate("SET GLOBAL sql_mode = ''");
            
            //Close the connection
            if (statement != null) statement.close();
            if (statementGlobal != null) statementGlobal.close();
            if (connection != null) connection.close();
    	}
    	catch (SQLException e) {
    		System.out.println("Can't connect to the Database" + e.getMessage());
    	}
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public static void createTables() 
    {
        // SQL statements to create tables
        String createPersonTable = "CREATE TABLE Person ("
                + "Id INT PRIMARY KEY  AUTO_INCREMENT, "
                + "Name VARCHAR(255) NOT NULL, "
                + "Age INT, "
                + "Email VARCHAR(255), "
                + "PhoneNumber VARCHAR(20), "
                + "Password VARCHAR(255)"
                + ") AUTO_INCREMENT = 1000;";

        String createTrainerTable = "CREATE TABLE Trainer ("
        		+ "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "PersonId INT, "
                + "WorkingHours INT, "
                + "GymHallId INT, "
                + "Rating DECIMAL(3, 2), "
                + "Salary DECIMAL(10, 2), "
                + "FOREIGN KEY (PersonId) REFERENCES Person(Id) ON DELETE CASCADE, "
                + "FOREIGN KEY (GymHallId) REFERENCES GymHall(Id)"
                + ");";

        String createTraineeTable = "CREATE TABLE Trainee ("
        		+ "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "PersonId INT, "
                + "SubscriptionId INT, "
                + "ExercisePlanId INT, "
                + "Points INT, "
                + "FOREIGN KEY (PersonId) REFERENCES Person(Id) ON DELETE CASCADE, "
                + "FOREIGN KEY (SubscriptionId) REFERENCES Subscription(Id) ON DELETE CASCADE, "
                + "FOREIGN KEY (ExercisePlanId) REFERENCES ExercisePlan(Id) ON DELETE CASCADE"
                + ");";

        String createAdminTable = "CREATE TABLE Admin ("
        		+ "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "PersonId INT, "
                + "Role VARCHAR(255) NOT NULL, "
                + "FOREIGN KEY (PersonId) REFERENCES Person(Id) ON DELETE CASCADE"
                + ");";

        String createManagerTable = "CREATE TABLE Manager ("
        		+ "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "PersonId INT, "
                + "Role VARCHAR(255) NOT NULL, "
                + "FOREIGN KEY (PersonId) REFERENCES Person(Id) ON DELETE CASCADE"
                + ");";

        String createGymHallTable = "CREATE TABLE GymHall ("
                + "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "Name VARCHAR(255)"
                + ");";
        
        String createEquipmentTable = "CREATE TABLE Equipment ("
                + "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "Name VARCHAR(255), "
                + "Type VARCHAR(255)"
                + ");";


        String createGymHallEquipmentTable = "CREATE TABLE GymHall_Equipment ("
                + "GymHallId INT, "
                + "EquipmentId INT, "
                + "PRIMARY KEY (GymHallId, EquipmentId), "
                + "FOREIGN KEY (GymHallId) REFERENCES GymHall(Id) ON DELETE CASCADE, "
                + "FOREIGN KEY (EquipmentId) REFERENCES Equipment(Id) ON DELETE CASCADE"
                + ");";

        String createExercisePlanTable = "CREATE TABLE ExercisePlan ("
                + "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "Steps TEXT, "
                + "Duration INT"
                + ");";

        String createSubscriptionTable = "CREATE TABLE Subscription ("
                + "Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + "Type VARCHAR(255), "
                + "StartDate DATE, "
                + "EndDate DATE, "
                + "Price DECIMAL(10, 2), "
                + "Discount DECIMAL(5, 2)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url+databaseName, user, password);
             Statement stmt = conn.createStatement()) {

            // Execute SQL statements
        	stmt.executeUpdate(createGymHallTable);
        	stmt.executeUpdate(createExercisePlanTable);
            stmt.executeUpdate(createSubscriptionTable);
            stmt.executeUpdate(createPersonTable);
            stmt.executeUpdate(createTrainerTable);
            stmt.executeUpdate(createTraineeTable);
            stmt.executeUpdate(createAdminTable);
            stmt.executeUpdate(createManagerTable);
            stmt.executeUpdate(createEquipmentTable);
            stmt.executeUpdate(createGymHallEquipmentTable);
            

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            System.out.println("Connection Error : " + e.getMessage());
        }
    }
    
/////////////////////////////////////////////////////////////////////////////////////////
    
    public static void insertTrainer(Trainer trainer) 
    {
    	int foreignID = 0;
    	 String sqlPerson = "INSERT INTO Person (Name, Age, Email, PhoneNumber, Password) VALUES (?, ?, ?, ?, ?)";
         String sqlTrainer = "INSERT INTO Trainer (PersonId, WorkingHours, GymHallId, Rating, Salary) VALUES (?, ?, ?, ?, ?)";
         
         String sqlSearchID = "SELECT Id FROM Person Where Email = ?";
         try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
             conn.setAutoCommit(false);  // Start transaction
             
             try (PreparedStatement psPerson = conn.prepareStatement(sqlPerson);
                  PreparedStatement psTrainer = conn.prepareStatement(sqlTrainer);
            	  PreparedStatement psID = conn.prepareStatement(sqlSearchID)) {
                  
                 // Insert into Person table
            	 //psPerson.setInt(1, trainer.getPersonID());
                 psPerson.setString(1, trainer.getPersonName());
                 psPerson.setInt(2, trainer.getPersonAge());
                 psPerson.setString(3, trainer.getPersonEmail());
                 psPerson.setString(4, trainer.getPersonPhone());
                 psPerson.setString(5, trainer.getPersonPassword());
                 psPerson.executeUpdate();
                 
                 psID.setString(1, trainer.getPersonEmail());
                 ResultSet resultSet = psID.executeQuery();
                 if(resultSet.next()) {
                	 foreignID = resultSet.getInt("Id");
                 }
                 // Insert into Trainer table
                 psTrainer.setInt(1, foreignID);
                 psTrainer.setInt(2, trainer.getWorkingHours());
                 psTrainer.setInt(3, trainer.getAssignedHall());
                 psTrainer.setDouble(4, trainer.getRating());
                 psTrainer.setDouble(5, trainer.getSalary());
                 psTrainer.executeUpdate();
                 
                 conn.commit();  // Commit transaction
                 System.out.println("Trainer Inserted Successfuly");
             } catch (SQLException e) {
                 conn.rollback();  // Roll back if there's an error
                 System.out.println("Error Ocured : " + e.getMessage());
             }
         } catch (SQLException e) {
        	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
         }
    }
    //*************************************************************************************//
    
    public static void insertTrainee(Trainee trainee) 
    {
    	int foreignID = 0;
    	String sqlPerson = "INSERT INTO Person (Name, Age, Email, PhoneNumber, Password) VALUES (?, ?, ?, ?, ?)";
        String sqlTrainer = "INSERT INTO Trainee (PersonId, SubscriptionId, ExercisePlanId, Points) VALUES (?, ?, ?, ?)";
        
        String sqlSearchID = "SELECT Id FROM Person Where Email = ?";
        
        try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psPerson = conn.prepareStatement(sqlPerson);
                 PreparedStatement psTrainer = conn.prepareStatement(sqlTrainer);
            	 PreparedStatement psID = conn.prepareStatement(sqlSearchID)) {
                 
                // Insert into Person table
           	 	//psPerson.setInt(1, trainee.getPersonID());
                psPerson.setString(1, trainee.getPersonName());
                psPerson.setInt(2, trainee.getPersonAge());
                psPerson.setString(3, trainee.getPersonEmail());
                psPerson.setString(4, trainee.getPersonPhone());
                psPerson.setString(5, trainee.getPersonPassword());
                psPerson.executeUpdate();
                
                psID.setString(1, trainee.getPersonEmail());
                ResultSet resultSet = psID.executeQuery();
                if(resultSet.next()) {
               	 foreignID = resultSet.getInt("Id");
                }
                
                //"SubscriptionId INT, "
                //+ "ExercisePlanId INT, "
                //+ "Points INT, "
                // Insert into Trainer table
                psTrainer.setInt(1, foreignID);
                psTrainer.setInt(2, trainee.getSubcription().getSubscriptionID());
                psTrainer.setInt(3, trainee.getPlan().getEcercisePlanID());
                psTrainer.setInt(4, trainee.getPoints());
                psTrainer.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("Trainee Inserted Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
    //*************************************************************************************//

    public static void insertAdmin(Admin admin) 
    {
    	
    	 int foreignID = 0;
    	 String sqlPerson = "INSERT INTO Person (Name, Age, Email, PhoneNumber, Password) VALUES (?, ?, ?, ?, ?)";
         String sqlAdmin = "INSERT INTO Admin (PersonId, Role) VALUES (?, ?)";
         
         String sqlSearchID = "SELECT Id FROM Person Where Email = ?";
         
         try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
             conn.setAutoCommit(false);  // Start transaction
             
             try (PreparedStatement psPerson = conn.prepareStatement(sqlPerson);
                  PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
            	  PreparedStatement psID = conn.prepareStatement(sqlSearchID)) {
                  
                 // Insert into Person table
            	 //psPerson.setInt(1, admin.getPersonID());
                 psPerson.setString(1, admin.getPersonName());
                 psPerson.setInt(2, admin.getPersonAge());
                 psPerson.setString(3, admin.getPersonEmail());
                 psPerson.setString(4, admin.getPersonPhone());
                 psPerson.setString(5, admin.getPersonPassword());
                 psPerson.executeUpdate();
                 
                 psID.setString(1, admin.getPersonEmail());
                 ResultSet resultSet = psID.executeQuery();
                 if(resultSet.next()) {
                   	 foreignID = resultSet.getInt("Id");
                    }
                 
                 // Insert into Trainer table
                 psAdmin.setInt(1, foreignID);
                 psAdmin.setString(2, "Admin");
                 psAdmin.executeUpdate();
                 
                 conn.commit();  // Commit transaction
                 System.out.println("Admin Inserted Successfuly");
             } catch (SQLException e) {
                 conn.rollback();  // Roll back if there's an error
                 System.out.println("Error Ocured : " + e.getMessage());
             }
         } catch (SQLException e) {
        	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
         }
    }
    //*************************************************************************************//
    
    public static void insertManager(Manager manager) 
    {
    	int foreignID = 0;
    	 String sqlPerson = "INSERT INTO Person (Name, Age, Email, PhoneNumber, Password) VALUES (?, ?, ?, ?, ?)";
         //String sqlAdmin = "INSERT INTO Admin (Id) VALUES (?)";
         String sqlManager = "INSERT INTO Manager (PersonId, Role) VALUES (?, ?)";
         
         String sqlSearchID = "SELECT Id FROM Person Where Email = ?";
         
         try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
             conn.setAutoCommit(false);  // Start transaction
             
             try (PreparedStatement psPerson = conn.prepareStatement(sqlPerson);
                  PreparedStatement psManager = conn.prepareStatement(sqlManager);
            	  PreparedStatement psID = conn.prepareStatement(sqlSearchID)) {
                  
                 // Insert into Person table
            	 //psPerson.setInt(1, manager.getPersonID());
                 psPerson.setString(1, manager.getPersonName());
                 psPerson.setInt(2, manager.getPersonAge());
                 psPerson.setString(3, manager.getPersonEmail());
                 psPerson.setString(4, manager.getPersonPhone());
                 psPerson.setString(5, manager.getPersonPassword());
                 psPerson.executeUpdate();
                 
                 psID.setString(1, manager.getPersonEmail());
                 ResultSet resultSet = psID.executeQuery();
                 if(resultSet.next()) {
                   	 foreignID = resultSet.getInt("Id");
                    }
                 
                 // Insert into Manager table
                 psManager.setInt(1, foreignID);
                 psManager.setString(2, "Manager");
                 psManager.executeUpdate();
                 
                 conn.commit();  // Commit transaction
                 System.out.println("Manager Inserted Successfuly");
             } catch (SQLException e) {
                 conn.rollback();  // Roll back if there's an error
                 System.out.println("Error Ocured : " + e.getMessage());
             }
         } catch (SQLException e) {
        	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
         }
    }
    //*************************************************************************************//
    
    //"Id INT PRIMARY KEY, "
    //+ "Name VARCHAR(255)"
    // + ");";
    public static void insertGymHall(GymHall hall) 
    {
    	String sqlGymHall = "INSERT INTO GymHall (Name) VALUES (?)";
    	try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psGymHall = conn.prepareStatement(sqlGymHall)) {
                 
                // Insert into GymHall table
            	//psGymHall.setInt(1, hall.getGymHallID());
            	psGymHall.setString(1, hall.getGymHallName());
            	psGymHall.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("GymHall Inserted Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
  //*************************************************************************************//
    
    //"Id INT PRIMARY KEY, "
    //+ "Name VARCHAR(255), "
    //+ "Type VARCHAR(255)"
    public static void insertEquipment(Equipment eq) 
    {
    	String sqlEquipment = "INSERT INTO Equipment (Name, Type) VALUES (?, ?)";
    	try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psEquipment = conn.prepareStatement(sqlEquipment)) {
                 
                // Insert into Person table
            	//psEquipment.setInt(1, eq.getEquipmentID());
            	psEquipment.setString(1, eq.getEquipmentName());
            	psEquipment.setString(2, eq.getEquipmentType());
            	psEquipment.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("Equipment Inserted Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
  //*************************************************************************************//
    //"GymHallId INT, "
    //+ "EquipmentId INT, "
    
    public static void addEqToHall(Equipment eq, int hallID) 
    {
    	String sqlHall_Equipment = "INSERT INTO GymHall_Equipment (GymHallId, EquipmentId) VALUES (?, ?)";
    	try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psHall_Equipment = conn.prepareStatement(sqlHall_Equipment)) {
                 
                // Insert into Person table
            	psHall_Equipment.setInt(1, hallID);
            	psHall_Equipment.setInt(2, eq.getEquipmentID());
            	psHall_Equipment.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("Equipment Added Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
  //*************************************************************************************//
    
//    String createSubscriptionTable = "CREATE TABLE Subscription ("
//            + "Id INT PRIMARY KEY, "
//            + "Type VARCHAR(255), "
//            + "StartDate DATE, "
//            + "EndDate DATE, "
//            + "Price DECIMAL(10, 2), "
//            + "Discount DECIMAL(5, 2)"
//            + ");";
    
    public static void insertSubscription(Subscription sub) 
    {
    	String sqlSubscription = "INSERT INTO Subscription (Type, StartDate, EndDate, Price, Discount) VALUES (?, ?, ?, ?, ?)";
    	try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psSubscription = conn.prepareStatement(sqlSubscription)) {
                
            	// Converting LocalDate to Date
//            	String startDate = sub.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//            	String endDate = sub.getEndtDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            	
                // Insert into Person table
            	//psSubscription.setInt(1, sub.getSubscriptionID());
            	psSubscription.setString(1, sub.getSubscriptionType());
            	psSubscription.setDate(2, java.sql.Date.valueOf(sub.getStartDate()));
            	psSubscription.setDate(3, java.sql.Date.valueOf(sub.getEndtDate()));
            	psSubscription.setDouble(4, sub.getPrice());
            	psSubscription.setDouble(5, sub.getDiscount());
            	psSubscription.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("Subscripion Inserted Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
  //*************************************************************************************//
    
//  String createExercisePlanTable = "CREATE TABLE ExercisePlan ("
//  + "Id INT PRIMARY KEY, "
//  + "Steps TEXT, "
//  + "Duration INT"
//  + ");";
    
    public static void insertExercisePlan(ExercisePlan exPlan) 
    {
    	String sqlExercisePlan = "INSERT INTO ExercisePlan (Steps, Duration) VALUES (?, ?)";
    	try (Connection conn = DriverManager.getConnection(url+databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction
            
            try (PreparedStatement psExercisePlan = conn.prepareStatement(sqlExercisePlan)) {
                
            	// Converting List of Steps (Strings) into one String
            	String steps = "";
            	for(String step : exPlan.getExerciseSteps()) {
            		steps += step + "\t";
            	}
            	
                // Insert into Person table
            	//psExercisePlan.setInt(1, exPlan.getEcercisePlanID());
            	psExercisePlan.setString(1, steps);
            	psExercisePlan.setInt(2, exPlan.getExerciseDuration());
            	psExercisePlan.executeUpdate();
                
                conn.commit();  // Commit transaction
                System.out.println("Exersice Plan Inserted Successfuly");
            } catch (SQLException e) {
                conn.rollback();  // Roll back if there's an error
                System.out.println("Error Ocured : " + e.getMessage());
            }
        } catch (SQLException e) {
       	 System.out.println("Couldn't connect to the Database : " + e.getMessage());
        }
    }
    
  //*************************************************************************************//
    public static void removeTrainer(int trainerId) {
        String deleteTrainerSql = "DELETE FROM Trainer WHERE PersonId = ?";

        try (Connection conn = DriverManager.getConnection(url + databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement trainerStatement = conn.prepareStatement(deleteTrainerSql)) {
                trainerStatement.setInt(1, trainerId);
                int rowsAffected = trainerStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Trainer with ID " + trainerId + " was deleted successfully.");
                    conn.commit();  // Commit transaction
                } else {
                    System.out.println("No trainer found with ID " + trainerId);
                    conn.rollback();  // Rollback transaction if no rows affected
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while deleting trainer: " + e.getMessage());
                conn.rollback();  // Rollback transaction in case of error
            }

        } catch (SQLException e1) {
            System.out.println("Error occurred while connecting to the database: " + e1.getMessage());
        }
    }
    //*************************************************************************************//

    
    public static void removeTrainee(int traineeId) {
        String deleteTrainerSql = "DELETE FROM Trainee WHERE PersonId = ?";

        try (Connection conn = DriverManager.getConnection(url + databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement trainerStatement = conn.prepareStatement(deleteTrainerSql)) {
                trainerStatement.setInt(1, traineeId);
                int rowsAffected = trainerStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Trainee with ID " + traineeId + " was deleted successfully.");
                    conn.commit();  // Commit transaction
                } else {
                    System.out.println("No trainee found with ID " + traineeId);
                    conn.rollback();  // Rollback transaction if no rows affected
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while deleting trainee: " + e.getMessage());
                conn.rollback();  // Rollback transaction in case of error
            }

        } catch (SQLException e1) {
            System.out.println("Error occurred while connecting to the database: " + e1.getMessage());
        }
    }
    //*************************************************************************************//

    public static void removeHall(int hallId) {
        String deleteTrainerSql = "DELETE FROM GymHall WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection(url + databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement trainerStatement = conn.prepareStatement(deleteTrainerSql)) {
                trainerStatement.setInt(1, hallId);
                int rowsAffected = trainerStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Hall with ID " + hallId + " was deleted successfully.");
                    conn.commit();  // Commit transaction
                } else {
                    System.out.println("No Hall found with ID " + hallId);
                    conn.rollback();  // Rollback transaction if no rows affected
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while deleting Hall: " + e.getMessage());
                conn.rollback();  // Rollback transaction in case of error
            }

        } catch (SQLException e1) {
            System.out.println("Error occurred while connecting to the database: " + e1.getMessage());
        }
    }
    //*************************************************************************************//
    
    public static void removeEquipment(int eqId) {
        String deleteTrainerSql = "DELETE FROM Equipment WHERE Id = ?";

        try (Connection conn = DriverManager.getConnection(url + databaseName, user, password)) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement trainerStatement = conn.prepareStatement(deleteTrainerSql)) {
                trainerStatement.setInt(1, eqId);
                int rowsAffected = trainerStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Equipment with ID " + eqId + " was deleted successfully.");
                    conn.commit();  // Commit transaction
                } else {
                    System.out.println("No Equipment found with ID " + eqId);
                    conn.rollback();  // Rollback transaction if no rows affected
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while deleting Equipment: " + e.getMessage());
                conn.rollback();  // Rollback transaction in case of error
            }

        } catch (SQLException e1) {
            System.out.println("Error occurred while connecting to the database: " + e1.getMessage());
        }
    }
    //*************************************************************************************//

}
