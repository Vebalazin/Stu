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
import hr.foi.air.StudentAssistant.database.DataAdapterStavkaRasporeda;
import hr.foi.air.StudentAssistant.types.Kolegij;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.app.Activity;
import android.content.Context;

public class KolegijActivity extends Activity {
	private Context context;
	DataAdapterKolegij da;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kolegij);
		context = this;
		save();
		cancel();
		da = new DataAdapterKolegij(getBaseContext());
		ispisKolegija();
	}

	/**metoda koja sprema upisani kolegij**/
	private void save() {
		Button b = (Button) this.findViewById(R.id.btnSave);
		b.setOnClickListener(new OnClickListener() {
			EditText txtnaziv = (EditText) findViewById(R.id.txtNazivKolegija);

			//klikom na gumb btnSave se pohranjuje uneseni kolegij
			public void onClick(View v) {
				String naziv = txtnaziv.getText().toString();
				DataAdapterKolegij da = new DataAdapterKolegij(context);
				da.openToWrite();
				//pohrana kolegija u tablicu u bazi
				da.insertKolegij(new Kolegij(naziv));
				da.close();
				Toast.makeText(context, "Kolegij " + naziv + " je dodan!",Toast.LENGTH_SHORT).show();
				txtnaziv.setText("");
				//osvjezavanje prikaza liste kolegija
				ispisKolegija();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtnaziv.getWindowToken(), 0);
			}
		});

	}

	/**pritiskom na gumb btnCancel, metoda vraæa korisnika na pocetni izbornik (zatvara trenutnu aktivnost)**/
	private void cancel() {
		Button b = (Button) findViewById(R.id.btnCancel);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Zatvara aktivnost i vraæa korisnika na poèetni izbornik
				finish();
			}
		});
	}
	
	/**metoda za ispis svih kolegija u listi**/
	private void ispisKolegija() {
		ListView lv; // referenca na ListView objekt
		String[] from; // nazivi atributa koji se mapiraju
		int[] to; // nazivi elemenata predloška u koje se mapira
		HashMap<String, String> mapa;
		List<HashMap<String, String>> fillMaps; // lista mapa (veza)
		List<Kolegij> lista; // lista Kolegij objekata
		Iterator<Kolegij> itr; // standardni iterator
		SimpleAdapter adapter; // adapter za prikaz podataka na formi
		// dohvat podataka iz baze
		da.openToRead();
		// dohvaæanje svih kolegija 
		lista = da.getAllKolegiji();
		da.close();
		// definiranje atributa za veze
		from = new String[] { "ID_kolegija", "naziv" };
		to = new int[] { R.id.idKolegija, R.id.kolegij };
		// kreiranje mapa
		fillMaps = new ArrayList<HashMap<String, String>>();
		itr = lista.iterator();
		while (itr.hasNext()) {
			mapa = new HashMap<String, String>();
			Kolegij kolegij = itr.next();
			mapa.put("ID_kolegija", "" + kolegij.getId());
			mapa.put("naziv", kolegij.getName());
			fillMaps.add(mapa);
		}
		// dohvat i brisanje liste
		lv = (ListView) findViewById(R.id.lvKolegiji);
		lv.invalidateViews();
		// generiranje podataka za prikaz u list view-u
		adapter = new SimpleAdapter(context, fillMaps, R.layout.kolegij_data,
				from, to);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);

	}

	/**pokretanje kontekstualnog izbornika**/
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
			TextView tvIdKolegija = (TextView) view
					.findViewById(R.id.idKolegija);
			int idKolegija = Integer
					.parseInt(tvIdKolegija.getText().toString());
			DataAdapterKolegij da = new DataAdapterKolegij(getBaseContext());
			da.openToWrite();
			//brise se odabrani kolegij
			da.deleteKolegij(idKolegija);
			da.close();
			DataAdapterEvidencija dk = new DataAdapterEvidencija(getBaseContext());
			dk.openToWrite();
			//brisu se pripadajuce evidencije izbrisanog kolegija
			dk.deleteEvidencijaKolegij(idKolegija);
			dk.close();
			DataAdapterBodovi db = new DataAdapterBodovi(getBaseContext());
			db.openToWrite();
			//brisu se pripadajuci bodovi
			db.deleteBodoviKolegij(idKolegija);
			db.close();
			DataAdapterBiljeske dbilj = new DataAdapterBiljeske(getBaseContext());
			dbilj.openToWrite();
			//brisu se pripadajuce biljeske
			dbilj.deleteBiljeskaKolegij(idKolegija);
			dbilj.close();
			DataAdapterStavkaRasporeda ds= new DataAdapterStavkaRasporeda(getBaseContext());
			ds.openToWrite();
			//brisu se pripadajuce stavke
			ds.deleteStavkaKolegij(idKolegija);
			ds.close();
			ispisKolegija();

		}
		return true;
	}
}
