package fka.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by FKA on 06.03.2016.
 */
public class MainActivity extends AppCompatActivity {

    String[] stringList_NotesText;
    List<Integer> list_NotesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stringList_NotesText = createNotesTextStringList();

        if(stringList_NotesText.length > 0){
            ListAdapter listAdapter_Notes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringList_NotesText);
            ListView listView_Notes = (ListView)findViewById(R.id.listView_Notes);
            listView_Notes.setAdapter(listAdapter_Notes);
            listView_Notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedNote = String.valueOf(parent.getItemAtPosition(position));
                    int integerClickedNotePosition = position;

                    list_NotesId = createNotesIdStringList();
                    int integer_ClickedNotesId = list_NotesId.get(integerClickedNotePosition);

                    Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                    intent.putExtra("clickedNotesId",integer_ClickedNotesId);
                    startActivity(intent);
                }
            });

        } else {
            String[] string_NoNoteInData = {"No notes"};
            ListAdapter listAdapter_Notes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, string_NoNoteInData);
            ListView listView_Notes = (ListView)findViewById(R.id.listView_Notes);
            listView_Notes.setAdapter(listAdapter_Notes);
            listView_Notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String clickedNote = String.valueOf(parent.getItemAtPosition(position));
                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public String[] createNotesTextStringList(){
        DataBaseHandler db = new DataBaseHandler(this);
        List<Note> list_Notes = db.getAllNotes();

        List<String> arrayList_Notes = new ArrayList<String>();
        for (Note cn : list_Notes) {
            arrayList_Notes.add(cn.getString_NoteText());
        }
        String[] stringList_NotesText = arrayList_Notes.toArray(new String[arrayList_Notes.size()]);
        return stringList_NotesText;
    }

    public List<Integer> createNotesIdStringList(){
        DataBaseHandler db = new DataBaseHandler(this);
        List<Note> list_Notes = db.getAllNotes();
        List<Integer> list_NotesId = new ArrayList<Integer>();
        for (Note cn : list_Notes) {
            list_NotesId.add(cn.getInteger_NoteId());
        }
        return list_NotesId;
    }

}
