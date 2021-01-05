package com.example.architecturecomp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NoteViewModel extends AndroidViewModel{
   private String title,desc,priority;
    private Repository repository;
    private LiveData<List<Entity>>getallnotes;
    public NoteViewModel(@NonNull Application application) {
       super(application);
        repository=new Repository(application);
        getallnotes=repository.getAllnote();
    }
    public void insert(Entity entity){
        repository.insert(entity);
    }
    public void delete(Entity entity){
        repository.delete(entity);
    }
    public void update(Entity entity){
        repository.update(entity);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public LiveData<List<Entity>>getallnotesdata(){
            if(getallnotes==null){
                getallnotes=repository.getAllnote();
            }
        return getallnotes;
    }


}
