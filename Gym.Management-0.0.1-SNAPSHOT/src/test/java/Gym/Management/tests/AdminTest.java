package Gym.Management.tests;

import static org.mockito.Mockito.*;

import Gym.Management.facility.Equipment;
import Gym.Management.facility.GymHall;
import Gym.Management.mainRun.Logs;
import Gym.Management.people.Admin;
import Gym.Management.people.Person;
import Gym.Management.people.Trainer;
import Gym.Management.people.Trainee;
import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;
import Gym.Management.sqlConnection.SQLConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;

@TestInstance(Lifecycle.PER_CLASS)
public class AdminTest {
    
    private Admin admin;
    private SQLConnection mockSQLConnection;

    @BeforeAll
    public void setUp() {
        mockSQLConnection = mock(SQLConnection.class); 
        admin = new Admin("John Doe", 30, "john@example.com", "01234567892", "password", mockSQLConnection);
    }

    @AfterEach
    public void tearDown() {
        
    	
        Person.id = 0;
    }

    @Test
    public void testAddHall() {
        GymHall hall = new GymHall("Main Hall");

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.addHall(hall);

            mockedSQL.verify(() -> SQLConnection.insertGymHall(hall));
            mockedLogs.verify(() -> Logs.addLogs(admin, hall.getGymHallName(), "insert"));
        }
    }

    @Test
    public void testRemoveHall_ValidID() {
        int hallID = 1;

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeHall(hallID);

            mockedSQL.verify(() -> SQLConnection.removeHall(hallID));
            mockedLogs.verify(() -> Logs.addLogs(admin, hallID, "delete"));
        }
    }

    @Test
    public void testRemoveHall_InvalidID() {
        int hallID = -1;

        try (MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeHall(hallID);

            mockedSQL.verify(() -> SQLConnection.removeHall(hallID), never()); // Should not be called for invalid ID
        }
    }

    @Test
    public void testAddEquipment() {
        Equipment equipment = new Equipment("Treadmill", "Cardio");
        int hallID = 1;

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.addEquipment(hallID, equipment);

            mockedSQL.verify(() -> SQLConnection.insertEquipment(equipment));
            mockedSQL.verify(() -> SQLConnection.addEqToHall(equipment, hallID));
            mockedLogs.verify(() -> Logs.addLogs(admin, equipment.getEquipmentName(), "add to " + hallID));
        }
    }

    @Test
    public void testRemoveEquipment() {
        int equipmentID = 1;

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeEquipment(equipmentID);

            mockedSQL.verify(() -> SQLConnection.removeEquipment(equipmentID));
            mockedLogs.verify(() -> Logs.addLogs(admin, equipmentID, "remove"));
        }
    }

    @Test
    public void testAddTrainer() {
        Trainer trainer = new Trainer("Jane Smith", 28, 3000.0, "jane@example.com", "01553645231", "trainerpass", 8, 1);

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.addTrainer(trainer);

            mockedSQL.verify(() -> SQLConnection.insertTrainer(trainer));
            mockedLogs.verify(() -> Logs.addLogs(admin, trainer.getPersonEmail(), "insert"));
        }
    }

    @Test
    public void testRemoveTrainer_ValidID() {
        int trainerID = 1;

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeTrainer(trainerID);

            mockedSQL.verify(() -> SQLConnection.removeTrainer(trainerID));
            mockedLogs.verify(() -> Logs.addLogs(admin, trainerID, "remove"));
        }
    }

    @Test
    public void testRemoveTrainer_InvalidID() {
        int trainerID = -1;

        try (MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeTrainer(trainerID);

            mockedSQL.verify(() -> SQLConnection.removeTrainer(trainerID), never()); // Should not be called for invalid ID
        }
    }

    @Test
    public void testAddTrainee() {
        Subscription subscription = new Subscription("gold");
        ExercisePlan exercisePlan = new ExercisePlan(1);
        Trainee trainee = new Trainee("Mike Johnson", 25, "mike@example.com", "01131265489", "traineepass", subscription, exercisePlan);

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.addTrainee(trainee);

            mockedSQL.verify(() -> SQLConnection.insertTrainee(trainee));
            mockedLogs.verify(() -> Logs.addLogs(admin, trainee.getPersonEmail(), "insert"));
        }
    }

    @Test
    public void testRemoveTrainee() {
        int traineeID = 1;

        try (MockedStatic<Logs> mockedLogs = mockStatic(Logs.class); 
             MockedStatic<SQLConnection> mockedSQL = mockStatic(SQLConnection.class)) {

            admin.removeTrainee(traineeID);

            mockedSQL.verify(() -> SQLConnection.removeTrainee(traineeID));
            mockedLogs.verify(() -> Logs.addLogs(admin, traineeID, "remove"));
        }
    }
}
