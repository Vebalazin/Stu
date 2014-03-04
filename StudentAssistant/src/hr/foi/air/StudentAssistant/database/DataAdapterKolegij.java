package hr.foi.air.StudentAssistant.database;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.StudentAssistant.types.Kolegij;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class DataAdapterKolegij {
	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE = "kolegij";
	public static final String KEY_ID = "ID_kolegija";
	private DBHelper sqLiteHelper;
	private static SQLiteDatabase sqLiteDatabase;
	private Context context;

	public DataAdapterKolegij(Context c) {
		context=c;
	}

	/**metoda koja otvara bazu za citanje**/
	public DataAdapterKolegij openToRead() throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	/**metoda koja otvara bazu za pisanje**/
	public DataAdapterKolegij openToWrite() throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	/**metoda koja zatvara bazu**/
	public void close() {
		sqLiteHelper.close();
	}

	/**metoda pohranjuje kolegij u bazu**/
	public long insertKolegij(Kolegij kolegij) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("naziv", kolegij.getName());
		return sqLiteDatabase.insert(TABLE, null, contentValues);
	}

	/**metoda za popunjavanje spinnera s nazivima upisanih kolegija**/
	public static void populateSpinner (ArrayAdapter<String> adapterForSpinner, SQLiteDatabase myDB, Context con){
		Cursor c = myDB.query(TABLE, null, null, null, null, null,null);
		int nazivInd = c.getColumnIndexOrThrow("naziv");
		if (c.moveToFirst()) {
			do {
				adapterForSpinner.add(c.getString(nazivInd));
			} while (c.moveToNext());
			if (myDB != null) {
				myDB.close();
			}
		} 
		c.close();

	}

	/**metoda koja, ovisno o odabranom nazivu kolegija u spinneru, vraæa id odabranog kolegija**/
	public static int idKolegija(SQLiteDatabase myDB,AdapterView<?> parent, int position){
		Cursor c = myDB.query(TABLE, null, null, null, null, null,
				null);
		int nazivInd = c.getColumnIndexOrThrow("naziv");
		int id = c.getColumnIndexOrThrow("ID_kolegija");
		int id_kolegija= 0;
		//cursor prolazi redovima u tablici kolegija 
		if (c.moveToFirst()) {
			do {
				//usporeduje se naziv kolegija u redu u kojem se nalazi cursor s kolegijom koji je odabran u spinneru
				//kada u tablici pronade trazeni naziv kolegija, njegov id se zapisuje u varijablu id_kolegija
				if (c.getString(nazivInd).compareTo(parent.getItemAtPosition(position).toString())==0){
					id_kolegija= c.getInt(id);
				}

			} while (c.moveToNext());
			if (myDB != null) {
				myDB.close();
			}
		}
		c.close();
		return id_kolegija;

	}

	/**metoda koja vraæa naziv kolegija, ovisno o ulaznom parametru id kolegija**/
	public static String NazivKolegija(SQLiteDatabase myDB,int id_kolegija){
		Cursor c = myDB.query(TABLE, null, null, null, null, null,
				null);
		String naziv= "";
		//cursor prolazi redovima u tablici kolegija 
		if (c.moveToFirst()) {
			do {
				//ako je id_kolegija jednak vrijednosti u kolumni ID_kolegij, pohrani naziv tog kolegija u varijablu naziv
				if (c.getInt(c.getColumnIndex("ID_kolegija"))==id_kolegija){
					naziv = c.getString(c.getColumnIndex("naziv"));		
				}

			} while (c.moveToNext());
			if (myDB != null) {
				myDB.close();
			}
		}
		c.close();
		myDB.close();
		return naziv;

	}

	/**metoda za brisanje odreðenog kolegija, ovisno o njegovom id-u**/
	public boolean deleteKolegij(int idKolegija){
		return sqLiteDatabase.delete(TABLE, KEY_ID + "=" + idKolegija, null) > 0;
	}

	/**metoda za ispis svih kolegija u listu**/
	public List<Kolegij> getAllKolegiji() {
		List<Kolegij> result = new ArrayList<Kolegij>();
		String[] columns = new String[] { "ID_kolegija","naziv" };
		Cursor cursor = sqLiteDatabase.query(TABLE, columns, null, null, null, null,
				null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("ID_kolegija"));
			String naziv = cursor.getString(cursor.getColumnIndex("naziv"));
			Kolegij kolegij = new Kolegij(id, naziv);
			result.add(kolegij);
		}

		cursor.close();
		return result;
	}
}


