package Gym.Management.mainRun;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Gym.Management.people.Admin;

public class Logs {

	public static void createLogsFile() 
	{
		// Specify the file name and path
        String fileName = "AdminLogs.txt";

        // Data to write to the file
        String content = "Admin Logs : ";

        // Create the file and write data to it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Logs File Created Successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void addLogs(Admin admin, Object appended, String operation) 
	{
		// Specify the file name and path
        String fileName = "AdminLogs.txt";

        // Text to append to the file
        String contentToAppend = "\n Admin : " + admin.getPersonEmail() + " " + operation + " " + appended;

        // Append text to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(contentToAppend);
            System.out.println("Text appended successfully!");
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
	}
	
}
