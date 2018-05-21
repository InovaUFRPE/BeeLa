package com.beela.beela.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Preferencias;
import com.beela.beela.Helper.Upload;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PrincipalActivity extends AppCompatActivity {
    private Preferencias preferencias;

    private BottomNavigationItemView gerenciarPerfil;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button upload;

    private ImageView fotoUsuario;

    private StorageReference mStorage;

    private TextView nome,perfilAtual;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatebaseRef;
    private ProgressBar mProgreesBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mStorageRef = FirebaseStorage.getInstance().getReference("Fotos");
        mDatebaseRef = FirebaseDatabase.getInstance().getReference("Fotos");

        preferencias = new Preferencias(PrincipalActivity.this);

        gerenciarPerfil = findViewById(R.id.nav_perfil);

        gerenciarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarGerenciarPerfil();
            }
        });


        perfilAtual = findViewById(R.id.textViewPerfilAtual);

        nome = findViewById(R.id.textViewNomeConta);
        nome.setText(preferencias.getNome());



        upload = findViewById(R.id.buttonUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadFile();

            }
        });

        fotoUsuario = findViewById(R.id.imageViewFotoConta);
        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();


            }
        });








        Toast.makeText(PrincipalActivity.this, "Seu interesse 1 é " + preferencias.getInteresse1(), Toast.LENGTH_SHORT).show();

        Toast.makeText(PrincipalActivity.this, "Seu nome é " + preferencias.getNome() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Seu e-mail é " + preferencias.getEmail() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Sua data de aniversário é " + preferencias.getDataAniversario() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Seu gênero é " + preferencias.getGenero() + ".", Toast.LENGTH_SHORT).show();



    }


    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {

        if(mImageUri != null){

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                    "." + getFileExtension(mImageUri));


            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgreesBar.setProgress(0);
//                        };
//                    }, 5000);

                    Toast.makeText(PrincipalActivity.this, "Upload Completo",Toast.LENGTH_LONG).show();
                    Upload upload = new Upload("NomeEmailRefetenciaAqui",

                            taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mDatebaseRef.push().getKey();
                    mDatebaseRef.child(uploadId).setValue(upload);

                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(PrincipalActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = 100.0 *taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                    // mProgressBar.setProgress((int) progress);
                }
            });

        }else{
            Toast.makeText(this,"Nenhum arquivo selecionado",Toast.LENGTH_LONG).show();


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST &&resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {

            mImageUri = data.getData();
            fotoUsuario.setImageURI(mImageUri);

            //Picasso.with(this).load(mImageUri).into(mImageView);



        }


    }

    private void openFileChooser() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }




    public void redirecionarGerenciarPerfil() {
        Intent abrirGerenciarPerfil = new Intent(PrincipalActivity.this, VisualizarPerfilActivity.class);
        startActivity(abrirGerenciarPerfil);
    }
}
