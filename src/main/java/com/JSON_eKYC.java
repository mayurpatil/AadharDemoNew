package com;

import org.json.simple.JSONObject;
import org.json.*;
import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.*;
import javax.servlet.*;

@SuppressWarnings("serial")
public class JSON_eKYC extends HttpServlet {
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String AadharId = req.getParameter("AadharId");
		String OTP = req.getParameter("OTP");		
     
		JSONObject obj1 = new JSONObject();
		JSONObject obj3 = new JSONObject();
		//JSONObject location = new JSONObject();
		//JSONObject demog = new JSONObject();
		JSONObject main = new JSONObject();

		try {
			obj1.put("type", "pincode");
			obj1.put("pincode", "580008");
			//location.put("location", obj1);

         obj3.put("aadhaar-id", AadharId);
         obj3.put("location", obj1);
         obj3.put("modality", "otp");
         obj3.put("certificate-type", "preprod");
         obj3.put("otp", OTP);
     
         //obj2.put("auth-capture-request", obj3);
         //obj2.put("auth-capture-request", obj1);
                
			main.put("consent", "Y");
         main.put("auth-capture-request", obj3);
        		                 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.setContentType("text/plain");
		URL url = null;
		//url = new URL("http://localhost:8181/auth/raw");
     	url = new URL("http://188.166.254.225:8181/kyc/raw");

		HttpURLConnection urlConn = null;
		urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("POST");
      urlConn.setConnectTimeout(0);
		urlConn.setRequestProperty("Content-Type", "application/json");
		urlConn.connect();

		DataOutputStream output = null;
		DataInputStream input = null;
		output = new DataOutputStream(urlConn.getOutputStream());

		/* Construct the POST data. */
		String content = main.toString();
      resp.getWriter().println(content);

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
