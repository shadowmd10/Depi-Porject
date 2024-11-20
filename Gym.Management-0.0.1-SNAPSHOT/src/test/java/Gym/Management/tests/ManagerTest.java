package Gym.Management.tests;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import Gym.Management.people.Admin;
import Gym.Management.people.Manager;

public class ManagerTest {

    private Manager manager;
    private Admin admin;
 
    @BeforeEach
    public void setUp() {
        manager = new Manager("Alice Smith", 35, "alice@example.com", "01234567890", "password");
        admin = new Admin("Bob Johnson", 30, "Admin102@gmail.com", "01298765431", "password");
    }

    @Test
    public void testAddAdmin() {
        manager.addAdmin(admin);
        assertTrue("Admin should be added to the Manager's list", Manager.admins.contains(admin));
    }

    @Test
    public void testAddNullAdmin() {
        manager.addAdmin(null);
        
        assertFalse("Null admin should not be added to the list", Manager.admins.contains(null));
    }

    @Test
    public void testRemoveAdmin() {
        manager.addAdmin(admin); 
        manager.removeAdmin(admin);
        assertFalse("Admin should be removed from the Manager's list", Manager.admins.contains(admin));
    }

    @Test
    public void testRemoveNonExistingAdmin() {
        Manager manager2 = new Manager("Charlie Brown", 40, "charlie@example.com", "01231231234", "password");
        manager2.removeAdmin(admin); 
        assertFalse("Admin should not be removed since it was never added", Manager.admins.contains(admin));
    }
}
