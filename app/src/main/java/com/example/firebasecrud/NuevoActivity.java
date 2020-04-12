package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;

import com.example.firebasecrud.Models.ContactoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoActivity extends AppCompatActivity {

    private EditText et_nuevo_nombre, et_nuevo_numero;
    private FloatingActionButton fab_nuevo_save;
    private ContactoModel contactoModel;

    /* FIREBASE DATABASE */
    private final String textReference = "contactos";
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference(textReference);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.toolbar_nuevo);
        setSupportActionBar(toolbar);

        et_nuevo_nombre = findViewById(R.id.et_nuevo_nombre);
        et_nuevo_numero = findViewById(R.id.et_nuevo_numero);
        fab_nuevo_save = findViewById(R.id.fab_nuevo_save);

        fab_nuevo_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String nombre = et_nuevo_nombre.getText().toString();
                String numero = et_nuevo_numero.getText().toString();

                if(!nombre.equals("") && !numero.equals("")){
                    String id = reference.push().getKey();

                    if(id != null && !id.equals("")){
                        contactoModel = new ContactoModel(id, nombre, numero);
                        reference.child(id).setValue(contactoModel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        if(!contactoModel.get_id().equals("") && contactoModel.get_id() != ""){
                                            Intent detalle = new Intent(NuevoActivity.this, DetalleActivity.class);
                                            detalle.putExtra("id", contactoModel.get_id());
                                            startActivity(detalle);
                                            finish();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(v, "No se pudo guardar", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    } else{
                        Snackbar.make(v, "Problemas al crear id en base de datos.", Snackbar.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(NuevoActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
