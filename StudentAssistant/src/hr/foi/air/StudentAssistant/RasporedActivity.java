package hr.foi.air.StudentAssistant;

import hr.foi.air.StudentAssistant.R;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RasporedActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raspored);
	}
	/**metoda koja pokrece aktivnost za izradu rasporeda**/
	public void openIzradaR(View target){
		Intent i = new Intent(this, IzradaRActivity.class);
		startActivity(i);
	}
	public void openPregledR(View target){
		//ako ne postoje stavke ili kolegiji, obavijesti korisnika da ih treba unijeti
		if (checkData() == false) {
			Toast.makeText(this, "Unesite prvo stavke! Takoðer, morate imati bar 1 uneseni kolegij!", Toast.LENGTH_LONG)
			.show();
		//inace, pokreni aktivnost za pregled rasporeda
		} else {
			Intent i = new Intent(this, PregledRActivity.class);
			startActivity(i);
		}
	}
	
	/**metoda koja provjerava da li postoje stavke u rasporedu**/
	public boolean checkData() {
		SQLiteDatabase myDB = null;
		try {
		    myDB = this.openOrCreateDatabase("database.db", 1, null);
		    //u varijablu i se pohranjuje broj zapisa tablice stavka_rasporeda
			int i = myDB.rawQuery("SELECT * FROM stavka_rasporeda", null).getCount();
			int j=myDB.rawQuery("SELECT * FROM kolegij", null).getCount();
			myDB.close();
			//ako je i>0 tada u tablici postoji bar 1 zapis i metoda vraca true vrijednost
			if (i > 0 && j>0){
				return true;
			}
			//inace, ne postoje stavke rasporeda i metoda vraca false
			else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
}
