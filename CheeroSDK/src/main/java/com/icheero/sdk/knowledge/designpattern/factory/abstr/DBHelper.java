package com.icheero.sdk.knowledge.designpattern.factory.abstr;

public class DBHelper<E>
{
	private static final String DATABASE = "SQLSERVER";
	
	public static IDbHelper<User> userDbHelper()
	{
		IDbHelper<User> user;
		switch (DATABASE)
		{
			case "SQLSERVER":
			default:
				user = new MSSqlDbHelper<>();
				break;
			case "MySql":
				user = new MySqlDbHelper<>();
				break;
		}
		return user;
	}
	
	
}
