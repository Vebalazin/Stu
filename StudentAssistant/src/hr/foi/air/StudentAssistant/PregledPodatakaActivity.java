package hr.foi.air.StudentAssistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import hr.foi.air.StudentAssistant.R;
import hr.foi.air.StudentAssistant.database.DataAdapterBiljeske;
import hr.foi.air.StudentAssistant.database.DataAdapterBodovi;
import hr.foi.air.StudentAssistant.database.DataAdapterEvidencija;
import hr.foi.air.StudentAssistant.database.DataAdapterKolegij;
import hr.foi.air.StudentAssistant.types.Biljeska;
import hr.foi.air.StudentAssistant.types.Bodovi;
import hr.foi.air.StudentAssistant.types.Evidencija;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class PregledPodatakaActivity extends Activity implements
OnItemSelectedListener {
	Spinner spinner;
	String MY_DATABASE_NAME = "database.db";
	ArrayAdapter<String> adapterForSpinner;
	Context c;
	int id_kolegija, id_prikaza;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pregledp);
		c = this;
		ispisKolegija(); //metoda za popunjavanje spinnera unesenim kolegijima
		ispisPrikaza(); //metoda za popunjavanje spinnera mogucim prikazima podataka: biljeske, evidencija, bodovi
		spinner.setOnItemSelectedListener(this);
		PrikaziPodatke(); //metoda za ispis zeljenih podataka u listi
		super.onCreate(savedInstanceState);

	}

	/**metoda koja ispisuje odabrane podatke u listView**/
	private void PrikaziPodatke() {
		Button b = (Button) this.findViewById(R.id.btnOsvjezi);

		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ListView lv; // referenca na ListView objekt
				String[] from; // nazivi atributa koji se mapiraju
				int[] to; // nazivi elemenata predloška u koje se mapira
				HashMap<String, String> mapa;
				List<HashMap<String, String>> fillMaps; // lista mapa (veza)
				TextView tv1 = (TextView) findViewById(R.id.textView1);
				TextView tv2 = (TextView) findViewById(R.id.textView2);
				TextView tv3 = (TextView) findViewById(R.id.textView3);

				if (id_prikaza == 0) {

					List<Biljeska> listaBiljeski; // lista Biljeska objekata
					Iterator<Biljeska> itr; // standardni iterator
					SimpleAdapter adapter; // adapter za prikaz podataka na
					// formi
					// dohvat podataka iz baze
					DataAdapterBiljeske da = new DataAdapterBiljeske(
							getBaseContext());
					da.openToRead();
					// dohvaæanje svih biljeski za kolegij koji je odabran u
					// spinneru spPregledKolegiji koji sadrzi upisane kolegije
					listaBiljeski = da.getAllBiljeske(id_kolegija);
					da.close();
					// definiranje atributa za veze
					from = new String[] { "ID_biljeske", "biljeska" };
					to = new int[] { R.id.idBiljeske, R.id.biljeska };
					// kreiranje mapa
					fillMaps = new ArrayList<HashMap<String, String>>();
					itr = listaBiljeski.iterator();
					while (itr.hasNext()) {
						mapa = new HashMap<String, String>();
						Biljeska biljeska = itr.next();
						mapa.put("ID_biljeske", "" + biljeska.getIdBiljeske());
						mapa.put("biljeska", biljeska.getBiljeska());
						fillMaps.add(mapa);
					}
					// dohvat i brisanje liste
					lv = (ListView) findViewById(R.id.lvPodaci);
					lv.invalidateViews();
					// generiranje podataka za prikaz u list view-u
					adapter = new SimpleAdapter(c, fillMaps,
							R.layout.biljeske_data, from, to);
					lv.setAdapter(adapter);
					//definiranje zaglavlja liste
					tv1.setText("ID");
					tv2.setText("Bilješka");
					tv3.setText("");
					registerForContextMenu(lv);
				}
				if (id_prikaza == 1) {
					List<Evidencija> listaEvidencija; // lista Evidencija objekata
					Iterator<Evidencija> itr; // standardni iterator
					SimpleAdapter adapter; // adapter za prikaz podataka naformi
					// dohvat podataka iz baze
					DataAdapterEvidencija da = new DataAdapterEvidencija(getBaseContext());
					da.openToRead();
					// dohvaæanje svih evidencija kolegija koji je odabran u
					// spinneru spPregledKolegiji koji sadrzi upisane kolegije
					listaEvidencija = da.getAllEvidencije(id_kolegija);
					da.close();
					// definiranje atributa za veze
					from = new String[] { "ID_evidencije", "evidencija", "datum" };
					to = new int[] { R.id.idEvidencije, R.id.evidencija, R.id.datum };
					// kreiranje mapa
					fillMaps = new ArrayList<HashMap<String, String>>();
					itr = listaEvidencija.iterator();
					while (itr.hasNext()) {
						mapa = new HashMap<String, String>();
						Evidencija evidencija = itr.next();
						mapa.put("ID_evidencije",
								"" + evidencija.getIdEvidencije());
						mapa.put("evidencija", evidencija.getEvidencija());
						mapa.put("datum", evidencija.getDatum());
						fillMaps.add(mapa);
					}
					// dohvat i brisanje liste
					lv = (ListView) findViewById(R.id.lvPodaci);
					lv.invalidateViews();
					// generiranje podataka za prikaz u list view-u
					adapter = new SimpleAdapter(c, fillMaps,
							R.layout.evidencija_data, from, to);
					lv.setAdapter(adapter);
					//definiranje zaglavlja liste
					tv1.setText("ID");
					tv2.setText("Evidencija");
					tv3.setText("Datum");
					registerForContextMenu(lv);
				}

				if (id_prikaza == 2) {
					List<Bodovi> listaBodova; // lista Bodovi objekata
					Iterator<Bodovi> itr; // standardni iterator
					SimpleAdapter adapter; // adapter za prikaz podataka na formi
					// dohvat podataka iz baze
					DataAdapterBodovi da = new DataAdapterBodovi(getBaseContext());
					da.openToRead();
					// dohvaæanje svih bodova za kolegij koji je odabran u
					// spinneru spPregledKolegiji koji sadrzi upisane kolegije
					listaBodova = da.getAllBodovi(id_kolegija);
					da.close();
					// definiranje atributa za veze
					from = new String[] { "ID_bodova", "bod", "oznaka" };
					to = new int[] { R.id.idBodova, R.id.bodovi, R.id.oznaka_boda };
					// kreiranje mapa
					fillMaps = new ArrayList<HashMap<String, String>>();
					itr = listaBodova.iterator();
					//sve dok postoji sljedeci element, dodaj element u mapu
					while (itr.hasNext()) {
						mapa = new HashMap<String, String>();
						Bodovi bodovi = itr.next();
						mapa.put("ID_bodova", "" + bodovi.getIdBoda());
						mapa.put("bod", bodovi.getBod().toString());
						mapa.put("oznaka", bodovi.getOznaka());
						fillMaps.add(mapa);
					}
					// dohvat i brisanje liste
					lv = (ListView) findViewById(R.id.lvPodaci);
					lv.invalidateViews();
					// generiranje podataka za prikaz u list view-u
					adapter = new SimpleAdapter(c, fillMaps,R.layout.bodovi_data, from, to);
					lv.setAdapter(adapter);
					//definiranje zaglavlja liste
					tv1.setText("ID");
					tv2.setText("Bodovi");
					tv3.setText("Oznaka");
					registerForContextMenu(lv);
				}

			}

		});
	}

	/** metoda koji popunjava spinner spOdabirPrikaza**/
	private void ispisPrikaza() {
		Spinner s = new Spinner(c);
		s = (Spinner) findViewById(R.id.spOdabirPrikaza);
		s.setOnItemSelectedListener(this);
		ArrayAdapter<String> sp = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(sp);
		sp.add("Bilješke");
		sp.add("Evidencija");
		sp.add("Bodovi");

	}

	/**metoda koja popunjava spinner spPregledKolegiji s upisanim kolegijima**/
	private void ispisKolegija() {
		spinner = (Spinner) findViewById(R.id.spPregledPKolegiji);
		SQLiteDatabase myDB = null;
		myDB = this.openOrCreateDatabase("database.db", 1, null);
		adapterForSpinner = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapterForSpinner
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapterForSpinner);
		DataAdapterKolegij.populateSpinner(adapterForSpinner, myDB, c);
		myDB.close();

	}

	/**nakon odabira kolegija, metoda u varijablu id_kolegija zapisuje id kolegija kojeg je korisnik odabrao putem spinnera**/
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		if (parent == (Spinner) findViewById(R.id.spPregledPKolegiji)) {
			SQLiteDatabase myDB = null;
			myDB = this.openOrCreateDatabase(MY_DATABASE_NAME, 1, null);
			id_kolegija = DataAdapterKolegij.idKolegija(myDB, parent, position);
		}
		if (parent == (Spinner) findViewById(R.id.spOdabirPrikaza)) {
			id_prikaza = parent.getSelectedItemPosition();
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**dugim pritiskom na element liste se otvara izbornik**/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(R.string.dbContextMenuTitle);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
	}

	/**metoda koja se pokrece nakon sto korisnik odabere jednu od ponudenih opcija izbornika**/ 
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.dbBrisanje) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			View view = info.targetView;
			switch (id_prikaza) {
			//ako je u spinneru odabran prikaz biljeski, brise se biljeska
			case 0:
				TextView tvIdBiljeske = (TextView) view.findViewById(R.id.idBiljeske);
				int idBiljeske = Integer.parseInt(tvIdBiljeske.getText().toString());
				DataAdapterBiljeske daBiljeske = new DataAdapterBiljeske(getBaseContext());
				daBiljeske.openToWrite();
				daBiljeske.deleteBiljeska(idBiljeske);
				daBiljeske.close();
				break;
			//ako je u spinneru odabran prikaz evidencija, brise se evidencija
			case 1:
				TextView tvIdEvidencije = (TextView) view
				.findViewById(R.id.idEvidencije);
				int idEvidencije = Integer.parseInt(tvIdEvidencije.getText()
						.toString());
				DataAdapterEvidencija daEvidencija = new DataAdapterEvidencija(
						getBaseContext());
				daEvidencija.openToWrite();
				daEvidencija.deleteEvidencija(idEvidencije);
				daEvidencija.close();
				break;
			//ako je u spinneru odabran prikaz bodova, brisu se bodovi
			case 2:
				TextView tvIdBodovi = (TextView) view.findViewById(R.id.idBodova);
				int idBoda = Integer.parseInt(tvIdBodovi.getText().toString());
				DataAdapterBodovi daBodovi = new DataAdapterBodovi(getBaseContext());
				daBodovi.openToWrite();
				daBodovi.deleteBodovi(idBoda);
				daBodovi.close();
				break;
			}
		}
		return true;
	}

}
