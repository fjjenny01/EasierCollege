package com.example.jinfang.easiercollege;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jinfang on 4/5/15.
 */
public class NotesReaderDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "JENNY";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NotesReaderContract.Note.TABLE_NAME + " (" +
                    NotesReaderContract.Note._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NotesReaderContract.Note.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    NotesReaderContract.Note.COLUMN_NAME_TEXT_CONTENT + TEXT_TYPE + COMMA_SEP +
                    NotesReaderContract.Note.COLUMN_NAME_PIC_CONTENT + TEXT_TYPE + COMMA_SEP +  // NEED TO BE CHANGED
                    NotesReaderContract.Note.COLUMN_NAME_TIME_CREATED + TEXT_TYPE + COMMA_SEP +
                    NotesReaderContract.Note.COLUMN_NAME_TIME_LAST_SAVED + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NotesReaderContract.Note.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NotesReader.db";


    private static final String[] COLUMNS = {NotesReaderContract.Note._ID, NotesReaderContract.Note.COLUMN_NAME_TITLE, NotesReaderContract.Note.COLUMN_NAME_TEXT_CONTENT, NotesReaderContract.Note.COLUMN_NAME_PIC_CONTENT, NotesReaderContract.Note.COLUMN_NAME_TIME_CREATED, NotesReaderContract.Note.COLUMN_NAME_TIME_LAST_SAVED};

    public static final String INSERT_ONE_LINE = "";

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public NotesReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public int createNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(NotesReaderContract.Note.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesReaderContract.Note.COLUMN_NAME_TEXT_CONTENT, note.getText_content());
        values.put(NotesReaderContract.Note.COLUMN_NAME_PIC_CONTENT, note.getPic_content());


        values.put(NotesReaderContract.Note.COLUMN_NAME_TIME_CREATED, dateFormat.format(note.getTime_created()));
        values.put(NotesReaderContract.Note.COLUMN_NAME_TIME_LAST_SAVED, dateFormat.format(note.getTime_last_saved()));

        long id = db.insert(NotesReaderContract.Note.TABLE_NAME, null, values);

        db.close();

        Integer i = (int)(long) id;

        return i;
    }


    public Note readNote(int id) {

        // get reference of the  database
        SQLiteDatabase db = this.getReadableDatabase();

        // get book query
        Cursor cursor = db.query(NotesReaderContract.Note.TABLE_NAME, // a. table
                COLUMNS, NotesReaderContract.Note._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note();
        note.setId(Integer.parseInt(cursor.getString(0)));
        note.setTitle(cursor.getString(1));
        note.setText_content(cursor.getString(2));
        note.setPic_content(cursor.getString(3));
        try {
            note.setTime_created(dateFormat.parse(cursor.getString(4)));
            note.setTime_last_saved(dateFormat.parse(cursor.getString(5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return note;
    }

    public int updateNote(Note note) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesReaderContract.Note.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesReaderContract.Note.COLUMN_NAME_TEXT_CONTENT, note.getText_content());
        values.put(NotesReaderContract.Note.COLUMN_NAME_PIC_CONTENT, note.getPic_content());
        values.put(NotesReaderContract.Note.COLUMN_NAME_TIME_CREATED, dateFormat.format(note.getTime_created()));
        values.put(NotesReaderContract.Note.COLUMN_NAME_TIME_LAST_SAVED, dateFormat.format(note.getTime_last_saved()));

        // update
        int i = db.update(NotesReaderContract.Note.TABLE_NAME, values, NotesReaderContract.Note._ID + " = ?", new String[] { String.valueOf(note.getId()) });

        db.close();
        return i;

    }


    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotesReaderContract.Note.TABLE_NAME, NotesReaderContract.Note._ID + "= ?", new String[]{String.valueOf(note.getId())});
        db.close();

    }


    public List<Note> getAllNotes() {
        List notes = new LinkedList();

        String query = "SELECT * FROM " + NotesReaderContract.Note.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        Note note = null;

        if (cursor.moveToFirst()) do {

            note = new Note();
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setTitle(cursor.getString(1));
            note.setText_content(cursor.getString(2));
            note.setPic_content(cursor.getString(3));
            try {
                note.setTime_created(dateFormat.parse(cursor.getString(4)));
                note.setTime_last_saved(dateFormat.parse(cursor.getString(5)));

            } catch (ParseException pe) {
                Log.e(TAG, "ParseException");
            }
            notes.add(note);

        } while (cursor.moveToNext());

        return notes;
    }

}
