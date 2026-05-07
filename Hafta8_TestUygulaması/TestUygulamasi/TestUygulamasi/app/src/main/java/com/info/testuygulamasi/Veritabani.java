package com.info.testuygulamasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Veritabani extends SQLiteOpenHelper {
    private static final String VERITABANI_ADI = "QuizDB.db";
    private static final int SURUM = 1;

    public Veritabani(Context c) {
        super(c, VERITABANI_ADI, null, SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Sorular (" +
                "soru TEXT PRIMARY KEY, " +
                "secA TEXT NOT NULL, " +
                "secB TEXT NOT NULL, " +
                "secC TEXT NOT NULL, " +
                "secD TEXT NOT NULL, " +
                "secE TEXT NOT NULL, " +
                "dogruCevap TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiSurum, int yeniSurum) {
        db.execSQL("DROP TABLE IF EXISTS Sorular");
        onCreate(db);
    }

    // --- CRUD (Ekle, Oku, Güncelle, Sil) METOTLARI ---

    public boolean soruEkle(String soru, String a, String b, String c, String d, String e, String dogru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();
        veriler.put("soru", soru);
        veriler.put("secA", a);
        veriler.put("secB", b);
        veriler.put("secC", c);
        veriler.put("secD", d);
        veriler.put("secE", e);
        veriler.put("dogruCevap", dogru);

        long sonuc = db.insert("Sorular", null, veriler);
        db.close();
        return sonuc != -1; // -1 ise ekleme başarısızdır
    }

    public boolean soruSil(String orijinalSoru) {
        SQLiteDatabase db = this.getWritableDatabase();
        int etkilenen = db.delete("Sorular", "soru=?", new String[]{orijinalSoru});
        db.close();
        return etkilenen > 0;
    }

    public boolean soruGuncelle(String orijinalSoru, String yeniSoru, String a, String b, String c, String d, String e, String dogru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();
        veriler.put("soru", yeniSoru);
        veriler.put("secA", a);
        veriler.put("secB", b);
        veriler.put("secC", c);
        veriler.put("secD", d);
        veriler.put("secE", e);
        veriler.put("dogruCevap", dogru);

        int etkilenen = db.update("Sorular", veriler, "soru=?", new String[]{orijinalSoru});
        db.close();
        return etkilenen > 0;
    }

    public ArrayList<String> tumSorulariGetir() {
        ArrayList<String> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT soru FROM Sorular", null);
        while (cursor.moveToNext()) {
            liste.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return liste;
    }

    public Cursor soruDetayGetir(String soruMetni) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Sorular WHERE soru=?", new String[]{soruMetni});
    }
}