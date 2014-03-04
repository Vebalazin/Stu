package hr.foi.air.StudentAssistant;

import hr.foi.air.StudentAssistant.R;
import hr.foi.air.StudentAssistant.database.DataAdapterKolegij;
import hr.foi.air.StudentAssistant.database.DataAdapterStavkaRasporeda;
import hr.foi.air.StudentAssistant.types.StavkaRasporeda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PregledRActivity extends Activity implements OnItemSelectedListener{
	Spinner spDani;
	String dan;
	Context c;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c=this;
		setContentView(R.layout.activity_pregledr);
		ispisDana(); //metoda za popunjavanje spinnera danima u tjednu
		spDani.setOnItemSelectedListener(this);
		PrikaziPodatke(); //metoda za prikaz stavki rasporeda za odabrani dan
	}

	/**metoda koja popunjava spinner s danima u tjednu**/
	private void ispisDana(){
		spDani = (Spinner)findViewById(R.id.spOdabirDanaPregledR);
		ArrayAdapter<String> sp = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDani.setAdapter(sp);
		String[] dani= {"Ponedjeljak","Utorak","Srijeda","Èetvrtak","Petak","Subota"};
		for (int i=0;i<6;i++){
			sp.add(dani[i]);
		}	
	}
	
	/**metoda koja popunjava listu sa stavkama rasporeda**/
	private void PrikaziPodatke(){
		Button b = (Button)this.findViewById(R.id.btnOsvjeziStavke);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//ListView lv; // referenca na ListView objekt
				String[] from; // nazivi atributa koji se mapiraju
				int[] to; // nazivi elemenata predloška u koje se mapira
				HashMap<String, String> mapa; 							
				List<HashMap<String, String>> fillMaps; // lista mapa (veza)
				List<StavkaRasporeda> listaStavki; // lista StavkaRasporeda objekata
				Iterator<StavkaRasporeda> itr; // standardni iterator
				SimpleAdapter adapter; // adapter za prikaz podataka na formi
				// dohvat podataka iz baze
				DataAdapterStavkaRasporeda da = new DataAdapterStavkaRasporeda(getBaseContext());
				da.openToRead();
				//dohvaæanje svih stavki za dan koji je odabran u spinneru spOdabirDanaPregledR
				listaStavki = da.getAllStavke(dan);
				da.close();
				// definiranje atributa za veze
				from = new String[] { "id_stavke","id_kolegija", "vrijeme_p","vrijeme_k","dvorana", "nastava" };
				to = new int[] { R.id.idStavke,R.id.idKolegija, R.id.vrijeme_p, R.id.vrijeme_k, R.id.dvorana,R.id.nastava};
				// kreiranje mapa
				fillMaps = new ArrayList<HashMap<String, String>>();
				itr = listaStavki.iterator();
				DataAdapterKolegij dk = new DataAdapterKolegij(getBaseContext());
				dk.openToRead();
				//sve dok postoje elementi u listi popunjavaj listu s trazenim podacima
				while (itr.hasNext()) {
					mapa = new HashMap<String, String>();
					StavkaRasporeda stavka = itr.next();
					mapa.put("id_stavke",""+stavka.getIdStavke());
					int id = stavka.getIdKolegija();
					mapa.put("id_kolegija",getNaziv(id));
					dk.close();
					mapa.put("vrijeme_p","Vrijeme:  "+ stavka.getVrijemeP());
					mapa.put("vrijeme_k","-"+ stavka.getVrijemeK());
					mapa.put("dvorana",", dv:"+ stavka.getDvorana().toString());
					mapa.put("nastava", stavka.getNastava()+":");
					fillMaps.add(mapa);
				}
				// dohvat i brisanje liste
				lv = (ListView) findViewById(R.id.lvStavkaRasporeda);
				lv.invalidateViews();
				// generiranje podataka za prikaz u list view-u
				adapter = new SimpleAdapter(c, fillMaps, R.layout.stavka_data,from, to);
				lv.setAdapter(adapter);
				registerForContextMenu(lv);
			}
		});}

	//nakon sto korisnik odabere dan u spinneru, dan se pohranjuje u varijablu dan kako bi se kod ispisa stavki
	//ispisale stavke pojedinog dana
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		dan=spDani.getSelectedItem().toString();

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	
	/**metoda koja vraæa naziv kolegija, koristi se kod ispisa stavki jer zelimo ispisati naziv kolegija,a ne njegov id**/
	public String getNaziv(int id){
		SQLiteDatabase myDB = null;
		myDB = this.openOrCreateDatabase("database.db", 1, null);
		//u varijablu naziv se pohranjuje naziva kolegija koju vraæa metoda NazivKolegija
		//navedena metoda uzima id kolegija i nakon pretrage tablice kolegij vraca naziv odredenog kolegija
		String naziv = DataAdapterKolegij.NazivKolegija(myDB,id);
		myDB.close();
		return naziv;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(R.string.dbContextMenuTitle);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		//super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.dbBrisanje) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			View view = info.targetView;
			TextView tvId = (TextView) view.findViewById(R.id.idStavke);
			int id = Integer.parseInt(tvId.getText().toString());
			DataAdapterStavkaRasporeda da = new DataAdapterStavkaRasporeda(getBaseContext());
			da.openToWrite();
			da.deleteStavka(id);
			da.close();			
		}
		return true;
	}

}