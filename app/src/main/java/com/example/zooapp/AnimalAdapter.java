package com.example.zooapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Animal> mAnimals;
    public AnimalAdapter(List<Animal> animals) {
        mAnimals = animals;
    }
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        AnimalViewHolder viewHolder=new AnimalViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AnimalViewHolder holder, int position) {
        final Animal animal = mAnimals.get(position);
        holder.NameTextView.setText(animal.getName());
        holder.ContinentTextView.setText(animal.getContinent());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Delete Animal");
                builder.setMessage("Are you sure you want to delete this animal?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) v.getContext()).deleteAnimal(animal.getId());
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAnimals.size();
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int id);
    }
    class AnimalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView NameTextView;
        public ImageButton deleteButton;
        public TextView ContinentTextView;
        private AnimalAdapter.OnDeleteButtonClickListener mDeleteListener;
        private int mAnimalId;
        public AnimalViewHolder(View itemView) {
            super(itemView);
            deleteButton=itemView.findViewById((R.id.btn_delete));
            NameTextView = itemView.findViewById(R.id.tv_animal_name);
            ContinentTextView = itemView.findViewById(R.id.tv_animal_continent);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_delete && mDeleteListener != null) {
                mDeleteListener.onDeleteButtonClick(mAnimalId);
            }
        }
    }
}
