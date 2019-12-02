package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

         final float y = Float.parseFloat(coordX.toString());
         final float x = Float.parseFloat(coordY.toString());

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cidade cidade = new Cidade(txtNomeCidade.toString(), x, y);
            }
        });
    }

}
