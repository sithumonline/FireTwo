package com.github.sithumonline.firetwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestAdapter extends FirestoreRecyclerAdapter<Request, RequestAdapter.RequestHolder> {

    private RequestAdapter.OnItemClickListener listener;
    private Context appContext;

    public RequestAdapter(@NonNull FirestoreRecyclerOptions<Request> options, Context context) {
        super(options);
        this.appContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RequestHolder holder, int position, @NonNull Request model) {
        holder.textViewName.setText(model.getName());
        holder.textViewFood.setText(model.getFood());
        holder.textViewDescription.setText(model.getDescription());
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_main,
                parent, false);
        return new RequestHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class RequestHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewFood;
        TextView textViewDescription;

        public RequestHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewFood = itemView.findViewById(R.id.text_food);
            textViewDescription = itemView.findViewById(R.id.text_description);

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

    public void setOnItemClickListener(RequestAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    
}
