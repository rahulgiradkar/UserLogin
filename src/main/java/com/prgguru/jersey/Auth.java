package com.prgguru.jersey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.prgguru.jersey.DBConnection;
import com.prgguru.jersey.Utitlity;

@Path("/auth")
public class Auth {

	@GET
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/login")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String auth(@QueryParam("username") String username, @QueryParam("password") String password,
			@QueryParam("device") String device) {
		boolean isValid = true;
		JSONArray array = new JSONArray();
		if (!Utitlity.isNotNull(username)) {
			array.put("Username should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(password)) {
			array.put("Password should not be empty");
			isValid = false;
		}
		if (!isValid) {
			return Utitlity.createMessage(422, "validation error", array);
		} else {
			try {
				Connection dbConnection = DBConnection.createConnection();
				Statement stmt = dbConnection.createStatement();
				String query = "SELECT * FROM user WHERE username = '" + username + "' AND password=" + "'" + password
						+ "'";
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					return Utitlity.createMessage(200, "Sucess", "Login success fully");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return Utitlity.createMessage(500, "validation error", "Internal server serror");
			}
		}
		return Utitlity.createMessage(403, "validation error", "Invalid creadicial");
	}
	public static void main(String[] args) {
		Auth auth=new Auth();
		String a=auth.register("Raxxxhul", "password", "name", "dob", "814929032", "wbfgwefb", null);
		System.out.println(a);
	}

	@GET
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/register")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("username") String username, @QueryParam("password") String password,
			@QueryParam("name") String name, @QueryParam("dob") String dob,@QueryParam("email") String email, @QueryParam("mobile") String mobile,@QueryParam("role") String role) {
		boolean isValid = true;
		JSONArray array = new JSONArray();
		if (!Utitlity.isNotNull(username)) {
			array.put("Username should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(password)) {
			array.put("Password should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(name)) {
			array.put("Name should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(mobile)) {
			array.put("Mobile should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(email)) {
			array.put("Email should not be empty");
			isValid = false;
		}
		if (!Utitlity.isNotNull(role)) {
			role="customer";
		}
		if (!isValid) {
			return Utitlity.createMessage(422, "validation error", array);
		} else {
			return registre(username, password, name, dob,mobile,email,role);
		}
		
	}
	private String registre(String username, String password, String name, String dob, String mobile, String email, String role) {
		try {
			Connection dbConnection = DBConnection.createConnection();
			String query = "INSERT into user(username,password,name,dob,mobile,email,role)  values(?,?,?,?,?,?,?)";
			 PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		      preparedStmt.setString(1, username);
		      preparedStmt.setString(2, password);
		      preparedStmt.setString(3, name);
		      preparedStmt.setString(4, dob);		      
		      preparedStmt.setString(5, mobile);
		      preparedStmt.setString(6, email);
		      preparedStmt.setString(7, role);
		  int x= preparedStmt.executeUpdate();   
			if(x==1) {
				return Utitlity.createMessage(200, "Sucess", "User added success fully");
			}else {
				return Utitlity.createMessage(501, "validation error", "Invalid");
			}
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			if(e.getErrorCode()==1062) {
				return Utitlity.createMessage(409 , "validation error", "User is allredy exits");
			}else {
				return Utitlity.createMessage(500, "validation error", "Internal server serror");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Utitlity.createMessage(500, "validation error", "Internal server serror");
			
		}
	}

}
