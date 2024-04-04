package ro.unibuc.hello.e2e.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.e2e.util.HeaderSetup;
import ro.unibuc.hello.e2e.util.ResponseErrorHandler;
import ro.unibuc.hello.e2e.util.ResponseResults;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest()
public class ReadingRecordSteps {

    public static ResponseResults latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    private ReadingRecordRepository readingRecordRepository;

    private List<ReadingRecordEntity> readingRecords;

    @PostConstruct
    public void init() {
        readingRecords = readingRecordRepository.findAll();
    }

    @Given("^the client calls /readingrecords")
    public void the_client_issues_GET_authors() {
        executeGet("http://localhost:8080/readingrecords");
    }

    @Then("^the client receives a status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the response contains all reading records")
    public void the_response_contains_all_reading_records() throws JSONException {
        String latestResponseBody = latestResponse.getBody();
        JSONArray jsonArray = new JSONArray(latestResponseBody);

        Set<String> readingRecordIds = new HashSet<>();
        for (ReadingRecordEntity readingRecord : readingRecords) {
            readingRecordIds.add(readingRecord.getReadingRecordId());
        }

        // Check if every reading record exists in the response
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String readingRecordId = jsonObject.getString("readingRecordId");
            assertThat("Response received does not contain reading record with id: " + readingRecordId,
                    readingRecordIds.contains(readingRecordId), is(true));
        }
    }

    public void executeGet(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }
}
