package com.connectelco.griot.app;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class App {
	public static void main(String[] args) {
		final CassandraConnector client = new CassandraConnector();
		final String ipAddress = args.length > 0 ? args[0] : "73.184.216.136";
		final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
		System.out.println("Connecting to IP Address " + ipAddress + ":" + port
				+ "...");
		client.connect(ipAddress, port);
		
		String query = " select * from connectelco.test where pk = ? and t = ?";
		final ResultSet testRslts = client.getSession().execute(query, 0, 0);
		final Row testRow = testRslts.one();
		System.out.println(testRow);
		client.close();
	}
	
}
