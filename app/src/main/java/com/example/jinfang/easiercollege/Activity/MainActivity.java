package com.example.jinfang.easiercollege.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.jinfang.easiercollege.Note;
import com.example.jinfang.easiercollege.NotesReaderDbHelper;
import com.example.jinfang.easiercollege.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MainActivity shows a list of notes
 * @author Jin Fang
 * @version 05/08/2015
 */
public class MainActivity extends ActionBarActivity  {

    private static String TAG = "Jenny";
    List<Note> notes;
    NoteListAdapter noteListAdapter;
    NotesReaderDbHelper db = new NotesReaderDbHelper(this);
    SearchView searchView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get a list of all titles
        notes = db.getAllNotes();

        sortByTimedLastSaved(notes);

        List listTitle = new ArrayList();

        for (int i = 0; i < notes.size(); i++) {
            listTitle.add(i, notes.get(i).getTitle());
        }

        noteListAdapter = new NoteListAdapter(this, notes);

        listView = (ListView) findViewById(R.id.list);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(noteListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), NoteEditingActivity.class);

                intent.putExtra("note", notes.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);


//        final MenuItem  menuItem = menu.findItem(R.id.action_search);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        /**
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!TextUtils.isEmpty(newText)) {
                    noteListAdapter.getFilter().filter(newText);
//                    Log.i("jenny", "filter text");

                }
                else
                {
                        menuItem.collapseActionView();
                        searchView.clearFocus();
                }

                return true;
            }
        });**/
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * handle click events of searching and creating a new note
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
//            case R.id.action_search:
//
//                ListView listView = (ListView) findViewById(R.id.list);
//                listView.setTextFilterEnabled(true);
//                Log.i("Jenny", "enabled true");
//                return true;
            case R.id.action_add:
                Intent intent = new Intent(getBaseContext(), NoteEditingActivity.class);
                int id = db.createNote(new Note());
                intent.putExtra("note", id);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * sort all notes by the time last saved.
     * The new and old notes are listed from top to bottom
     * @param notes
     */
    private void sortByTimedLastSaved(List<Note> notes)
    {
        Collections.sort(notes, new Comparator<Note>() {
            public int compare(Note n1, Note n2) {
                if (n1.getTime_last_saved() == null || n2.getTime_last_saved() == null)
                    return 0;
                return n2.getTime_last_saved().compareTo(n1.getTime_last_saved());
            }
        });
    }

    /**
     * refresh the listView when MainActivity resumes from
     * NoteEditingActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        // get all notes again, because something changed
        notes = db.getAllNotes();

        sortByTimedLastSaved(notes);

        List listTitle = new ArrayList();
        for (int i = 0; i < notes.size(); i++) {
            listTitle.add(i, notes.get(i).getTitle());
        }

        ListView listView = (ListView) findViewById(R.id.list);
        noteListAdapter = new NoteListAdapter(this, notes);
        listView.setAdapter(noteListAdapter);
        noteListAdapter.notifyDataSetChanged();

    }

}
