package play_i.playground.responses.model;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"google",
"facebook",
"slack"
})
@JsonIgnoreProperties
public class Data {
//
//@JsonProperty("google")
//private Google google;
@JsonProperty("facebook")
private Facebook facebook;

@SerializedName("user_id")
private String user_id;

//@JsonProperty("slack")
//private Slack slack;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

//@JsonProperty("google")
//public Google getGoogle() {
//return google;
//}
//
//@JsonProperty("google")
//public void setGoogle(Google google) {
//this.google = google;
//}

@JsonProperty("facebook")
public Facebook getFacebook() {
return facebook;
}

@JsonProperty("facebook")
public void setFacebook(Facebook facebook) {
this.facebook = facebook;
}

//
//@JsonProperty("slack")
//public Slack getSlack() {
//return slack;
//}
//
//@JsonProperty("slack")
//public void setSlack(Slack slack) {
//this.slack = slack;
//}



@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public String getUser_id() {
	return user_id;
}

public void setUser_id(String user_id) {
	this.user_id = user_id;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}