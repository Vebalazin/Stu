package hr.foi.air.StudentAssistant;

import hr.foi.air.StudentAssistant.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**pokrece se nova aktinost, ovisno o kliknutom gumbu**/
	public void openKolegij(View target) {
		Intent i = new Intent(this, KolegijActivity.class);
		startActivity(i);
	}

	public void openRaspored(View target) {
		Intent i = new Intent(this, RasporedActivity.class);
		startActivity(i);
	}

	public void openPodaci(View target) {
		Intent i = new Intent(this, PodaciActivity.class);
		startActivity(i);
	}

	public void openObavijesti(View target) {
		Intent i = new Intent(this, ObavijestiActivity.class);
		startActivity(i);
	}
}
