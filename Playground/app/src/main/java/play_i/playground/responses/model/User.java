package play_i.playground.responses.model;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("user_id")
	private String user_id;
	
	
	public User(){
		
		
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
