package com.example.architecturecomp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Entity.class},version = 1)
public abstract class Notedappdatabase extends RoomDatabase {
    public static Notedappdatabase instance;
    public  abstract NoteDao getNotedaao();
    public static synchronized Notedappdatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    Notedappdatabase.class,"NoteDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }
    static RoomDatabase.Callback callback=new RoomDatabase.Callback(){
         @Override
         public void onCreate(@NonNull SupportSQLiteDatabase db) {
             super.onCreate(db);
             new PopulateDatabase(instance).execute();

         }
     };
    private static  class PopulateDatabase extends AsyncTask<Void,Void,Void>{
 private NoteDao noteDao;
 public PopulateDatabase(Notedappdatabase notedappdatabase){
     noteDao=notedappdatabase.getNotedaao();
 }
        @Override
        protected Void doInBackground(Void...voids) {
            noteDao.inset(new Entity("Title1","descriptin1","priority1"));
            noteDao.inset(new Entity("Title2","descriptin2","priority2"));
            noteDao.inset(new Entity("Title3","descriptin3","priority3"));
            return null;
        }
    }
}
