package hr.foi.air.StudentAssistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;

import hr.foi.air.StudentAssistant.R;
import hr.foi.air.StudentAssistant.plugins.obavijest_json.JsonObavijestLoader;
import hr.foi.air.StudentAssistant.types.Obavijest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ObavijestiActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_obavijesti);
		try {

			List<Obavijest> listaObavijesti; // lista Obavijesti objekata
			ListView lv; // referenca na ListView objekt
			String[] from; // nazivi atributa koji se mapiraju
			int[] to; // nazivi elemenata predloška u koje se mapira
			HashMap<String, String> mapaObavijesti; // veza podataka i predloška
			List<HashMap<String, String>> fillMaps; // lista mapa (veza)
			Iterator<Obavijest> itr; // standardni iterator
			SimpleAdapter adapter; // adapter za prikaz podataka na formi
			JsonObavijestLoader da = new JsonObavijestLoader();
			// pohrana svih obavijesti u listu
			listaObavijesti = da.loadObavijesti();
			// definiranje atributa za veze
			from = new String[] { "Autor", "Obavijest" };
			to = new int[] { R.id.Autor, R.id.Obavijest };
			// kreiranje mapa
			fillMaps = new ArrayList<HashMap<String, String>>();
			itr = listaObavijesti.iterator();
			while (itr.hasNext()) {
				mapaObavijesti = new HashMap<String, String>();
				Obavijest obavijest = itr.next();
				mapaObavijesti.put("Autor", obavijest.getAutor());
				mapaObavijesti.put("Obavijest", obavijest.getObavijest());
				fillMaps.add(mapaObavijesti);
			}
			// dohvat i brisanje liste
			lv = (ListView) findViewById(R.id.lvObavijesti);
			lv.invalidateViews();
			// generiranje podataka za prikaz u list view-u
			adapter = new SimpleAdapter(this, fillMaps, R.layout.obavijest_row,
					from, to);
			lv.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}

	}
}
