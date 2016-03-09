package fka.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by FKA on 06.03.2016.
 */
public class EditNoteActivity extends MainActivity {

    EditText editText_EditNote;
    String string_noteText;
    Note clickedNote;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clickedNote = getClickedNote();

        editText_EditNote = (EditText)findViewById(R.id.editText_EditNote);
        editText_EditNote.setText(clickedNote.getString_NoteText(), TextView.BufferType.EDITABLE);

        // Save-Button
        Button button_SaveEditedNote = (Button) findViewById(R.id.button_SaveEditedNote);
        button_SaveEditedNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_EditNote = (EditText)findViewById(R.id.editText_EditNote);
                string_noteText = editText_EditNote.getText().toString();
                clickedNote.setString_NoteText(string_noteText);
                saveEditedNote(clickedNote);

                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Delete-Button
        Button button_DeleteEditedNote = (Button) findViewById(R.id.button_DeleteEditedNote);
        button_DeleteEditedNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Delete note?");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete this note?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, delete note and start MainActivity
                                deleteEditedNote(clickedNote);
                                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mynotes) {
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_createnote) {
            Intent intent = new Intent(EditNoteActivity.this, CreateNoteActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getClickedNotesId(){
        Bundle extras = getIntent().getExtras();
        int integer_ClickedNotesId = extras.getInt("clickedNotesId");
        return integer_ClickedNotesId;
    }

    private Note getClickedNote(){
        DataBaseHandler db = new DataBaseHandler(this);
        Note note = db.getNote(getClickedNotesId());
        return note;
    }

    private void saveEditedNote(Note note){
        DataBaseHandler db = new DataBaseHandler(this);
        db.updateNote(note);
    }

    private void deleteEditedNote(Note note){
        DataBaseHandler db = new DataBaseHandler(this);
        db.deleteNote(note);
    }

}
