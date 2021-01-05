package com.example.architecturecomp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architecturecomp.databinding.ItemsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteAddapter extends ListAdapter<Entity,NoteAddapter.Noteholder> {
    private Context context;
    private onItemClickListner listner;
//    public NoteAddapter(MainActivity mainActivity){
//        super(mainActivity);
//        this.context=mainActivity;
//    }
    public NoteAddapter(){
        super((DIFf_CallBack));
    }
    public static final DiffUtil.ItemCallback<Entity> DIFf_CallBack=new DiffUtil.ItemCallback<Entity>() {
        @Override
        public boolean areItemsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {

            return  oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                     && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriorty().equals(newItem.getPriorty());
        }
    };
    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout,parent,false);

        return new Noteholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Noteholder holder, int position) {
        Entity entity=getItem(position);
       holder.titile.setText(entity.getTitle());
       holder.desc.setText(entity.getDescription());
       holder.priority.setText(entity.getPriorty());
    }

//  public void setNote(List<Entity>entities){
//        this.entities=entities;
//
//        notifyDataSetChanged();
//  }
  public Entity getAt(int position){
        return getItem(position);
  }
//    @Override
//    public int getItemCount() {
//        return getItem().size();
//    }

    public class Noteholder extends RecyclerView.ViewHolder {
        private TextView titile,desc,priority;
        public Noteholder(@NonNull View itemView) {
            super(itemView);
            titile=itemView.findViewById(R.id.titleview);
            desc=itemView.findViewById(R.id.desc);
            priority=itemView.findViewById(R.id.priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listner!=null && position!=RecyclerView.NO_POSITION){
                        listner.onItemclick(getItem(position));
                    }

                }
            });
        }
    }
    public interface onItemClickListner {
       void onItemclick(Entity entity);

    }
    public void setOnItemClickListner(onItemClickListner listner){
        this.listner=listner;
    }
}
