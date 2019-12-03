package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class TelaCidade extends AppCompatActivity {

    Button btnAdicionar;
    EditText coordX, coordY;
    TextView txtNomeCidade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cidade);

        btnAdicionar = (Button)findViewById(R.id.btnAddCidade);
        coordX = (EditText)findViewById(R.id.coordX);
        coordY= (EditText)findViewById(R.id.coordY);
        txtNomeCidade=(TextView)findViewById(R.id.txtNomeCidade);

        final int ultimo = (int)getIntent().getSerializableExtra("ultimoIndice");

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtdFaltaNome = 16 - txtNomeCidade.getText().toString().length();
                String nome = txtNomeCidade.getText().toString();
                int qtdFaltaCordx = 5 -coordX.toString().length();
                int qtdFaltaCordy = 6 -coordY.toString().length();
                String cordx = coordX.toString();
                String cordy = coordY.toString();
                for(int i=0; i<qtdFaltaNome; i++)
                    nome = nome+ " ";
                for(int i = 0; i < qtdFaltaCordx; i++)
                    cordx = " " + cordx;
                for(int i = 0; i < qtdFaltaCordy; i++)
                    cordy = " " + cordy;
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("Cidades.txt", Context.MODE_APPEND));
                    writer.append(ultimo + ""+ nome + cordx + cordy + "\n");
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
