package com.dadashow.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleHolder> {
    ArrayList<String> noteNames;

    public RecyclerAdapter(ArrayList<String> noteNames) {
        this.noteNames = noteNames;
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_row_layout,parent,false);
        return new RecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        holder.textView.setText(noteNames.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragmentDirections.ActionListFragmentToNotesFragment fragment=ListFragmentDirections.actionListFragmentToNotesFragment("old");
                fragment.setIndex(position+1);
                Navigation.findNavController(v).navigate(fragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        return noteNames.size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.custom_row_text_view);
        }

    }

}
