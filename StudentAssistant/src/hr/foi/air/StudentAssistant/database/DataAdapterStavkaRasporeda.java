package hr.foi.air.StudentAssistant.database;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.StudentAssistant.types.StavkaRasporeda;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataAdapterStavkaRasporeda {

	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE = "stavka_rasporeda";
	public static final String KEY_ID = "ID_stavke";
	private DBHelper sqLiteHelper;
	private static SQLiteDatabase sqLiteDatabase;
	private Context context;

	public DataAdapterStavkaRasporeda(Context c) {
		context=c;
	}

	/**metoda koja otvara bazu za citanje**/
	public DataAdapterStavkaRasporeda openToRead() throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	/**metoda koja otvara bazu za pisanje**/
	public DataAdapterStavkaRasporeda openToWrite() throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	/**metoda za zatvaranje baze**/
	public void close() {
		sqLiteHelper.close();
	}

	/**metoda pohranjuje evidenciju u bazu**/
	public long insertStavka(StavkaRasporeda stavkaRaspreda) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id_kolegija", stavkaRaspreda.getIdKolegija());
		contentValues.put("vrijeme_p", stavkaRaspreda.getVrijemeP().toString());
		contentValues.put("vrijeme_k", stavkaRaspreda.getVrijemeK().toString());
		contentValues.put("dvorana", stavkaRaspreda.getDvorana());
		contentValues.put("dan", stavkaRaspreda.getDan());
		contentValues.put("nastava", stavkaRaspreda.getNastava());
		return sqLiteDatabase.insert(TABLE, null, contentValues);
	}

	
	/**metoda za ispis stavki rasporeda u listu, stavke se ispisuju za odredeni dan (ovisno o ul. parametru danStavke)**/
	public List<StavkaRasporeda> getAllStavke(String danStavke) {
		List<StavkaRasporeda> result = new ArrayList<StavkaRasporeda>();
		Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE+" ORDER BY vrijeme_p ASC", null);

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			//provjeramo da li su stringovi:vrijednost kolumne dan u tablici i ulazni parametar danStavke jednaki
			//ako jesu, metode compareTo vraæa 0 te dodajemo stavku u listu
			if(cursor.getString(cursor.getColumnIndex("dan")).compareTo(danStavke)==0){
				int id=cursor.getInt(cursor.getColumnIndex("ID_stavke"));
				String vrijeme_p = cursor.getString(cursor.getColumnIndex("vrijeme_p"));
				String vrijeme_k = cursor.getString(cursor.getColumnIndex("vrijeme_k"));
				int dvorana = cursor.getInt(cursor.getColumnIndex("dvorana"));
				String dan = cursor.getString(cursor.getColumnIndex("dan"));
				int idKolegija = cursor.getInt(cursor.getColumnIndex("id_kolegija"));
				String nastava = cursor.getString(cursor.getColumnIndex("nastava"));
				StavkaRasporeda stavka = new StavkaRasporeda(id, idKolegija, vrijeme_p, vrijeme_k, dvorana, dan, nastava);
				result.add(stavka);
			}
		}
		cursor.close();
		return result;
	}

	/**metoda za brisanje stavke rasporeda**/
	public boolean deleteStavka(int idStavke){
		return sqLiteDatabase.delete(TABLE, KEY_ID + "=" + idStavke, null) > 0;
	}
	
	//metoda za brisanje stavki s odredenim id-om kolegija
	public boolean deleteStavkaKolegij(int id){
		return sqLiteDatabase.delete(TABLE, "id_kolegija" + "=" + id, null) > 0;
	}

}



