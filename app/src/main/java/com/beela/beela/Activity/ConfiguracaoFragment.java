package com.beela.beela.Activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.Helper.Upload;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracaoFragment extends Fragment {

    private Sessao preferencias;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button upload;
    private ImageView fotoUsuario;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatebaseRef;
    private TextView nome,datanacimento,email,senha,genero;


    public ConfiguracaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracao, container,false);

        mStorageRef = FirebaseStorage.getInstance().getReference("Fotos");
        mDatebaseRef = FirebaseDatabase.getInstance().getReference("Fotos");

        preferencias = Sessao.getInstancia(getContext());

        fotoUsuario = view.findViewById(R.id.imageViewPrincipalFrag);

        fotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        nome = view.findViewById(R.id.textViewNome);
        senha = view.findViewById(R.id.textViewSenha);
        email = view.findViewById(R.id.textViewEmail);
        genero = view.findViewById(R.id.textViewGenero);
        datanacimento = view.findViewById(R.id.textViewGenero);


        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mudar nome com alert


            }
        });


        senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mudar senha com alert

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mudar senha com alert

            }
        });


        genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // mudar genero com alert

            }
        });



        datanacimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mudar data

            }
        });



        upload = view.findViewById(R.id.buttonUploadConfFrag);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile();

            }
        });

            return view;
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

                    Toast.makeText(getContext(), "Upload Completo", Toast.LENGTH_LONG).show();

                    String identificador = Codificador.codificador(preferencias.getUsuario().getEmail());
                    String urlfoto = taskSnapshot.getDownloadUrl().toString();

                    preferencias.getUsuario().setUrlFoto(urlfoto);
                    preferencias.salvarUrlFoto(identificador,urlfoto);

                    mDatebaseRef = FirebaseDatabase.getInstance().getReference("conta");

                    mDatebaseRef.child(identificador).child("urlFoto").setValue(urlfoto);
                  //  mDatebaseRef.child(identificador).child("nome").

                    mDatebaseRef = FirebaseDatabase.getInstance().getReference("conta").child(identificador);

                    mDatebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                          String  a = (String) dataSnapshot.child("email").getValue();



                          Toast.makeText(getContext(), "Url: " + a, Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });








                    Upload upload = new Upload(identificador, taskSnapshot.getDownloadUrl().toString());

                    String uploadId = mDatebaseRef.push().getKey();
                    //TODO Isso servia pra alguma Coisa mas tava Dando pau no Firebase
                    //mDatebaseRef.child(uploadId).setValue("upload");

                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                    // mProgressBar.setProgress((int) progress);
                }
            });

        } else {
            Toast.makeText(getContext(), "Nenhum arquivo selecionado", Toast.LENGTH_LONG).show();


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            fotoUsuario.setImageURI(mImageUri);

            //Picasso.with(this).load(mImageUri).into(mImageView);


        }


    }


    private String getFileExtension(Uri uri) {

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);


    }

}


