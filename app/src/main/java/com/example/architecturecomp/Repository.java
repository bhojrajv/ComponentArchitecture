package com.example.architecturecomp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class Repository {
    private NoteDao noteDao;
    private LiveData<List<Entity>>allnote;

    public Repository(Application application){
        Notedappdatabase notedappdatabase=Notedappdatabase.getInstance(application);
        noteDao=notedappdatabase.getNotedaao();
    }
    public void insert(Entity entity){
         new AsyncInsert(noteDao).execute(entity);
    }
    public void update(Entity entity){
        new Asyncupdate(noteDao).execute(entity);
    }
    public void delete(Entity entity){
  new Asyndelete(noteDao).execute(entity);
    }
    public void deleteAll(){
 new AsyncDeleteAll(noteDao).execute();
    }
  public LiveData<List<Entity>>getAllnote(){
      allnote=  noteDao.getAll();
        return allnote;
    }
    public static class AsyncInsert extends AsyncTask<Entity,Void,Void>{
        private NoteDao noteDao;
        public AsyncInsert(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Entity... entities) {
            noteDao.inset(entities[0]);
            return null;
        }
    }
    public static class Asyncupdate extends AsyncTask<Entity,Void,Void>{
 private NoteDao noteDao;
 public Asyncupdate (NoteDao noteDao){
     this.noteDao=noteDao;
 }
        @Override
        protected Void doInBackground(Entity... entities) {
       noteDao.update(entities[0]);
            return null;
        }
    }
    public static class Asyndelete extends AsyncTask<Entity,Void,Void>{
   private NoteDao noteDao;
   public Asyndelete(NoteDao dao){
       this.noteDao=dao;
   }
        @Override
        protected Void doInBackground(Entity... entities) {
           noteDao.delete(entities[0]);
            return null;
        }
    }
    public static class AsyncDeleteAll extends AsyncTask<Void ,Void ,Void>{
  private NoteDao noteDao;
  public AsyncDeleteAll(NoteDao noteDao){
      this.noteDao=noteDao;
  }
        @Override
        protected Void doInBackground(Void... voids) {
      noteDao.deletAll();
            return null;
        }
    }
}
