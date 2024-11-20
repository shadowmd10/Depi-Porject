package Gym.Management.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import Gym.Management.people.Trainer;


import static org.junit.Assert.*;

public class TrainerTest {
    
    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new Trainer("John Doe", 30, 5000.0, "john.doe@example.com", "01234567890", "password", 8, 1);
    }

    @Test 
    public void testTrainerInitialization() {
        assertEquals("John Doe", trainer.getPersonName());
        assertEquals(30, trainer.getPersonAge());
        assertEquals("john.doe@example.com", trainer.getPersonEmail());
		
        assertEquals(5000.0, trainer.getSalary(), 0.01);
        assertEquals(8, trainer.getWorkingHours());
        assertEquals(1, trainer.getAssignedHall());
        assertEquals(1.0, trainer.getRating(), 0.01);
    }

    @Test
    public void testSetWorkingHours_Valid() {
        trainer.setWorkingHours(10);
        assertEquals(10, trainer.getWorkingHours());
    }

    @Test
    public void testSetWorkingHours_Invalid() {
        trainer.setWorkingHours(5); // should trigger an invalid message
        assertEquals(8, trainer.getWorkingHours()); // should remain unchanged
    }

    @Test
    public void testSetAssignedHall_Valid() {
        trainer.setAssignedHall(2);
        assertEquals(2, trainer.getAssignedHall());
    }

    @Test
    public void testSetAssignedHall_Invalid() {
        trainer.setAssignedHall(0); // should trigger an invalid message
        assertEquals(1, trainer.getAssignedHall()); // should remain unchanged
    }

    @Test
    public void testSetRating_Valid() {
        trainer.setRating(4.5);
        assertEquals(4.5, trainer.getRating(), 0.01);
    }

    @Test
    public void testSetRating_Invalid() {
        trainer.setRating(6.0); // should trigger an invalid message
        assertEquals(1.0, trainer.getRating(), 0.01); // should remain unchanged
    }

    @Test
    public void testSetSalary_Valid() {
        trainer.setSalary(6000.0);
        assertEquals(6000.0, trainer.getSalary(), 0.01);
    }

    @Test
    public void testSetSalary_Invalid() {
        trainer.setSalary(-100.0); // should trigger an invalid message
        assertEquals(5000.0, trainer.getSalary(), 0.01); // should remain unchanged
    }

    @Test
    public void testIncreaseRating_Valid() {
        trainer.increaseRating(1.0);
        assertEquals(2.0, trainer.getRating(), 0.01);
    }

	/*
	 * @Test public void testIncreaseRating_Invalid() { trainer.increaseRating(2.0);
	 * // should trigger an invalid message assertEquals(2.0, trainer.getRating(),
	 * 0.01); // should remain unchanged }
	 */

    @Test
    public void testToString() {
        String expected = "Mr/Ms John Doe : 30 years old. His/Her Email address : john.doe@example.com with Rating : 1.0";
        assertEquals(expected, trainer.toString());
    }
}
