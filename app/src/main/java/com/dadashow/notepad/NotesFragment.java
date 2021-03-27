package com.dadashow.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class NotesFragment extends Fragment {
    EditText myNoteText;
    EditText noteAboutText;
    Button saveBtn;
    Button deleteBtn;
    ArrayList<String>noteNames;
    ArrayList<String>notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        }catch (Exception e){
            e.printStackTrace();
        }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveBtn=view.findViewById(R.id.saveBtn);
        deleteBtn=view.findViewById(R.id.deleteBtn);

        myNoteText =view.findViewById(R.id.myNote);
        noteAboutText =view.findViewById(R.id.aboutNote);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotesFragmentArgs.fromBundle(getArguments()).getInfo().equals("new")){
                    NavDirections directions=NotesFragmentDirections.actionNotesFragmentToListFragment();
                    Navigation.findNavController(v).navigate(directions);
                }else {
                    int id=NotesFragmentArgs.fromBundle(getArguments()).getIndex();
                    noteNames =new ArrayList<>();
                    notes =new ArrayList<>();
                    SQLiteDatabase database=getActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null);
                    Cursor cursor=database.rawQuery("SELECT * FROM notes",null);
                    int noteNameIx=cursor.getColumnIndex("notename");
                    int noteIx=cursor.getColumnIndex("note");
                    while (cursor.moveToNext()){
                        noteNames.add(cursor.getString(noteNameIx));
                        notes.add(cursor.getString(noteIx));
                    }
                    database.execSQL("DELETE  FROM notes WHERE 1");
                    noteNames.remove(id-1);
                    notes.remove(id-1);
                    database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY,notename VARCHAR,note VARCHAR)");
                    for (int i = 0; i < noteNames.size(); i++) {
                        String myNote = notes.get(i);
                        String noteAbout = noteNames.get(i);
                        String stringForStatement = "INSERT INTO notes (notename,note) VALUES (?,?)";
                        SQLiteStatement statement = database.compileStatement(stringForStatement);
                        statement.bindString(1, noteAbout);
                        statement.bindString(2, myNote);
                        statement.execute();
                    }
                    Toast.makeText(getActivity(),"DELETED",Toast.LENGTH_SHORT).show();

                    NavDirections directions=NotesFragmentDirections.actionNotesFragmentToListFragment();
                    Navigation.findNavController(v).navigate(directions);
                }

                }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=noteAboutText.getText().toString();
                while (a.contains(" ")){
                    a=a.replace(" ","");
                }
                if (!a.equals("")){
                SQLiteDatabase database=getActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null);
                if (NotesFragmentArgs.fromBundle(getArguments()).getInfo().equals("new")){
                String myNote=myNoteText.getText().toString();
                String noteAbout=noteAboutText.getText().toString();
                 database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY,notename VARCHAR,note VARCHAR)");
                 String stringForStatement="INSERT INTO notes (notename,note) VALUES (?,?)";
                SQLiteStatement statement=database.compileStatement(stringForStatement);
                statement.bindString(1,noteAbout);
                statement.bindString(2,myNote);
                statement.execute();
                NavDirections directions=NotesFragmentDirections.actionNotesFragmentToListFragment();
                Navigation.findNavController(v).navigate(directions);
                }else {
                    String myNote=myNoteText.getText().toString();
                    String noteAbout=noteAboutText.getText().toString();
                    int id=NotesFragmentArgs.fromBundle(getArguments()).getIndex();
                    database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY,notename VARCHAR,note VARCHAR)");
                    String stringForStatement1="UPDATE notes SET notename=?,note=? WHERE id=?";
                    SQLiteStatement sqLiteStatement=database.compileStatement(stringForStatement1);
                    sqLiteStatement.bindString(1,noteAbout);
                    sqLiteStatement.bindString(2,myNote);
                    sqLiteStatement.bindLong(3,id);
                    sqLiteStatement.execute();
                    Toast.makeText(getActivity(),"SAVED",Toast.LENGTH_SHORT).show();

                    NavDirections directions=NotesFragmentDirections.actionNotesFragmentToListFragment();
                    Navigation.findNavController(v).navigate(directions);

                }
            }else {
                    Toast.makeText(getActivity(),"Enter note name to save",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getArguments()!=null){
            SQLiteDatabase database=getActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null);


            if (NotesFragmentArgs.fromBundle(getArguments()).getInfo().equals("old")){
                int id=NotesFragmentArgs.fromBundle(getArguments()).getIndex();
                Cursor cursor=database.rawQuery("SELECT * FROM notes WHERE id=?",new String[]{id+""});
                int index1=cursor.getColumnIndex("notename");
                int index2=cursor.getColumnIndex("note");
                while (cursor.moveToNext()){
                 String noteName=cursor.getString(index1);
                String note=cursor.getString(index2);
                    noteAboutText.setText(noteName);
                    myNoteText.setText(note);
                }

                cursor.close();
            }else {
                myNoteText.setText("");
                noteAboutText.setText("");

            }
        }

    }
}