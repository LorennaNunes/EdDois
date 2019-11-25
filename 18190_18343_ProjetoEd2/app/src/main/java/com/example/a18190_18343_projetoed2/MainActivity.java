package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnBuscar, btnNovaCidade, btnNovoCaminho;
    Cidade cidade;
    Caminho caminhos;
    ArrayList<Cidade> listaCidades;
    ArrayList<String> nomesCidades;
    Spinner spinnerDe;
    Spinner spinnerPara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnNovaCidade = (Button)findViewById(R.id.btnNovaCidade);
        btnNovoCaminho = (Button)findViewById(R.id.btnNovoCaminho);

        spinnerDe = (Spinner) findViewById(R.id.spinnerDe);
        spinnerPara = (Spinner) findViewById(R.id.spinnerPara);


        listaCidades = new ArrayList<>();
        nomesCidades = new ArrayList<>();
        caminhos = new Caminho();


        nomesCidades = Cidades();


       //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cbDe,android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, nomesCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDe.setAdapter(adapter);
        spinnerPara.setAdapter(adapter);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("GrafoTremEspanhaPortugal.txt"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        caminhos.CriarAdjacencias(listaCidades, br);

    }

    public ArrayList<String> Cidades(){
        int id;
        String nome;
        float distanciaX;
        float distanciaY;

        int inicioIndice =0;
        int tamanhoIndice =2;
        int inicioNome = tamanhoIndice + inicioIndice;
        int tamanhoNome = 16;
        int inicioX = inicioNome + tamanhoNome;
        int tamanhoX =6;
        int inicioY = inicioX+tamanhoX;
        int tamanhoY = 5;
        int adjacencias[][][] = new int[500][500][2];

        ArrayList<String> ret = new ArrayList<>();
        String linha;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("Cidades.txt"), "UTF-8"));
            linha = br.readLine();

            while (linha != null) {
                String s = linha.substring(inicioIndice, tamanhoIndice);
                s = s.trim();
                id = Integer.parseInt(s);

                nome = linha.substring(inicioNome, tamanhoNome).trim();

                linha = linha.replace(',','.');
                String vet[] = linha.substring(inicioX).split(" ");

                distanciaX = Float.parseFloat(vet[0]);
                distanciaY = Float.parseFloat(vet[1]);

                cidade = new Cidade(id,nome,distanciaX,distanciaY);
                listaCidades.add(cidade);

                ret.add(cidade.nomeCidade);

                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public void CriarMatriz()
    {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("Cidades.txt"), "UTF-8"));
            String linha = br.readLine();

            while (linha != null) {

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

/*
    public ArrayList<String> NomesCidades(){
        int id;
        String nome;
        float distanciaX;
        float distanciaY;

        int inicioIndice =0;
        int tamanhoIndice =2;
        int inicioNome = tamanhoIndice + inicioIndice;
        int tamanhoNome = 16;
        int inicioX = inicioNome + tamanhoNome;
        int tamanhoX =6;
        int inicioY = inicioX+tamanhoX;
        int tamanhoY = 5;

        ArrayList<String> ret = new ArrayList<>();
        String linha;
        try {
            //FileInputStream stream = new FileInputStream("Cidades.txt");
            //FileInputStream stream = new FileInputStream("file:///android_asset/Cidades.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("Cidades.txt"), "UTF-8"));
            linha = br.readLine();

            while (linha != null) {
                String s = linha.substring(inicioIndice, tamanhoIndice);
                s = s.trim();
                id = Integer.parseInt(s);

                nome = linha.substring(inicioNome, tamanhoNome).trim();

                linha = linha.replace(',','.');
                String vet[] = linha.substring(inicioX).split(" ");

                distanciaX = Float.parseFloat(vet[0]);
                distanciaY = Float.parseFloat(vet[1]);

                cidade = new Cidade(id,nome,distanciaX,distanciaY);
                ret.add(cidade.nomeCidade);

                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }*/
}
