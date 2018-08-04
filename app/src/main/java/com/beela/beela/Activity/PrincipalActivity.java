package com.beela.beela.Activity;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
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
    private Sessao preferencias;
    private BottomNavigationItemView gerenciarPerfil;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button upload;
    private ImageView fotoUsuario;
    private StorageReference mStorage;
    private TextView nome, perfilAtual;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatebaseRef;
    private ProgressBar mProgreesBar;


    //fragment


    private BottomNavigationView mPrincipalNav;
    private FrameLayout mPrincipalFrame;

    private PrincipalFragment principalFragment;
    private PerfilFragment perfilFragment;
    private ConfiguracaoFragment configuracaoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);



        mStorageRef = FirebaseStorage.getInstance().getReference("Fotos");
        mDatebaseRef = FirebaseDatabase.getInstance().getReference("Fotos");

        preferencias = Sessao.getInstancia(this.getApplicationContext());


        //fragment
        mPrincipalFrame = findViewById(R.id.principal_frame);
        mPrincipalNav = findViewById(R.id.principal_nav);

        principalFragment = new PrincipalFragment();
        perfilFragment = new PerfilFragment();
        configuracaoFragment = new ConfiguracaoFragment();

        setFragment(principalFragment);


        mPrincipalNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.nav_home:

                        mPrincipalNav.setItemBackgroundResource(R.color.colorRoxo);
                        setFragment(principalFragment);
                        return true;

                    case R.id.nav_conf:
                        mPrincipalNav.setItemBackgroundResource(R.color.colorRoxo);
                        setFragment(configuracaoFragment);

                        return true;

                    case R.id.nav_perfil:
                        mPrincipalNav.setItemBackgroundResource(R.color.colorRoxo);
                        setFragment(perfilFragment);

                        return true;

                    default:
                        return false;

                }

            }
        });




//        gerenciarPerfil = findViewById(R.id.nav_perfil);
//        gerenciarPerfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                redirecionarGerenciarPerfil();
//            }
//        });
//
//        perfilAtual = findViewById(R.id.textViewPerfilAtual);
//        nome = findViewById(R.id.textViewNomeConta);
//        nome.setText(preferencias.getValor());
//        upload = findViewById(R.id.buttonUpload);
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                uploadFile();
//
//            }
//        });
//        fotoUsuario = findViewById(R.id.imageViewFotoConta);
//        fotoUsuario.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFileChooser();
//
//            }
//        });


//        Toast.makeText(PrincipalActivity.this, preferencias.getPerfil().getInteresses().size(), Toast.LENGTH_SHORT).show();

//        Toast.makeText(PrincipalActivity.this, "entrou na principal", Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 1: " + preferencias.getInteresse1(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 2: " + preferencias.getInteresse2(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 3: " + preferencias.getInteresse3(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 4: " + preferencias.getInteresse4(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 5: " + preferencias.getInteresse5(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 6: " + preferencias.getInteresse6(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 7: " + preferencias.getInteresse7(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 8: " + preferencias.getInteresse8(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 9: " + preferencias.getInteresse9(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PrincipalActivity.this, "sessao string 10: " + preferencias.getInteresse10(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(PrincipalActivity.this, "Nome: " + preferencias.getNome(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(PrincipalActivity.this, "Url: " + preferencias.getUrlFoto(), Toast.LENGTH_SHORT).show();


        for (String interesse : preferencias.getPerfil().getInteresses()) {
            //Toast.makeText(PrincipalActivity.this, "interesse: " + interesse, Toast.LENGTH_SHORT).show();
        }

        /**Toast.makeText(PrincipalActivity.this, "Seu nome por objeto da sessão é: " + preferencias.getUsuario().getValor(), Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Seu e-mail por objeto da sessão é: " + preferencias.getUsuario().getEmail(), Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Sua data de aniversário por objeto da sessão é: " + preferencias.getUsuario().getDataAniversario(), Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Seu sexo por objeto da sessão é: " + preferencias.getUsuario().getSexo(), Toast.LENGTH_SHORT).show();

         //Toast.makeText(PrincipalActivity.this, "Seu interesse 1 é " + preferencias.getInteresse1(), Toast.LENGTH_SHORT).show();

         Toast.makeText(PrincipalActivity.this, "Seu nome é " + preferencias.getValor() + ".", Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Seu e-mail é " + preferencias.getEmail() + ".", Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Sua data de aniversário é " + preferencias.getDataAniversario() + ".", Toast.LENGTH_SHORT).show();
         Toast.makeText(PrincipalActivity.this, "Seu gênero é " + preferencias.getGenero() + ".", Toast.LENGTH_SHORT).show();
         */


    }


    private void uploadFile() {

        if (mImageUri != null) {

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

                    Toast.makeText(PrincipalActivity.this, "Upload Completo", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload("NomeEmailRefetenciaAqui",

                            taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mDatebaseRef.push().getKey();
                    mDatebaseRef.child(uploadId).setValue(upload);

                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(PrincipalActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                    // mProgressBar.setProgress((int) progress);
                }
            });

        } else {
            Toast.makeText(this, "Nenhum arquivo selecionado", Toast.LENGTH_LONG).show();


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            fotoUsuario.setImageURI(mImageUri);

            //Picasso.with(this).load(mImageUri).into(mImageView);


        }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    //fragment
    private void setFragment(android.support.v4.app.Fragment fragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.principal_frame, fragment);
        fragmentTransaction.commit();


    }


    private String getFileExtension(Uri uri) {

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
        public void redirecionarGerenciarPerfil () {
            Intent abrirGerenciarPerfil = new Intent(PrincipalActivity.this, VisualizarPerfilActivity.class);
            startActivity(abrirGerenciarPerfil);
        }



    @Override
    public void onBackPressed() {



        final CharSequence[] escolha = {"Sim", "Não"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(PrincipalActivity.this);
        alerta.setItems(escolha, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String opcao = (String) escolha[i];
                if (opcao.equals(("Sim"))) {

                finishAffinity();

                } else if (opcao.equals(("Não"))) {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.setTitle("Deseja Fechar o Aplicativo ? ");
        AlertDialog aviso = alerta.create();
        aviso.show();










    }


}



