package hr.foi.air.StudentAssistant.database;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.StudentAssistant.types.Evidencija;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataAdapterEvidencija {

	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE = "evidencija";
	public static final String KEY_ID = "ID_evidencije";
	private DBHelper sqLiteHelper;
	private static SQLiteDatabase sqLiteDatabase;
	private Context context;

	public DataAdapterEvidencija(Context c) {
		context=c;
	}

	/**metoda koja otvara bazu za citanje**/
	public DataAdapterEvidencija openToRead() throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}
	
	/**metoda koja otvara bazu za pisanje**/
	public DataAdapterEvidencija openToWrite() throws android.database.SQLException {
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
	public long insertEvidencija(Evidencija evidencija) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id_kolegija", evidencija.getIdKolegija());
		contentValues.put("evidencija", evidencija.getEvidencija());
		contentValues.put("datum", evidencija.getDatum().toString());
		return sqLiteDatabase.insert(TABLE, null, contentValues);
	}

	/**metoda za ispis evidencija u listu, ispisuje evidencije iz odredenog kolegija - ovisno o ulaznom parametru idk (id-u kolegija)**/
	public List<Evidencija> getAllEvidencije(int idk) {
		List<Evidencija> result = new ArrayList<Evidencija>();
		String[] columns = new String[] { "ID_evidencije","id_kolegija", "evidencija", "datum" };
		Cursor cursor = sqLiteDatabase.query(TABLE, columns, null, null, null, null,
				null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			if(cursor.getInt(cursor.getColumnIndex("id_kolegija"))==idk){
				int id_evidencije=cursor.getInt(cursor.getColumnIndex("ID_evidencije"));
				String evid = cursor.getString(cursor.getColumnIndex("evidencija"));
				String datum = cursor.getString(cursor.getColumnIndex("datum"));
				int id = cursor.getInt(cursor.getColumnIndex("id_kolegija"));
				Evidencija evidencija = new Evidencija(id_evidencije,id, evid, datum);
				result.add(evidencija);
			}
		}
		cursor.close();
		return result;
	}

	/**metoda za brisanje evidencije**/
	public boolean deleteEvidencija(int idEvidencije){
		return sqLiteDatabase.delete(TABLE, KEY_ID + "=" + idEvidencije, null) > 0;
	}
	
	//metoda za brisanje evidencija s odredenim id-om kolegija
	public boolean deleteEvidencijaKolegij(int id){
		return sqLiteDatabase.delete(TABLE, "id_kolegija" + "=" + id, null) > 0;
	}

}
