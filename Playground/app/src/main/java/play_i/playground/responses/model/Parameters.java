package play_i.playground.responses.model;

import com.google.gson.annotations.SerializedName;

public class Parameters {

	@SerializedName("date_cita")
	private String date_cita;

	
	public Parameters(){
		
		
	}


	public String getDate_cita() {
		return date_cita;
	}


	public void setDate_cita(String date_cita) {
		this.date_cita = date_cita;
	}


	
}
