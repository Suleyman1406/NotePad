package com.dadashow.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(MainActivity.this);
        inflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add_note_item){
            ListFragmentDirections.ActionListFragmentToNotesFragment fragment=ListFragmentDirections.actionListFragmentToNotesFragment("new");
            try {
                Navigation.findNavController(this,R.id.fragment2).navigate(fragment);
            }catch (Exception e){
                NavDirections navDirections=NotesFragmentDirections.actionNotesFragmentToListFragment();
                Navigation.findNavController(this,R.id.fragment2).navigate(navDirections);
                Navigation.findNavController(this,R.id.fragment2).navigate(fragment);

            }
        }
        return super.onOptionsItemSelected(item);

    }
}