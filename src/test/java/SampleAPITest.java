import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class SampleAPITest {

    private static Logger log  = LogManager.getLogger(SampleAPITest.class.getName());

    public static String doLogin() throws IOException {
        // URL =Protocol ://baseURL/endpoint?query_parameter
        String url = "http://localhost:8080/rest/auth/1/session";
        ///Users/jahidul/IdeaProjects/RestAssuredB2002/payloads/login.json
        // Read Payload login.json and store in String object
        String loginPayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/payloads/login.json")));

        // Specify the request body
        RequestSpecification requestSpecification = RestAssured.given().body(loginPayload);
        // Specify the request header
        requestSpecification.contentType(ContentType.JSON);
        // Make POST call and store the relevant response in the Response object
       Response response = requestSpecification.post(url);

        /*System.out.println("Status code" + response.getStatusCode() + "\n"
                                         + response.asString() + "\n"
                                         + response.contentType()
        );*/


        /**
        * Process response to get the value of 'value' key using JSON Path
        * jsonpath = session.value
        * */
        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);
        String sessionId = jsonPath.get("session.value");
        System.out.println(sessionId);

        //System.out.println("previousLoginTime " + jsonPath.get("loginInfo.previousLoginTime"));
        log.info("***************previousLoginTime*******" + jsonPath.get("loginInfo.previousLoginTime") );

        return  sessionId;


    }

    public static Response createIssue() throws IOException {
        String url = "http://localhost:8080/rest/api/2/issue/";
        String createIssuePayload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/payloads/createIssue.json")));
        RequestSpecification requestSpecification = RestAssured.given().body(createIssuePayload);
        // Specify headers
        requestSpecification.contentType(ContentType.JSON);
        //requestSpecification.cookie("Cookie","JSESSIONID=DFD54B6BFF83C53F98401DCDBAFC421E");
        String sessionId = "JSESSIONID=" + doLogin() ;
        requestSpecification.header("Cookie", sessionId);

        Response response = requestSpecification.post(url);
        System.out.println("Status Code: " + response.statusCode());

        return response;

    }

   // @Test
    public static void createIssueStatusTest() throws IOException {
        Response response = createIssue();
        int actualStatusCode =  createIssue().statusCode();
        int expectedStatusCode = 200;
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
        System.out.println("Test Passed with status code " + actualStatusCode);

        String stringResponse = response.asString();
        JsonPath jsonPath = new JsonPath(stringResponse);
        String actualIssueid = jsonPath.get("id");
        String expectedIssueId = "10036";
        Assert.assertEquals(expectedIssueId, actualIssueid);
    }

   // @Test
    public static void issuIdValidationTest() throws IOException {

    }
}
