package com;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.*;
import javax.servlet.*;

@SuppressWarnings("serial")
public class JSON_OTP extends HttpServlet {
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String AadharId = req.getParameter("AadharId");
				
		//JSONObject obj2 = new JSONObject();
		JSONObject obj1 = new JSONObject();
		//JSONObject name = new JSONObject();
		//JSONObject location = new JSONObject();
		//JSONObject demog = new JSONObject();
		JSONObject main = new JSONObject();

		try {
			obj1.put("type", "pincode");
			obj1.put("pincode", "580008");
			//location.put("location", obj1);

			main.put("aadhaar-id", AadharId);
			main.put("location", obj1);
			main.put("modality", "demo");
			main.put("certificate-type", "preprod");
			main.put("channel", "EMAIL_AND_SMS");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		URL url = null;
		//url = new URL("http://localhost:8181/auth/raw");
     	url = new URL("http://188.166.254.225:8181/otp");

		HttpURLConnection urlConn = null;
		urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("POST");
		urlConn.setRequestProperty("Content-Type", "application/json");
		urlConn.connect();

		DataOutputStream output = null;
		DataInputStream input = null;
		output = new DataOutputStream(urlConn.getOutputStream());

		/* Construct the POST data. */
		String content = main.toString();

		/* Send the request data. */
		output.writeBytes(content);
		output.flush();
		output.close();
      resp.getWriter().println(output);

		/* Get response data. */
		String response = null;
		input = new DataInputStream(urlConn.getInputStream());
		while (null != ((response = input.readLine()))) {
			System.out.println(response);
			resp.getWriter().println(response);
			input.close();
		}	
	}
}
