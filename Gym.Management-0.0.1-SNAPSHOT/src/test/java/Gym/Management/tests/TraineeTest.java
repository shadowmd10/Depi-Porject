package Gym.Management.tests;

import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;
import Gym.Management.people.Trainee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.*;

public class TraineeTest {

    private Trainee trainee;
    private Subscription subscription;
    private ExercisePlan exercisePlan;

    @BeforeEach
    public void setUp() {
        subscription = new Subscription("gold");
        exercisePlan = new ExercisePlan(60); // Example with 60 minutes duration
        trainee = new Trainee("John Doe", 25, "john.doe@example.com", "01234567890", "password123", subscription, exercisePlan);
    }
    
    // Test for Valid Phone Number
    @Test
    public void testSetPersonPhone_Valid() {
        trainee.setPersonPhone("01234567890");
        assertEquals("01234567890", trainee.getPersonPhone());
    }

	/*
	 * // Test for Phone Number that's Too Short
	 * 
	 * @Test public void testSetPersonPhone_TooShort() {
	 * trainee.setPersonPhone("01234"); assertEquals("", trainee.getPersonPhone());
	 * }
	 */
	/*
	 * // Test for Phone Number with Non-Digit Characters
	 * 
	 * @Test public void testSetPersonPhone_NonDigit() {
	 * trainee.setPersonPhone("01234abc789"); assertEquals("",
	 * trainee.getPersonPhone()); }
	 * 
	 * // Test for Null Phone Number
	 * 
	 * @Test public void testSetPersonPhone_Null() { trainee.setPersonPhone(null);
	 * assertEquals("", trainee.getPersonPhone()); }
	 */

    // Test setting a valid subscription
    @Test
    public void testSetSubscription_Valid() {
        Subscription newSubscription = new Subscription("silver");
        trainee.setSubcription(newSubscription);
        assertEquals(newSubscription, trainee.getSubcription());
    }

    // Test setting a null subscription
    @Test
    public void testSetSubscription_Null() {
        trainee.setSubcription(null);
        assertEquals(subscription, trainee.getSubcription());  // Subscription should remain unchanged
    }

    // Test setting a valid exercise plan
    @Test
    public void testSetExercisePlan_Valid() {
        ExercisePlan newPlan = new ExercisePlan(90);  // Example with 90 minutes duration
        trainee.setPlan(newPlan);
        assertEquals(newPlan, trainee.getPlan());
    }

    // Test setting a null exercise plan
    @Test
    public void testSetExercisePlan_Null() {
        trainee.setPlan(null);
        assertEquals(exercisePlan, trainee.getPlan());  // Plan should remain unchanged
    }

    // Test increasing points with a valid value
    @Test
    public void testIncreasePoints_Valid() {
        trainee.increasePoints(50);
        assertEquals(100, trainee.getPoints());
    }

    // Test increasing points with an invalid (negative) value
    @Test
    public void testIncreasePoints_Invalid() {
        trainee.increasePoints(-20);
        assertEquals(50, trainee.getPoints());  // Points should remain unchanged
    }

    // Test increasing points with zero (no effect)
    @Test
    public void testIncreasePoints_Zero() {
        trainee.increasePoints(0);
        assertEquals(50, trainee.getPoints());  // Points should remain unchanged
    }

    // Test the toString method
    @Test
    public void testToString() {
        String expected = "gold Subscriper Mr/Ms John Doe : 25 years old. His/Her Email address : john.doe@example.com has : 50Points";
        assertEquals(expected, trainee.toString());
    }
}
