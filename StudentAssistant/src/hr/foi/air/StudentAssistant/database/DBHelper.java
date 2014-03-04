package hr.foi.air.StudentAssistant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override

	//kreiranje tablica
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE kolegij (ID_kolegija INTEGER PRIMARY KEY, naziv TEXT);");
		db.execSQL("CREATE TABLE biljeska (ID_biljeske INTEGER PRIMARY KEY,id_kolegija INTEGER, biljeska TEXT,FOREIGN KEY (id_kolegija) REFERENCES kolegij(ID_kolegija));");
		db.execSQL("CREATE TABLE evidencija (ID_evidencije INTEGER PRIMARY KEY, id_kolegija INTEGER, evidencija TEXT, datum TEXT,FOREIGN KEY (id_kolegija) REFERENCES kolegij(ID_kolegija));");
		db.execSQL("CREATE TABLE bodovi (ID_bodova INTEGER PRIMARY KEY,id_kolegija INTEGER, bod REAL, oznaka TEXT,FOREIGN KEY (id_kolegija) REFERENCES kolegij(ID_kolegija));");
		db.execSQL("CREATE TABLE stavka_rasporeda (ID_stavke INTEGER PRIMARY KEY,id_kolegija INTEGER, vrijeme_p TIME, vrijeme_k TIME,dvorana INTEGER, dan TEXT, nastava TEXT,FOREIGN KEY (id_kolegija) REFERENCES kolegij(ID_kolegija));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
