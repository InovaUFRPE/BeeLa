package com.beela.beela.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;

import com.beela.beela.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CriarContaP1Activity extends AppCompatActivity {
    private Usuario usuario;

    private AlertDialog alerta;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private TextView displayDate;
    private EditText editTextNome;
    private EditText editTextData;
    private EditText editTextGenero;
    private Button buttonContinuar;

    private static final String TAG = "CriarContaP2Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_pt1);
        setarDataEditText();

        editTextGenero = (EditText) findViewById(R.id.editTextPedirGenero);

        editTextGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setarGeneroEditText();
            }
        });
        editTextNome = (EditText) findViewById(R.id.editTextPedirNome);
        editTextData = (EditText) findViewById(R.id.editTextPedirData);

        buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextNome.getText().toString().equals("") && !editTextData.getText().toString().equals("") && !editTextGenero.getText().toString().equals("")) {
                    usuario = new Usuario();
                    usuario.setNome(editTextNome.getText().toString());
                    usuario.setDataAniversario(editTextData.getText().toString());
                    usuario.setSexo(editTextGenero.getText().toString());

                    redirecionarCriarContaFinal();

                } else {
                    Toast.makeText(CriarContaP1Activity.this, "Preencha os campos de nome, data de nascimento e gênero!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void redirecionarCriarContaFinal() {
        Bundle bundle = new Bundle();
        bundle.putString("nome", usuario.getNome());
        bundle.putString("data", usuario.getDataAniversario());
        bundle.putString("genero", usuario.getSexo());

        Intent intent = new Intent(CriarContaP1Activity.this, CriarContaP2Activity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    public void setarDataEditText() {
        displayDate = (TextView) findViewById(R.id.editTextPedirData);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        CriarContaP1Activity.this,
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
                String date = dayOfMonth + "/" + month + "/" + year;
                editTextData.setText(date);
            }
        };
    }


    private void setarGeneroEditText() {
        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Feminino");
        itens.add("Masculino");
        itens.add("Não Binário");

        //adapter utilizando um layout customizado (TextView)
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.alert_criar_conta_pt1, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha seu gênero:");
        //define o diálogo como uma lista, passa o adapter.

        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                String generoEditText = adapter.getItem(arg1).toString();

                editTextGenero.setText(generoEditText);
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }


}
