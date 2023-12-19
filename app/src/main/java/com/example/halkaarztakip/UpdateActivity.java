package com.example.halkaarztakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    TextView txtTitle;
    EditText updTitle, updPrice, updLot, updNote;
    ImageView done_btn;
    String title, price, lot, note, key;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        txtTitle = findViewById(R.id.upd_title);
        updTitle = findViewById(R.id.et_updTitle);
        updPrice = findViewById(R.id.et_updPrice);
        updLot = findViewById(R.id.et_updLot);
        updNote = findViewById(R.id.et_updNotes);
        done_btn = findViewById(R.id.upd_done_img);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            txtTitle.setText("#" + bundle.getString("Title"));
            updTitle.setText(bundle.getString("Title"));
            updPrice.setText(bundle.getString("Price"));
            updLot.setText(bundle.getString("Lot"));
            updNote.setText(bundle.getString("Note"));
            key = bundle.getString("Key");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Halka Arz Hisseleri").child(key);

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        title = updTitle.getText().toString().toUpperCase().trim();
        price = updPrice.getText().toString();
        lot = updLot.getText().toString();
        note = updNote.getText().toString();

        DataClass dataClass = new DataClass(title,price,lot,note);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(UpdateActivity.this,"Hisse güncellendi.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}