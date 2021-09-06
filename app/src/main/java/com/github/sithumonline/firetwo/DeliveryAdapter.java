package com.github.sithumonline.firetwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryAdapter extends FirestoreRecyclerAdapter<Delivery, DeliveryAdapter.DeliveryHolder> {

    private DeliveryAdapter.OnItemClickListener listener;
    private Context appContext;

    public DeliveryAdapter(@NonNull FirestoreRecyclerOptions<Delivery> options, Context context) {
        super(options);
        this.appContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DeliveryHolder holder, int position, @NonNull Delivery model) {
        holder.textViewName.setText(model.getName());
        holder.textViewPrice.setText(String.valueOf(model.getUnitPrice()));
        Glide.with(appContext).load(model.getImageLink())
                .placeholder(R.drawable.snack_two)
                .into(holder.imageCard);
    }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_main,
                parent, false);
        return new DeliveryHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class DeliveryHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        ImageView imageCard;

        public DeliveryHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageCard = itemView.findViewById(R.id.image_delivery);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(DeliveryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    
}
