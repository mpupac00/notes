package com.app.pupacic.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public NotesListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DataStorage.allNotesList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.notes_list_item, parent, false);
        }

        TextView idTv = (TextView) convertView.findViewById(R.id.noteIdTv);
        TextView titleTv = (TextView) convertView.findViewById(R.id.noteTitleTv);

        Note currentNote = DataStorage.allNotesList.get(position);

        idTv.setText(String.valueOf(currentNote.getId()));
        titleTv.setText(currentNote.getTitle());

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
