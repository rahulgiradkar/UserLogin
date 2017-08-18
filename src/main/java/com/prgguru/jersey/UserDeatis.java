package com.prgguru.jersey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/user")
public class UserDeatis {
	// HTTP Get Method
		@GET 
		// Path: http://localhost/<appln-folder-name>/login/dologin
		@Path("/userinfo")
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 
		// Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
		public Response doLogin(@QueryParam("user_id") String user_id) throws Exception{
			Connection dbConnection = DBConnection.createConnection();
			Statement stmt;
			stmt = dbConnection.createStatement();
			String whereclause="where id="+user_id;
			String query = "SELECT * FROM users " + whereclause;
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			JSONObject json = null;
			while (rs.next()) {
				try {
					json=new JSONObject();
					json.put("id",rs.getString("id"));
					json.put("username",rs.getString("username"));
					json.put("password",rs.getString("password"));
					json.put("dob",rs.getString("dob"));
					json.put("name",rs.getString("name"));
					json.put("role",rs.getString("role"));
					json.put("mobile",rs.getString("mobile"));
					json.put("email",rs.getString("email"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return Response.ok(Utitlity.createMessage(200, "User information", json)).build();
			
		}
}
