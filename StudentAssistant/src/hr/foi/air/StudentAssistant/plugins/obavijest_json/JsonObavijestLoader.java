package hr.foi.air.StudentAssistant.plugins.obavijest_json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.StudentAssistant.interfaces.IObavijesti;
import hr.foi.air.StudentAssistant.types.Obavijest;

public class JsonObavijestLoader implements IObavijesti {
	
	/**metoda za dohvacanje obavijesti s web servisa**/
	public String getObavijesti() {
		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request =
				new HttpGet("http://svgrdjan.0fees.net//index.php?action=list&entity=obavijesti&REST=json");
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return result;
	}

	/**metoda koja dohvacene obavijesti pohranjuje u listu**/
	public List<Obavijest> loadObavijesti() throws JSONException {
		List<Obavijest> obavijesti = new ArrayList<Obavijest>();

		String res = this.getObavijesti();
		int len = new JSONArray(res).length();

		for (int i = 0; i < len; i++) {
			JSONObject json = new JSONArray(res).getJSONObject(i);
			Obavijest poi = new Obavijest(json.getString("Naslov_obavijesti"),json.getString("Autor"),json.getString("Obavijest"));
			obavijesti.add(poi);
		}
		return obavijesti;
	}







}
