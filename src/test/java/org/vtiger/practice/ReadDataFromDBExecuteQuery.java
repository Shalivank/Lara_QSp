package org.vtiger.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class ReadDataFromDBExecuteQuery {

	public static void main(String[] args) throws SQLException {

		//step 1 : Create Object for Driver -  from cj.jdbc
		Driver driverRef = new Driver();
		Connection con = null;

		//step 2 : Register the Driver
		DriverManager.registerDriver(driverRef);

		//step 3 : Establish the connection - Provide database name
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sdet", "root", "root");

		//step 4 : create statement
		Statement state = con.createStatement();

		//step 5 : Execute the query - Provide table name
		String query = "select * from Emp";
		String expData = "Channu"; 
		ResultSet result = state.executeQuery(query);
		boolean flag = false;
		while(result.next())
		{
			String actData = result.getString(1);//1--->column index
			if(actData.equalsIgnoreCase(expData))
			{
				System.out.println(actData);
				flag = true;
				break;
			}
		}
		if(flag)
		{
			System.out.println("data present");
		}
		else
		{
			System.out.println("data not present");
		}

		//step 6 : Validate

		//step 7 : Close Connection
		con.close();

	}

}
