package hr.foi.air.StudentAssistant.database;

import java.util.ArrayList;
import java.util.List;
import hr.foi.air.StudentAssistant.types.Biljeska;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataAdapterBiljeske {
	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE = "biljeska";
	public static final String KEY_ID = "ID_biljeske";
	private DBHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;

	public DataAdapterBiljeske(Context c) {
		context = c;
	}

	/**metoda koja otvara bazu za citanje**/
	public DataAdapterBiljeske openToRead()
			throws android.database.SQLException {
		sqLiteHelper = new DBHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	/**metoda koja otvara bazu za pisanje**/
	public DataAdapterBiljeske openToWrite()
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

	/**metoda za pohranu biljeske u bazu**/
	public long insertBiljeska(Biljeska biljeska) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("id_kolegija", biljeska.getIdKolegija());
		contentValues.put("biljeska", biljeska.getBiljeska());
		return sqLiteDatabase.insert(TABLE, null, contentValues);
	}

	/**metoda za dohvaæanje svih biljeski odreðenog kolegija**/
	public List<Biljeska> getAllBiljeske(int idk) {
		List<Biljeska> result = new ArrayList<Biljeska>();
		String[] columns = new String[] { "ID_biljeske","id_kolegija", "biljeska" };
		Cursor cursor = sqLiteDatabase.query(TABLE, columns, null, null, null, null, null);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			if(cursor.getInt(cursor.getColumnIndex("id_kolegija"))==idk){
				int id_biljeske=cursor.getInt(cursor.getColumnIndex("ID_biljeske"));
				String name = cursor.getString(cursor.getColumnIndex("biljeska"));
				int id_kolegija = cursor.getInt(cursor.getColumnIndex("id_kolegija"));
				Biljeska biljeska = new Biljeska(id_biljeske, id_kolegija, name);
				result.add(biljeska);
			}
		}
		cursor.close();
		return result;
	}

	/**metoda za brisanje biljeske**/
	public boolean deleteBiljeska(int idBiljeske){
		return sqLiteDatabase.delete(TABLE, KEY_ID + "=" + idBiljeske, null) > 0;
	}
	
	//metoda za brisanje biljeski s odredenim id-om kolegija
	public boolean deleteBiljeskaKolegij(int id){
		return sqLiteDatabase.delete(TABLE, "id_kolegija" + "=" + id, null) > 0;
	}

}
