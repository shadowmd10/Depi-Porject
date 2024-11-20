package Gym.Management.sqlConnection;

import Gym.Management.mainRun.Logs;
import Gym.Management.papers.ExcelSheets;

public class TestDatabaseCreate {

	public static void main(String[] args) {

		SQLConnection.createDB();
		SQLConnection.createTables();
		
		Logs.createLogsFile();
		
		ExcelSheets.createSheet();
		
//		SQLConnection.deleteDB();
	}

}
