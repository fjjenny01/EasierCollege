package com.example.jinfang.easiercollege.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jinfang.easiercollege.FileOperation.PhotoUtil;
import com.example.jinfang.easiercollege.Note;
import com.example.jinfang.easiercollege.NotesReaderDbHelper;
import com.example.jinfang.easiercollege.R;

import java.util.Date;

/**
 * NoteEditingActivity allows user to edit a note and
 * save the changes.
 * Created by jinfang on 2/27/15.
 */
public class NoteEditingActivity extends ActionBarActivity {

    private EditText title;
    private EditText txt_content;
    private ImageView pic_content;
    NotesReaderDbHelper db;
    Note selectedNote;


    Uri imgUri;
    private static boolean hasPhoto;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int CHOOSE_GALLERY = 1889;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);


        Intent intent = getIntent();
        int id = intent.getIntExtra("note", -10);
        Log.i("Jenny", "The id get is: " + id);

        db = new NotesReaderDbHelper(getApplicationContext());

        selectedNote = db.readNote(id);

        title = (EditText) findViewById(R.id.edit_note_title);
        txt_content = (EditText) findViewById(R.id.edit_note_content);
        pic_content = (ImageView) findViewById(R.id.pic);


        title.setText(selectedNote.getTitle());
        txt_content.setText(selectedNote.getText_content());
        if (!selectedNote.getPic_content().equals("")) {

            pic_content.setImageURI(PhotoUtil.getUri(selectedNote.getId()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editing_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }


    /**
     * handle events of add a picture, delete a picture, and delete
     * the note. Save the changes and exit the NoteEditingActivity when user click
     * "Done" button.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteNote(this);
                return true;
            case R.id.action_save:
                updateNote();
                return true;
            case R.id.action_add_pic:
                //add a pic: choose from existing or taking a new pic
                addPic(this);
                return true;
            case R.id.action_del_pic:
                deletePic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * save a picture from taking a photo
     * using camera
     * @param context
     */
    public void addPic(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        final CharSequence[] options = {"Take Photo", "Cancel"};

        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    imgUri = PhotoUtil.getOutputMediaFileUri(MEDIA_TYPE_IMAGE, selectedNote.getId()); // create a file to save the image
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // set the image file name

                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    /**
     * delete the current picture in the note
     */
    public void deletePic() {

        pic_content.setImageDrawable(null);
        selectedNote.setPic_content("");
        hasPhoto = !PhotoUtil.deleteUri(selectedNote.getId());


    }


    /**
     * save all changes and exit NoteEditingActivity
     */
    public void updateNote() {
        selectedNote.setTitle(title.getText().toString());
        selectedNote.setText_content(txt_content.getText().toString());
        selectedNote.setTime_last_saved(new Date());
        if (hasPhoto) {
            selectedNote.setPic_content(selectedNote.getId() + "Pic");
        } else {
            Log.i("Jenny", "photos null");
        }

        db.updateNote(selectedNote);
        finish();
    }


    /**
     * Delete the current note: a popup is required to confirm
     * user's action
     * @param context
     */
    public void deleteNote(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure to delete this note?");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteNote(selectedNote);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    /**
     *  save the picture taken from camera to note
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                if (imgUri == null) {
                    Log.e("Jenny", "null");
                } else {

                    pic_content.setImageURI(imgUri);
                    hasPhoto = true;
                    updateNote();
                }

            } else {
                Log.i("Jenny", "Result NOT OK");
            }

        }
    }

}
