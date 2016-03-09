package fka.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by FKA on 06.03.2016.
 */
public class CreateNoteActivity extends  MainActivity {

    EditText editText_NewNote;
    String string_noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button_SaveNote = (Button) findViewById(R.id.button_SaveNote);
        button_SaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_NewNote = (EditText)findViewById(R.id.editText_Note);
                string_noteText = editText_NewNote.getText().toString();
                addCreatedNote(createNewNote(string_noteText));

                Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_createnotes) {
            Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public Note createNewNote(String stringNoteText){
        Note note = new Note();
        note.setString_NoteText(stringNoteText);
        return note;
    }

    private void addCreatedNote(Note note){
        DataBaseHandler db = new DataBaseHandler(this);
        db.addNote(note);
    }

}
