package com.beela.beela.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.Helper.Upload;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

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
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static final String TAG = "PrincipalActivity";
    public ConfiguracaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_configuracao, container, false);

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
        datanacimento = view.findViewById(R.id.textViewDataNascimento);


        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                View alertview = getLayoutInflater().inflate(R.layout.dialog_conf, null);
                TextView alertTitulo = alertview.findViewById(R.id.textViewAlertConf);
                final EditText editTextAlterar = alertview.findViewById(R.id.editTextAlertConf);
                Button buttonConfirmar = alertview.findViewById(R.id.buttonAlertConf);

                alertTitulo.setText("Digite seu Nome: ");
                editTextAlterar.setHint("Nome");
                alertDialog.setView(alertview);
                final AlertDialog dialog = alertDialog.create();
                dialog.show();

                buttonConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        if (!editTextAlterar.getText().toString().isEmpty()) {

                            String emailCodificado = Codificador.codificador(preferencias.getUsuario().getEmail());
                            mDatebaseRef = FirebaseDatabase.getInstance().getReference("conta").child(emailCodificado).child("nome"); //mudar o child para cada informacao correspondente
                            mDatebaseRef.setValue(editTextAlterar.getText().toString());
                            preferencias.getUsuario().setNome(editTextAlterar.getText().toString());
                            Toast.makeText(getActivity(), "Nome alterado", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }


                    }
                });


            }
        });


        senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mudar senha com alert

            }
        });

//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                View alertview = getLayoutInflater().inflate(R.layout.dialog_conf, null);
//                TextView alertTitulo = alertview.findViewById(R.id.textViewAlertConf);
//                final EditText editTextAlterar = alertview.findViewById(R.id.editTextAlertConf);
//                Button buttonConfirmar = alertview.findViewById(R.id.buttonAlertConf);
//
//                alertTitulo.setText("Digite seu o E-mail: ");
//                editTextAlterar.setHint("e-mail");
//                alertDialog.setView(alertview);
//                final AlertDialog dialog = alertDialog.create();
//                dialog.show();
//
//                buttonConfirmar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//
//                    public void onClick(View v) {
//
//                        if (!editTextAlterar.getText().toString().isEmpty()) {
//                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            String emailCodificado = Codificador.codificador(preferencias.getUsuario().getEmail());
//                            mDatebaseRef = FirebaseDatabase.getInstance().getReference("conta").child(emailCodificado).child("id"); //mudar o child para cada informacao correspondente
//
//                            String emailcod = Codificador.codificador(editTextAlterar.getText().toString());
//                            mDatebaseRef.setValue(emailcod);
//
//                            String email = editTextAlterar.getText().toString();
//                            mDatebaseRef = FirebaseDatabase.getInstance().getReference("conta").child(emailCodificado).child("email"); //mudar o child para cada informacao correspondente
//                            mDatebaseRef.setValue(email);
//
//                            preferencias.getUsuario().setEmail(email);
//                            preferencias.getUsuario().setId(emailcod);
//
//                            Toast.makeText(getActivity(), "E-mail alterado", Toast.LENGTH_LONG).show();
//
//                            Toast.makeText(getActivity(),user.getEmail().toString(),Toast.LENGTH_LONG).show();
//                            user.updateEmail(email);
//
//                            dialog.dismiss();
//                        }
//
//
//                    }
//                });
//
//
//            }
//        });
//

        genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // mudar genero com alert

            }
        });


        datanacimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mudar data com alert

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                View alertview = getLayoutInflater().inflate(R.layout.dialog_conf, null);
                TextView alertTitulo = alertview.findViewById(R.id.textViewAlertConf);
                final EditText editTextAlterar = alertview.findViewById(R.id.editTextAlertConf);
                Button buttonConfirmar = alertview.findViewById(R.id.buttonAlertConf);

                alertTitulo.setText("Digite sua data de nascimento: ");
                editTextAlterar.setHint("data nascimento");
                alertDialog.setView(alertview);
                final AlertDialog dialog = alertDialog.create();
                dialog.show();

                editTextAlterar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar calendar = Calendar.getInstance();
                        int ano = calendar.get(Calendar.YEAR);
                        int mes = calendar.get(Calendar.MONTH);
                        int dia = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog dialog = new DatePickerDialog(
                                getActivity(),
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListener,
                                ano, mes, dia);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();


                    }


                });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.d(TAG, "dataSet: date: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
//                        editTextData.setText(date);
                        //Setar data no banco
                    }
                };


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


