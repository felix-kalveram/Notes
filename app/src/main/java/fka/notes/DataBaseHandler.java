package fka.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FKA on 06.03.2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_database";

    // Notes table name
    private static final String TABLE_NOTES = "notes";

    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOTE = "note";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOTE + " TEXT" + ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        // Create tables again
        onCreate(db);
    }

    // Adding new note
    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getString_NoteText()); // Note

        // Inserting Row
        db.insert(TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single note
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID, KEY_NOTE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        // return note
        return note;
    }

    // Getting All Notes
    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setInteger_NoteId(Integer.parseInt(cursor.getString(0)));
                note.setString_NoteText(cursor.getString(1));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    // Getting notes Count
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single note
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, note.getString_NoteText());

        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getInteger_NoteId())});
    }

    // Deleting single note
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getInteger_NoteId())});
        db.close();
    }

}
