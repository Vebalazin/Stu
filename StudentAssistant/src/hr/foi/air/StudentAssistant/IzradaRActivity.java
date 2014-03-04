package hr.foi.air.StudentAssistant;

import java.util.ArrayList;
import hr.foi.air.StudentAssistant.R;
import hr.foi.air.StudentAssistant.database.DataAdapterKolegij;
import hr.foi.air.StudentAssistant.database.DataAdapterStavkaRasporeda;
import hr.foi.air.StudentAssistant.types.Kolegij;
import hr.foi.air.StudentAssistant.types.StavkaRasporeda;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class IzradaRActivity extends Activity implements OnItemSelectedListener {
	String MY_DATABASE_NAME = "database.db";
	String MY_DATABASE_TABLE = "kolegij";
	Spinner spKolegij, spDani, spNastava;
	ArrayAdapter<String> adapterForSpinner;
	ArrayList<Kolegij> kolegij;
	int id_kolegija;
	String dan, nastava;
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_izradar);
		c = this;
		ispisKolegija();/**metoda za popunjavanje spinnera unesenim kolegijima**/
		ispisDana(); /**metoda za popunjavanje spinnera danima u tjednu**/
		ispisNastave();/**metoda za popunjavanje spinnera oblicima nastave**/
		spremiStavku();/**metoda za pohranu stavke**/
		spKolegij.setOnItemSelectedListener(this);
		spDani.setOnItemSelectedListener(this);
		spNastava.setOnItemSelectedListener(this);

	}

	/**metoda za popunjavanje spinnera unesenim kolegija**/
	private void ispisKolegija() {
		spKolegij = (Spinner) findViewById(R.id.spOdabirKolegijaUnosR);
		SQLiteDatabase myDB = null;
		myDB = this.openOrCreateDatabase(MY_DATABASE_NAME, 1, null);
		adapterForSpinner = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapterForSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spKolegij.setAdapter(adapterForSpinner);
		DataAdapterKolegij.populateSpinner(adapterForSpinner, myDB, c);
		myDB.close();

	}

	/**metoda za popunjavanje spinnera danima u tjednu**/
	private void ispisDana() {
		spDani = (Spinner) findViewById(R.id.spOdabirDanaUnosR);
		ArrayAdapter<String> sp = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDani.setAdapter(sp);
		String[] dani = { "Ponedjeljak", "Utorak", "Srijeda", "Èetvrtak",
				"Petak", "Subota" };
		for (int i = 0; i < 6; i++) {
			sp.add(dani[i]);
		}

	}

	/**metoda za popunjavanje spinnera moguæim oblicima nastave**/
	private void ispisNastave() {
		spNastava = (Spinner) findViewById(R.id.spOdabirNastaveUnosR);
		ArrayAdapter<String> sp = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spNastava.setAdapter(sp);
		String[] nastava = { "Predavanja", "Seminar", "Auditorne vj.",
				"Laboratorijske vj." };
		for (int i = 0; i < 4; i++) {
			sp.add(nastava[i]);
		}

	}

	/**metoda za pohranu stavke u bazu**/
	private void spremiStavku() {
		Button b = (Button) this.findViewById(R.id.btnSpremiStavku);
		b.setOnClickListener(new OnClickListener() {
			EditText etDvorana = (EditText) findViewById(R.id.etUnosDvorane);
			EditText etVrijemeP = (EditText) findViewById(R.id.etVrijemeP);
			EditText etVrijemeK = (EditText) findViewById(R.id.etVrijemeK);

			public void onClick(View v) {
				//dohvaæamo unesene podatke
				String dvorana = etDvorana.getText().toString();
				String vrijeme_p = etVrijemeP.getText().toString();
				String vrijeme_k = etVrijemeK.getText().toString();
				//provjera da li su uneseni svi podaci,ako nisu korisniku se ispisuje pogreska
				if (dvorana.compareTo("") == 0 || vrijeme_p.compareTo("") == 0
						|| vrijeme_k.compareTo("") == 0) {
					Toast.makeText(c, "Neispravan unos!Unesite sve podatke!",
							Toast.LENGTH_SHORT).show();
					//ako su podaci uneseni ispravno, stavka se pohranjuje u tablicu u bazi
				} else {
					DataAdapterStavkaRasporeda da = new DataAdapterStavkaRasporeda(
							c);
					da.openToWrite();
					da.insertStavka(new StavkaRasporeda(id_kolegija, vrijeme_p,
							vrijeme_k, Integer.valueOf(dvorana), dan, nastava));
					da.close();
					Toast.makeText(c, "Stavka je spremljena!",
							Toast.LENGTH_SHORT).show();
					etDvorana.setText("");
					etVrijemeP.setText("");
					etVrijemeK.setText("");
				}
			}
		});
	}

	/**metoda koja se pokrece nakon sto korisnik odabere odredeni element bilokojeg spinnera**/
	//pohranjuje odabrani element u odgovarajucu globalnu varijablu
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		//provjeramo da li je korisnik odabrao neki element spinnera koji sadrzi kolegije
		if (parent == (Spinner) findViewById(R.id.spOdabirKolegijaUnosR)) {
			SQLiteDatabase myDB = null;
			myDB = this.openOrCreateDatabase(MY_DATABASE_NAME, 1, null);
			//umjesto naziva odabranog kolegija, u varijablu se pohranjuje njegov id
			id_kolegija = DataAdapterKolegij.idKolegija(myDB, parent, position);
		}
		//provjeramo da li je korisnik odabrao neki element spinnera koji sadrzi dane tjedna
		if (parent == (Spinner) findViewById(R.id.spOdabirDanaUnosR)) {
			dan = parent.getSelectedItem().toString();
		}
		//provjeramo da li je korisnik odabrao tip nastave u spinneru koji sadrzi tipove nastave
		if (parent == (Spinner) findViewById(R.id.spOdabirNastaveUnosR)) {
			nastava = parent.getSelectedItem().toString();
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
