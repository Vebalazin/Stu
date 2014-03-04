package hr.foi.air.StudentAssistant.database;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.StudentAssistant.types.Bodovi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataAdapterBodovi {

	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE = "bodovi";
	public static final String KEY_ID = "ID_bodova";
	private DBHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;

	public DataAdapterBodovi(Context c) {
		context = c;
	}

	/**metoda koja otvara bazu za citanje**/
	public DataAdapterBodovi openToRead()
			throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	/**metoda koja otvara bazu za pisanje**/
	public DataAdapterBodovi openToWrite()
			throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}
	
	/**metoda za zatvaranje baze**/
	public void close() {
		sqLiteHelper.close();
	}

	/**metoda pohranjuje bodove u bazu**/
	public long insertBodovi(Bodovi bodovi) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id_kolegija", bodovi.getIdKolegija());
		contentValues.put("bod", bodovi.getBod());
		contentValues.put("oznaka", bodovi.getOznaka());
		return sqLiteDatabase.insert(TABLE, null, contentValues);
	}

	/**metoda za ispis bodova u listu, ispisuje bodove iz odredenog kolegija - ovisno o ulaznom parametru idk (id-u kolegija)**/
	public List<Bodovi> getAllBodovi(int idk) {
		List<Bodovi> result = new ArrayList<Bodovi>();
		String[] columns = new String[] { "ID_bodova","id_kolegija", "bod", "oznaka" };
		Cursor cursor = sqLiteDatabase.query(TABLE, columns, null, null, null, null,
				null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			if(cursor.getInt(cursor.getColumnIndex("id_kolegija"))==idk){
				int id_boda=cursor.getInt(cursor.getColumnIndex("ID_bodova"));
				Float bod = cursor.getFloat(cursor.getColumnIndex("bod"));
				String oznaka = cursor.getString(cursor.getColumnIndex("oznaka"));
				int id = cursor.getInt(cursor.getColumnIndex("id_kolegija"));
				Bodovi bodovi = new Bodovi(id_boda,id, bod, oznaka);
				result.add(bodovi);
			}
		}
		cursor.close();
		return result;
	}

	/**metoda za brisanje bodova**/
	public boolean deleteBodovi(int idBodova){
		return sqLiteDatabase.delete(TABLE, KEY_ID + "=" + idBodova, null) > 0;
	}
	
	//metoda za brisanje bodova s odredenim id-om kolegija
	public boolean deleteBodoviKolegij(int id){
		return sqLiteDatabase.delete(TABLE, "id_kolegija" + "=" + id, null) > 0;
	}
}

