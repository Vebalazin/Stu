package hr.foi.air.StudentAssistant.interfaces;

import hr.foi.air.StudentAssistant.types.Obavijest;


import java.util.List;

import org.json.JSONException;

public interface IObavijesti {

	public String getObavijesti();
	public List<Obavijest> loadObavijesti() throws JSONException;

}
