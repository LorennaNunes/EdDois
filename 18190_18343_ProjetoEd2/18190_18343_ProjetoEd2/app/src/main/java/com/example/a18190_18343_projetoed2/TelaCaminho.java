package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;
/**Activity para adicionar um novo caminho
 * @author Lorenna Nunes 18343 e Maria Eduarda 18190
 * @version
 * @since
 * */
public class TelaCaminho extends AppCompatActivity {

    TextView txtDistancia, txtTempo;
    Button btnAdicionar, btnVoltar;
    Spinner spinnerDe, spinnerPara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_caminho);
        spinnerDe = (Spinner) findViewById(R.id.spinnerDe);
        spinnerPara = (Spinner) findViewById(R.id.spinnerPara);
        txtDistancia = (TextView)findViewById(R.id.txtDistancia);
        txtTempo = (TextView)findViewById(R.id.txtTempo);
        btnAdicionar = (Button)findViewById(R.id.btnAddCaminho);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        ArrayList<String> nomesCidades = (ArrayList<String>)getIntent().getSerializableExtra("cidadesNome");
        final ArrayList<String> caminhos = (ArrayList<String>)getIntent().getSerializableExtra("caminhos");
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cbDe,android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, nomesCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDe.setAdapter(adapter);
        spinnerPara.setAdapter(adapter);

        //quando o botão para adicionar um caminho for selecionado
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pode = true;
                //verifica se todos os campos foram preenchidos
                if (txtTempo.getText().equals("") || txtDistancia.getText().equals("") || txtTempo.getText().equals("0") || txtDistancia.getText().equals("0"))
                    Toast.makeText(TelaCaminho.this, "Informe todos os campos com valore diferentes de 0", Toast.LENGTH_SHORT).show();
                    //verifica se as cidades informadas são iguais
                else if (spinnerDe.getSelectedItem().equals(spinnerPara.getSelectedItem()))
                    Toast.makeText(TelaCaminho.this, "Precisa-se de cidades diferentes para criar um novo caminho", Toast.LENGTH_LONG).show();
                for (String caminho : caminhos) {
                    String[] x = caminho.split("/");
                    if (x[0].equals(spinnerDe.getSelectedItem()) && x[1].equals(spinnerPara.getSelectedItem()))
                    {
                        Toast.makeText(TelaCaminho.this, "Caminho já existe", Toast.LENGTH_LONG).show();
                        pode = false;
                    }
                }
                if (pode) {

                    String nomeOrigem = spinnerDe.getSelectedItem().toString();
                    String nomeDestino = spinnerPara.getSelectedItem().toString();
                    String distancia = txtDistancia.getText().toString();
                    String tempo = txtTempo.getText().toString();
                    int qtdFaltaOrigem = 15 - nomeOrigem.length();
                    int qtdFaltaDestino = 15 - nomeDestino.length();
                    int qtdFaltaDistancia = 5 - distancia.length();
                    int qtdFaltaTempo = 5 - tempo.length();
                    for (int i = 0; i < qtdFaltaOrigem; i++)
                        nomeOrigem = nomeOrigem + " ";
                    for (int i = 0; i < qtdFaltaDestino; i++)
                        nomeDestino = " " + nomeDestino;
                    for (int i = 0; i < qtdFaltaDistancia; i++)
                        distancia = " " + distancia;

                    for (int i = 0; i < qtdFaltaTempo; i++)
                        tempo = " " + tempo;
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("GrafoTremEspanhaPortugal.txt", Context.MODE_APPEND)); // abre o arquivo interno de caminho
                        writer.append(nomeOrigem + nomeDestino + distancia + tempo + "\n"); // adiciona o novo caminho formatado
                        writer.flush();
                        writer.close();
                        Toast.makeText(TelaCaminho.this, "Caminho adicionado", Toast.LENGTH_LONG).show(); // avisa que o caminho foi adicionado

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        //caso o usuário selecione o botão que volta para a tela principal
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaCaminho.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
