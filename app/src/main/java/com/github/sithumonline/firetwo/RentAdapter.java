package com.github.sithumonline.firetwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RentAdapter extends FirestoreRecyclerAdapter<Rent, RentAdapter.RentHolder> {

    private NoteAdapter.OnItemClickListener listener;

    public RentAdapter(@NonNull FirestoreRecyclerOptions<Rent> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RentHolder holder, int position, @NonNull Rent model) {
        holder.textViewName.setText(model.getName());
        holder.textViewAddress.setText(model.getAddress());
        holder.imageCard.setImageResource(R.drawable.buffet_set);
    }

    @NonNull
    @Override
    public RentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_main,
                parent, false);
        return new RentHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class RentHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAddress;
        ImageView imageCard;

        public RentHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            imageCard = itemView.findViewById(R.id.image_rent);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
