package hr.foi.air.StudentAssistant;

import java.util.ArrayList;
import hr.foi.air.StudentAssistant.R;
import hr.foi.air.StudentAssistant.database.DataAdapterBiljeske;
import hr.foi.air.StudentAssistant.database.DataAdapterBodovi;
import hr.foi.air.StudentAssistant.database.DataAdapterEvidencija;
import hr.foi.air.StudentAssistant.database.DataAdapterKolegij;
import hr.foi.air.StudentAssistant.types.Biljeska;
import hr.foi.air.StudentAssistant.types.Bodovi;
import hr.foi.air.StudentAssistant.types.Evidencija;
import hr.foi.air.StudentAssistant.types.Kolegij;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UnosPodatakaActivity extends Activity implements OnItemSelectedListener {
	String MY_DATABASE_NAME = "database.db";
	String MY_DATABASE_TABLE = "kolegij";
	TextView resultsView;
	Spinner spKolegij,spEvidencija;
	Context c;
	ArrayAdapter<String> adapterForSpinner;
	ArrayList<Kolegij> kolegij;
	int id_kolegija;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unosp);
		c=this;
		ispisKolegija();//metoda za popunjavanje spinnera unesenim kolegijima
		ispisEvidencija();//metoda za popunjavanje spinnera mogucim oblicima evidencije:prisutan,odsutan
		spremiBiljesku();//metoda za pohranu biljeske
		spremiEvidenciju();//metoda za pohranu evidencije
		spremiBodove();//metoda za pohranu bodova
		spKolegij.setOnItemSelectedListener(this);
	}

	/**popunjava spinner spKolegiji s upisanim kolegijima**/
	private void ispisKolegija() {

		spKolegij = (Spinner) findViewById(R.id.spKolegij);
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

	/**popunjava spinner spEvidencija s moguæim evidencijama - prisutan i neprisutan**/ 
	private void ispisEvidencija(){
		spEvidencija = (Spinner)findViewById(R.id.spEvidencija);
		ArrayAdapter<String> sp = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spEvidencija.setAdapter(sp);
		sp.add("Prisutan");
		sp.add("Odsutan");	
	}

	/**metoda za pohranu biljeski u bazu **/
	private void spremiBiljesku(){
		Button b = (Button)this.findViewById(R.id.btnSpremiBiljesku);
		//na gumb se postavlja listener kako bi aplikacija izvrsila neki dio koda nakon sto korisnik klikne na njega
		b.setOnClickListener(new OnClickListener() {
			EditText etBiljeska= (EditText)findViewById(R.id.etBiljeska);
			//metoda onClick se pokrece prilikom pritiska gumba
			public void onClick(View v) {
				String naziv = etBiljeska.getText().toString();
				//provjera da li su upisani svi podaci 
				if(naziv.compareTo("")==0){
					Toast.makeText(c, "Neispravan unos!Unesite sve podatke!", Toast.LENGTH_SHORT).show();
				}
				//ako je korisnik upisao sve podatke, podaci se pohranjuju pomoæu odgovarajuæe metode DataAdapter klase
				else{
					DataAdapterBiljeske da = new DataAdapterBiljeske(c);
					da.openToWrite();
					da.insertBiljeska(new Biljeska(id_kolegija,naziv));
					da.close();
					Toast.makeText(c, "Biljeska je spremljena!", Toast.LENGTH_SHORT).show();
					etBiljeska.setText("");
					spremiTipkovnicu(etBiljeska);
				}
			}
		});
	}

	/**metoda za pohranu evidencije u bazu**/ 
	private void spremiEvidenciju(){
		Button b = (Button)this.findViewById(R.id.btnSpremiEvidenciju);
		//na gumb se postavlja listener kako bi aplikacija izvrsila neki dio koda nakon sto korisnik klikne na njega
		b.setOnClickListener(new OnClickListener() {
			//metoda onClick se pokrece prilikom pritiska gumba
			public void onClick(View v) {
				DatePicker dt = (DatePicker)findViewById(R.id.datePicker1);
				int dan,mj,god;
				//dohvaæanje dana iz datepickera
				dan=dt.getDayOfMonth();
				//dohvaæanje mjeseca iz datepickera; mjesecu se dodaje 1 jer u dp-u je sijeèanj 0, veljaèa 1 itd.
				mj=dt.getMonth()+1;
				//dohvaæanje dana iz datepickera
				god=dt.getYear();
				String datum=dan+"."+mj+"."+god+".";
				DataAdapterEvidencija da = new DataAdapterEvidencija(c);
				da.openToWrite();
				// podaci se pohranjuju u tablicu evidencija
				da.insertEvidencija(new Evidencija(id_kolegija, spEvidencija.getSelectedItem().toString(), datum));
				Toast.makeText(c, "Evidencija je spremljena!", Toast.LENGTH_SHORT).show();
				da.close();
			}
		});

	}
	
	/**metoda za pohranu bodova u bazu**/ 
	private void spremiBodove(){
		Button b = (Button)this.findViewById(R.id.btnSpremiBodove);
		//na gumb se postavlja listener kako bi aplikacija izvrsila neki dio koda nakon sto korisnik klikne na njega
		b.setOnClickListener(new OnClickListener() {
			EditText etBod= (EditText)findViewById(R.id.etBodovi);
			EditText etOznaka= (EditText)findViewById(R.id.etOznakaBodova);
			//metoda onClick se pokrece prilikom pritiska gumba
			public void onClick(View v) {
				String oznaka = etOznaka.getText().toString();
				String bod = etBod.getText().toString();
				//provjera da li su upisani svi podaci 
				if(bod.compareTo("")==0 || oznaka.compareTo("")==0 ){
					Toast.makeText(c, "Neispravan unos!Unesite sve podatke!", Toast.LENGTH_SHORT).show();
				}
				//ako je korisnik upisao sve podatke, podaci se pohranjuju pomoæu odgovarajuæe metode DataAdapter klase
				else{
					DataAdapterBodovi da = new DataAdapterBodovi(c);
					da.openToWrite();
					da.insertBodovi(new Bodovi(id_kolegija,Float.valueOf(bod),oznaka));
					Toast.makeText(c, "Bodovi su spremljeni!", Toast.LENGTH_SHORT).show();
					etOznaka.setText("");
					etBod.setText("");
					spremiTipkovnicu(etBod);
					da.close();
				}

			}
		});
	}
	
	/**nakon sto korisnik odabere kolegij u spinneru, njegov id se pohranjuje u globalnu varijablu id_kolegija**/
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		SQLiteDatabase myDB = null;
		myDB = this.openOrCreateDatabase(MY_DATABASE_NAME, 1, null);
		//metoda idKolegija pretrazuje tablicu kolegiji i kad nade kolegij ciji naziv je jednak odabranom kolegiju
		//u spinneru, pohranjuje njegov id u varijablu id_kolegija
		id_kolegija= DataAdapterKolegij.idKolegija(myDB,parent,position); 
		myDB.close();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	/**metoda sprema tipkovnicu da ne smeta korisniku**/
	private void spremiTipkovnicu(EditText et) {
		InputMethodManager imm = (InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}
}