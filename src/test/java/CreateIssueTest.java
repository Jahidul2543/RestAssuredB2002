import base.Base;
import base.BaseAssertion;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.URL;

public class CreateIssueTest {

    @Test
    public void createIssueEndPointtest(){

        String sessionId = Base.getSessionId();
        String payload = Base.generatePayLoadString("createIssue.json");
        String url = URL.getEndPoint("/rest/api/2/issue/");
        Response response = Base.POSTRequest(url,payload, sessionId);
        // Assertion
        BaseAssertion.verifyStatusCode(response, 201);

    }
}
