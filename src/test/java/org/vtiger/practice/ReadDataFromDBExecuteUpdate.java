package org.vtiger.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class ReadDataFromDBExecuteUpdate {

	public static void main(String[] args) throws SQLException {

		//step1 : Create object for Driver
		Driver driverRef = new Driver();
		Connection con = null;

		//step 2 : Register the Driver
		DriverManager.registerDriver(driverRef);

		//step 3 : Establish the Connection - Provide Database Name
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sdet", "root", "root");

		//step 4 : Create Statement
		Statement state = con.createStatement();

		//step 5 : Execute the query
		String query = "insert into Emp values('Channu',123);";
		int result = state.executeUpdate(query);
		System.out.println(result);

		//step 6 : Validate
		if(result==1)
		{
			System.out.println("data added successfully");
		}
		else
		{
			System.out.println("data not added");
		}

		//step 7 : Close Connection
		con.close();
		System.out.println("connection closed");

	}
}
