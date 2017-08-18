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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/shops")
public class Shop {
	@GET
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/getlist")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public Response shopList(@QueryParam("shop_id") String shop_id) {
		String whereclause = null;
		if (Utitlity.isNotNull(shop_id)) {
			if(whereclause==null) {
				whereclause="where ";
			}
			whereclause = whereclause+"id=" + shop_id;
		}
		try {
			Connection dbConnection = DBConnection.createConnection();
			Statement stmt;
			stmt = dbConnection.createStatement();
			String query = "SELECT * FROM shops " + whereclause;
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			JSONArray shops = new JSONArray();
			while (rs.next()) {
				JSONObject json = getShopFromResultSet(rs);
				shops.put(json);
			}
			return Response.ok(Utitlity.createMessage(200, "List of shops", shops)).build();// .header("");;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;// Utitlity.createMessage(500, "List of shops", "Server error");;
	}

	private JSONObject getShopFromResultSet(ResultSet rs) throws JSONException, SQLException {
		JSONObject json = new JSONObject();
		json.put("id", rs.getString("id"));
		json.put("name", rs.getString("name"));
		json.put("employee_map_id", rs.getString("employee_map_id"));
		json.put("capacity", rs.getString("capacity"));
		json.put("address", rs.getString("address"));
		json.put("location_lat", rs.getString("location_lat"));
		json.put("location_lon", rs.getString("location_lon"));
		json.put("shop_open_time", rs.getString("shop_open_time"));
		json.put("shop_close_time", rs.getString("shop_close_time"));
		json.put("holiday", rs.getString("holiday"));
		json.put("lunch_time_start", rs.getString("lunch_time_start"));
		json.put("lunch_time_end", rs.getString("lunch_time_end"));
		json.put("description", rs.getString("description"));
		json.put("ambiance", rs.getString("ambiance"));
		json.put("type", rs.getString("type"));
		json.put("owner_id", rs.getString("owner_id"));
		json.put("admin_id", rs.getString("admin_id"));
		return json;
	}

	public static void main(String[] args) {
		Shop shop = new Shop();
		System.out.println(shop.shopList("1"));

	}
}
