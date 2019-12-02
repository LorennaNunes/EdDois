package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaCaminho extends AppCompatActivity {

    TextView txtDestino, txtOrigem, txtDistancia, txtTempo;
    Button btnAdicionar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_caminho);
        txtDestino = (TextView)findViewById(R.id.txtDestino);
        txtOrigem = (TextView)findViewById(R.id.txtOrigem);
        txtDistancia = (TextView)findViewById(R.id.txtDistancia);
        txtTempo = (TextView)findViewById(R.id.txtTempo);
        btnAdicionar = (Button)findViewById(R.id.btnAddCaminho);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Caminho caminho = new Caminho();
            }
        });
    }
}
