import com.fasterxml.jackson.annotation.JsonProperty;


public class ServerInfo {

  public ServerInfo(){

  }

  public int getRequest_id() {
    return request_id;
  }

  public void setRequest_id(int request_id) {
    this.request_id = request_id;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getCpuutilization() {
    return cpuutilization;
  }

  public void setCpuutilization(String cpuutilization) {
    this.cpuutilization = cpuutilization;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty("request_id")
  private int request_id;

  @JsonProperty("hostname")
  private String hostname;

  @JsonProperty("cpuutilization")
  private String cpuutilization;

  @JsonProperty("username")
  private String username;

}
