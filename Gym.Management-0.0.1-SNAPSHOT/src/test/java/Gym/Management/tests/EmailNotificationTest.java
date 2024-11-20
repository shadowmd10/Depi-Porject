package Gym.Management.tests;

import Gym.Management.papers.ExercisePlan;
import Gym.Management.papers.Subscription;
import Gym.Management.people.Admin;
import Gym.Management.people.Trainee;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmailNotificationTest {

	private Admin admin;
	private Trainee trainee;
    private Subscription subscription;
    private ExercisePlan exercisePlan;
    
    private boolean isEmailSent;

    @BeforeEach
    public void setUp() {
    	admin = new Admin("John Doe", 30, "john@example.com", "01234567892", "password");
        subscription = new Subscription("gold");
        exercisePlan = new ExercisePlan(20); // Example with 60 minutes duration
        trainee = new Trainee("John Doe", 25, "amrsaber4001@gmail.com", "01234567890", "password123", subscription, exercisePlan);
    }
    
 // Test for Expiring Subscription Date
    @Test
    public void testEmailNotificationForExpiringSubscription() {
    	subscription.setEndtDate(LocalDate.now().plusDays(2));
        trainee.setSubcription(subscription);
        System.out.println(trainee.getSubcription().getEndtDate());
        isEmailSent = admin.sendEmailNotification(trainee);
        assertEquals(true, isEmailSent);
    }
    
 // Test for Not Expiring Subscription Date
    @Test
    public void testEmailNotificationForNotExpiringSubscription() {
    	subscription.setEndtDate(LocalDate.now().plusDays(10));
        trainee.setSubcription(subscription);
        System.out.println(trainee.getSubcription().getEndtDate());
        isEmailSent = admin.sendEmailNotification(trainee);
        assertEquals(false, isEmailSent);
    }
}
