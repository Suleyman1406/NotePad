package com.dadashow.notepad;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ArrayList<String> noteNames;
    ArrayList<String> notes;
    public ListFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase database=getActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY,notename VARCHAR,note VARCHAR)");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerView);
        noteNames =new ArrayList<>();
        notes =new ArrayList<>();
        try {
            SQLiteDatabase database=getActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY,notename VARCHAR,note VARCHAR)");
            Cursor cursor=database.rawQuery("SELECT * FROM notes",null);
            int noteNameIx=cursor.getColumnIndex("notename");
            int noteIx=cursor.getColumnIndex("note");
            while (cursor.moveToNext()){
                noteNames.add(cursor.getString(noteNameIx));
                notes.add(cursor.getString(noteIx));
            }
            cursor.close();
            recyclerView=view.findViewById(R.id.recyclerView);
            recyclerAdapter=new RecyclerAdapter(noteNames);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(recyclerAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}