package hr.foi.air.StudentAssistant;

import hr.foi.air.StudentAssistant.R;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PodaciActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podaci);
	}

	
	/** ako je korisnik unio kolegije - pokreæe se jedna od aktivnosti, ovisno o pritisnutom buttonu**/
	public void openUnosP(View target) {
		// ako korisnik nije unio kolegije - ispisuje se poruka
		if (checkData() == false) {
			Toast.makeText(this, "Unesite prvo kolegije!", Toast.LENGTH_LONG)
			.show();
			finish();
		} else {
			Intent i = new Intent(this, UnosPodatakaActivity.class);
			startActivity(i);
		}

	}

	public void openPregledP(View target) {
		if (checkData() == false) {
			Toast.makeText(this, "Unesite prvo kolegije!", Toast.LENGTH_LONG)
			.show();
			finish();
		} else {
			Intent i = new Intent(this, PregledPodatakaActivity.class);
			startActivity(i);
		}
	}

	/**metoda provjerava da li je korisnik unio barem jedan kolegij**/
	public boolean checkData() {
		SQLiteDatabase myDB = null;
		try {
			myDB = this.openOrCreateDatabase("database.db", 1, null);
			//u varijablu i se pohranjuje broj redova tablice kolegij
			int i = myDB.rawQuery("SELECT * FROM kolegij", null).getCount();
			myDB.close();
			//ako je i>0 tada u tablici postoji bar 1 zapis i metoda vraca true vrijednost
			if (i>0){
			   return true;
			}
			//inace, ne postoje zapisi u tablici kolegij i metoda vraca false
			else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

}
