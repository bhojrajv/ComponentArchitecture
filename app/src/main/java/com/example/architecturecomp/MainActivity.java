package com.example.architecturecomp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.architecturecomp.databinding.ActivityMainBinding;

import java.util.List;

import static com.example.architecturecomp.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
private NoteViewModel noteViewModel;
 ActivityMainBinding binding;
 NoteAddapter noteAddapter;
 private final static int Request_code=1;
 private final static int Edit_Request_code=2;
private LiveData<List<Entity>>liveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this, activity_main);
      //   noteViewModel=new NoteViewModel(getApplication());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
         noteAddapter=new NoteAddapter();
        binding.rec.setLayoutManager(linearLayoutManager);
        binding.rec.setAdapter(noteAddapter);
        noteViewModel= new ViewModelProvider(this).get(NoteViewModel.class);
        liveData=noteViewModel.getallnotesdata();
        liveData.observe(this, new Observer<List<Entity>>() {
            @Override
            public void onChanged(List<Entity> entities) {
                //update data here
                noteAddapter.submitList(entities);


            }
        });
binding.fabbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,SecondActitivity.class);
        startActivityForResult(intent,Request_code);

    }
});
new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        noteViewModel.delete(noteAddapter.getAt(viewHolder.getAdapterPosition()));
        Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

    }
}).attachToRecyclerView(binding.rec);

  noteAddapter.setOnItemClickListner(new NoteAddapter.onItemClickListner() {
      @Override
      public void onItemclick(Entity entity) {
          Intent intent=new Intent(MainActivity.this,SecondActitivity.class);
          intent.putExtra(SecondActitivity.titile,entity.getTitle());
          intent.putExtra(SecondActitivity.description,entity.getDescription());
          intent.putExtra(SecondActitivity.Priority,entity.getPriorty());
          intent.putExtra(SecondActitivity.EXTRA_ID,entity.getId());
          startActivityForResult(intent,Edit_Request_code);


      }
  });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==Request_code){
          String title=  data.getExtras().getString(SecondActitivity.titile);
          String desc=data.getExtras().getString(SecondActitivity.description);
          int priority=data.getExtras().getInt(SecondActitivity.Priority,1);

          noteViewModel.insert(new Entity(title,desc,String.valueOf(priority)));
            Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show();
        }else if(requestCode==Edit_Request_code && resultCode==RESULT_OK) {

             int id=data.getIntExtra(SecondActitivity.EXTRA_ID,-1);
             if(id==-1){
                 Toast.makeText(this, "note not update", Toast.LENGTH_SHORT).show();
             }else {
                 String title=data.getExtras().getString(SecondActitivity.titile);
                 String desc=data.getExtras().getString(SecondActitivity.description);
                 int prority=data.getExtras().getInt(SecondActitivity.Priority);
                 Entity entity=new Entity(title,desc,String.valueOf(prority));
                 entity.setId(id);
                 noteViewModel.update(entity);
                 Toast.makeText(this, "note updated successfully", Toast.LENGTH_SHORT).show();
             }


        }
            else {
            Toast.makeText(this, "note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                noteViewModel.deleteAll();
            default:return true;
        }
    }
}