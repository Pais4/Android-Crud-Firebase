package com.example.firebasecrud;

import android.content.Intent;
import android.os.Bundle;

import com.example.firebasecrud.Models.ContactoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    private EditText et_editar_nombre, et_editar_numero;
    private FloatingActionButton fab_editar_guardar;
    private ContactoModel contactoModel;

    /* FIREBASE DATABASE */
    private final String textReference = "contactos";
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference(textReference);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar_editar);
        setSupportActionBar(toolbar);

        et_editar_nombre = findViewById(R.id.et_editar_nombre);
        et_editar_numero = findViewById(R.id.et_editar_numero);
        fab_editar_guardar = findViewById(R.id.fab_editar_guardar);
        contactoModel = new ContactoModel();

        // Busca en Firebase y trae la informacion
        String id = getIntent().getStringExtra("id");
        if(id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    contactoModel = dataSnapshot.getValue(ContactoModel.class);
                    if(contactoModel != null){
                        et_editar_nombre.setText(contactoModel.get_nombre());
                        et_editar_numero.setText(contactoModel.get_numero());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(EditarActivity.this, "Error al conectar con Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }

        fab_editar_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String nombre = et_editar_nombre.getText().toString();
                String numero = et_editar_numero.getText().toString();

                if(!nombre.equals("") && !numero.equals("")){

                    if(contactoModel != null) {
                        String id = contactoModel.get_id();

                        if (id != null && !id.equals("")) {
                            contactoModel.set_nombre(nombre);
                            contactoModel.set_numero(numero);

                            reference.child(id).setValue(contactoModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if (!contactoModel.get_id().equals("") && contactoModel.get_id() != "") {
                                                Intent detalle = new Intent(EditarActivity.this, DetalleActivity.class);
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
                        } else {
                            Snackbar.make(v, "Problemas al crear id en base de datos.", Snackbar.LENGTH_LONG).show();
                        }
                    }

                }else{
                    Toast.makeText(EditarActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
