package com.example.halkaarztakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    TextView detailTitle, detailPrice, detailLot, detailNote, detailPriceList;
    ListView detailListView;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String title,price,lot,note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detail_title);
        detailPrice = findViewById(R.id.detail_price);
        detailLot = findViewById(R.id.detail_lot);
        detailNote = findViewById(R.id.et_detailNotes);
        detailNote.setMovementMethod(new ScrollingMovementMethod());
        detailPriceList = findViewById(R.id.detail_priceList);
        detailPriceList.setMovementMethod(new ScrollingMovementMethod());
        //detailListView = findViewById(R.id.detail_listview);

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailTitle.setText("#" + bundle.getString("Title"));
            detailPrice.setText(bundle.getString("Price") + " ₺");
            detailLot.setText(bundle.getString("Lot") + " LOT");
            detailNote.setText(bundle.getString("Note"));
            key = bundle.getString("Key");

            title = bundle.getString("Title");
            price = bundle.getString("Price");
            lot = bundle.getString("Lot");
            note = bundle.getString("Note");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
                //deleteData();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        detailPriceList.setText(calculatePrices(price, lot));
    }

    public String calculatePrices(String price, String lot){
        String text = "";
        float firstPrice = (Float.parseFloat(price));
        float newPrice = (Float.parseFloat(price)*11/10);
        float lotSayi = (Float.parseFloat(lot));

        for (int i = 1; i <= 15; i++){
            DecimalFormat df = new DecimalFormat("#.##");
            text = text + i + ". tavan fiyatı: " + df.format(newPrice) + " ₺\n"
                    + "Toplam Kâr:  " + df.format((newPrice*lotSayi) - (firstPrice*lotSayi)) + " ₺\n"
                    + "Toplam Fiyat: " + df.format(newPrice*lotSayi) + " ₺\n\n";
            newPrice = (newPrice*11/10);
        }

        return text;
    }
    public void deleteData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Halka Arz Hisseleri");
        databaseReference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(DetailActivity.this, "Hisse silindi.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailActivity.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(){
        Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                .putExtra("Title", title)
                .putExtra("Price", price)
                .putExtra("Lot", lot)
                .putExtra("Note", note)
                .putExtra("Key", key);
        startActivity(intent);
    }

    private void showAlertDialog(){
        View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.dialog_layout, (ConstraintLayout)findViewById(R.id.dialog_constraintLayout));
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.dialogText)).setText("#" + title + " adlı hissenizi silmek istediğinize emin misiniz?");
        ((ImageView) view.findViewById(R.id.dialog_image)).setImageResource(R.drawable.round_priority_high_24);
        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.dialog_btn_evet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        view.findViewById(R.id.dialog_btn_hayir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}