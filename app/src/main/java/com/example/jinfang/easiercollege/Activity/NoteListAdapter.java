package com.example.jinfang.easiercollege.Activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jinfang.easiercollege.Note;
import com.example.jinfang.easiercollege.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinfang on 2/20/15.
 */

public class NoteListAdapter extends BaseAdapter {
//public class NoteListAdapter extends BaseAdapter implements Filterable {

    private static String TAG = "Jenny";
    List<Note> notes = new ArrayList<Note>();
    LayoutInflater inflater;
    Context context;
    View alertDialogView; // might not be needed


   public NoteListAdapter(Context context, List<Note> notes)
   {
       this.context = context;
       for (Note p: notes)
       {
           this.notes.add(p);
       }
       inflater = LayoutInflater.from(context);


   }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    /**
    @Override
    public Filter getFilter() {

        return  new android.widget.Filter()
        {

            FilterResults result = new FilterResults();

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<Note> filtered= new ArrayList<Note>();
                String filterString = constraint.toString().toLowerCase();

                for (int i = 0; i < notes.size(); i++)

                {
                    Note note = notes.get(i);
                    if((note.getTitle().toLowerCase().contains(filterString))|| (note.getText_content().toLowerCase().contains(filterString)))
                    {
                        filtered.add(note);
                    }
                }

                result.values = filtered;
                result.count = filtered.size();
                return result;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                NoteListAdapter.this.notes = (List<Note>)result.values;
                NoteListAdapter.this.notifyDataSetChanged();

            }
        };


    }**/


    public final class ViewHolder {

        public TextView title;
        public TextView timeLastSaved;
        public TextView timeCreated;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.timeLastSaved = (TextView)convertView.findViewById(R.id.timeLastSaved);
            holder.timeCreated = (TextView) convertView.findViewById(R.id.timeCreated);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(notes.get(position).getTitle());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd@HH:mm:ss");
        holder.timeLastSaved.setText("Saved on "+dateFormat.format(notes.get(position).getTime_last_saved()));
        holder.timeCreated.setText(dateFormat1.format(notes.get(position).getTime_created()));


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }





}
