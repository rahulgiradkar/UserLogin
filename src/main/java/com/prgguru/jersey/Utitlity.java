package com.prgguru.jersey;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Utitlity {
	/**
	 * Null check Method
	 * 
	 * @param txt
	 * @return
	 */
	public static boolean isNotNull(String txt) {
		// System.out.println("Inside isNotNull");
		return txt != null && txt.trim().length() >= 0 ? true : false;
	}

	/**
	 * Method to construct JSON
	 * 
	 * @param tag
	 * @param status
	 * @return
	 */
	public static String constructJSON(String tag, boolean status) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return obj.toString();
	}

	/**
	 * Method to construct JSON with Error Msg
	 * 
	 * @param tag
	 * @param status
	 * @param err_msg
	 * @return
	 */
	public static String constructJSON(String tag, boolean status,String err_msg) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("tag", tag);
			obj.put("status", new Boolean(status));
			obj.put("error_msg", err_msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		return obj.toString(); 
	}

	public static String createMessage(int i, String string, Object array)  {
		JSONObject jsonObject=new JSONObject();
		try {	jsonObject.put("app", Utitlity.getAPP());
		jsonObject.put("message", string);
		jsonObject.put("data", array);
		jsonObject.put("status", i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	private static JSONObject getAPP() throws JSONException  {
		JSONObject jsonObject=new JSONObject();		
			jsonObject.put("app_name","Shop");
			jsonObject.put("version", "1");
			jsonObject.put("time", new Date().getTime());
		
		return jsonObject;
	}
	
}
