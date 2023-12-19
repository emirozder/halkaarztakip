package com.example.halkaarztakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    EditText hisseTitle, hissePrice, hisseLot, hisseNote;
    String title, price, lot, note;
    ImageView done_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        hisseTitle = findViewById(R.id.et_addTitle);
        hissePrice = findViewById(R.id.et_addPrice);
        hisseLot = findViewById(R.id.et_addLot);
        hisseNote = findViewById(R.id.et_addNotes);
        done_btn = findViewById(R.id.add_done_img);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void saveData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        title = hisseTitle.getText().toString().toUpperCase().trim();
        price = hissePrice.getText().toString();
        lot = hisseLot.getText().toString();
        note = hisseNote.getText().toString();

        DataClass dataClass = new DataClass(title,price,lot,note);

        //String childName = "#" + title;

        FirebaseDatabase.getInstance().getReference("Halka Arz Hisseleri").child(title)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(AddActivity.this,"Hisse eklendi.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}