package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestUtils {
	
	private static Logger log = LogManager.getLogger(TestUtils.class.getName());
	
	public static String getResposeString(Response response){
		log.info("Converting Response to String");
		String strResponse = response.getBody().asString();
		log.debug("String Response"+ strResponse);
		return strResponse;
	}
	
	public static JsonPath jsonParser(String response){
		log.info("Parsing String Response to JSON");
		JsonPath jsonResponse = new JsonPath(response);
		log.debug("JSON Response: "+jsonResponse);
		return jsonResponse;
	}
	

	public static XmlPath xmlParser(String response){
		log.info("Parsing String Response to XML");
		XmlPath xmlResponse = new XmlPath(response);
		log.debug("XML Response: "+xmlResponse);
		return xmlResponse;
	}
	
	public static int getStatusCode(Response response){
		log.info("Getting Response Code");
		int statusCode = response.getStatusCode();
		log.info("Status Code: "+statusCode);
		return statusCode;
	}
	
	public static String getStatusMessage(Response response){
		log.info("Getting Response Message");
		String responseMessage = response.getStatusLine();
		log.info("Response Message: "+responseMessage);
		return responseMessage;
	}

	public static String getResponseBody(Response response){

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// By using the ResponseBody.asString() method, we can convert the  body
		// into the string representation.
		String responseBody = body.asString();
		log.info("Response Body is: " + responseBody);
		return new String();

	}
	
	
	
	
	
	
	
}
