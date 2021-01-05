package com.example.architecturecomp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.architecturecomp.databinding.ActivitySecondActitivityBinding;

import static com.example.architecturecomp.R.*;

public class SecondActitivity extends AppCompatActivity {
  ActivitySecondActitivityBinding binding;
    public final static String EXTRA_ID="com.example.architecturecomp.ID";
  public final static String titile="com.example.architecturecomp.title";
  public final static String description="com.example.architecturecomp.description";
  public final static String Priority="com.example.architecturecomp.priority";
  NoteViewModel noteViewModel;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, layout.activity_second_actitivity);
        binding.numpicker.setMinValue(1);
        binding.numpicker.setMaxValue(10);
        //setContentView(binding.getRoot());
        Intent intent=getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if(intent.hasExtra(EXTRA_ID)){
            getSupportActionBar().setTitle("Edite text note");
           binding.titltxt.setText(intent.getStringExtra(titile));
            binding.desctxt.setText(intent.getStringExtra(description));
            binding.numpicker.setValue(Integer.parseInt(intent.getStringExtra(Priority)));

        }
        getSupportActionBar().setTitle("Add  text note");
        noteViewModel=new NoteViewModel(getApplication());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu,menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case id.saved:
               saveNotes();
           default:return true;
       }
    }
    //    @Override
//    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
//        switch (menu.getItem(featureId).getItemId()){
//            case R.id.saved:
//                saveNotes();
//            default:return true;
//        }
//    }

//  @Override
//  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//       switch (v.getId()){
//           case R.id.saved:
//               saveNotes();
//           default:return;
//        }
//   }

    private void saveNotes() {
         String titile2=binding.titltxt.getText().toString();
         String desc=binding.desctxt.getText().toString();
         int priority=binding.numpicker.getValue();

         if(titile.isEmpty() || desc.isEmpty() ){
             Toast.makeText(this, "please fill the all above field", Toast.LENGTH_SHORT).show();
             return;
         }else {

             Intent intent=new Intent();
             intent.putExtra(titile,titile2);
             intent.putExtra(description,desc);
             intent.putExtra(Priority,priority);
             int id=getIntent().getIntExtra(EXTRA_ID,-1);
             if(id!=-1){
                 intent.putExtra(EXTRA_ID,id);
             }
             setResult(RESULT_OK,intent);
             finish();
         }
    }
}
