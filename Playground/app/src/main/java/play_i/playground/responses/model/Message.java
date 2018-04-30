package play_i.playground.responses.model;

import com.google.gson.annotations.SerializedName;

public class Message {

	 @SerializedName("type")
    private Integer type;

    @SerializedName("speech")
    private String speech;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}
    
    
    
}
