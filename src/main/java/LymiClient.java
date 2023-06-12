import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LymiClient {
  private final String serverUrl;

  public LymiClient(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  public void createPerson(ServerInfo serverInfo) {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost(serverUrl);

    try {
      // Convert the Person object to JSON
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
      ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
      String jsonPayload = objectWriter.writeValueAsString(serverInfo);

      // Set the JSON payload as the request entity
      StringEntity requestEntity = new StringEntity(jsonPayload);
      httpPost.setEntity(requestEntity);
      httpPost.setHeader("Content-Type", "application/json");

      // Execute the request
      HttpResponse response = httpClient.execute(httpPost);

      // Process the response
      int statusCode = response.getStatusLine().getStatusCode();
      BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line;
      StringBuilder responseBody = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        responseBody.append(line);
      }

      // Print the response
      System.out.println("Status code: " + statusCode);
      System.out.println("Response body: " + responseBody.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
