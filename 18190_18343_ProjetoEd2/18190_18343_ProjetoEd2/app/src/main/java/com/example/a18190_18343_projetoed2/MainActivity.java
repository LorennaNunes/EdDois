package com.example.a18190_18343_projetoed2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnBuscar, btnNovaCidade, btnNovoCaminho;
    EditText dist_temp;
    Cidade cidade;
    Caminho caminhos;
    BucketHash listaCidades;
    ArrayList<String> nomesCidades;
    Spinner spinnerDe;
    Spinner spinnerPara;
    Spinner spinnerEscolha;
    ImageView imgMapa;
    Bitmap bitmapO, workingBitmap, mutableBitmap;

    Paint p;
    Canvas c;
    Graph grafo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnNovaCidade = (Button)findViewById(R.id.btnNovaCidade);
        btnNovoCaminho = (Button)findViewById(R.id.btnNovoCaminho);

        /*try {
            escreverArquivoInterno();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        dist_temp = (EditText) findViewById(R.id.txtDistTemp);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cidades();
                mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);
                c  = new Canvas(mutableBitmap);
                BuscarCaminho(spinnerDe.getSelectedItem().toString(), spinnerPara.getSelectedItem().toString());
            }
        });
        spinnerDe = (Spinner) findViewById(R.id.spinnerDe);
        spinnerPara = (Spinner) findViewById(R.id.spinnerPara);
        spinnerEscolha = (Spinner) findViewById(R.id.spinnerE);

        imgMapa = findViewById(R.id.imgMapa);

        BitmapFactory.Options myoptions = new BitmapFactory.Options();
        myoptions.inScaled = true;
        myoptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

        bitmapO = BitmapFactory.decodeResource(getResources(), R.drawable.mapaep, myoptions);
        p = new Paint();
        p.setColor(Color.BLACK);

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5f);
        workingBitmap = Bitmap.createBitmap(bitmapO);
        mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        c = new Canvas(mutableBitmap);
        grafo = new Graph();
        listaCidades = new BucketHash();
        nomesCidades = new ArrayList<>();
        caminhos = new Caminho();
        Cidades();


        ArrayList<String> obj = new ArrayList<>();
        obj.add("Distancia");
        obj.add("Tempo");
        ArrayAdapter<String> adapterEscolha = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, obj);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cbDe,android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, nomesCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDe.setAdapter(adapter);
        spinnerPara.setAdapter(adapter);
        spinnerEscolha.setAdapter(adapterEscolha);


        btnNovaCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCidade.class);
                Bundle b = new Bundle();
                b.putSerializable("ultimoIndice", nomesCidades.size() - 1);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        btnNovoCaminho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TelaCaminho.class);
                Bundle b = new Bundle();
                b.putSerializable("cidadesNome", nomesCidades);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void Cidades(){
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
        String linha;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Cidades.txt")));
            linha = br.readLine();

            while (linha != null) {
                String s = linha.substring(inicioIndice, tamanhoIndice);
                s = s.trim();
                id = Integer.parseInt(s);

                nome = linha.substring(inicioNome, tamanhoNome).trim();
                nomesCidades.add(nome);

                linha = linha.replace(',','.');
                String vet[] = linha.substring(inicioX).split(" ");

                distanciaX = Float.parseFloat(vet[0]);
                distanciaY = Float.parseFloat(vet[1]);

                cidade = new Cidade(nome,distanciaX,distanciaY, id);
                listaCidades.Insert(cidade);
                grafo.NewVertice(nome);


                c.drawCircle(distanciaX*mutableBitmap.getWidth(),distanciaY*mutableBitmap.getHeight(),10,p);
                linha = br.readLine();
            }

        }

        catch (Exception e){
            e.printStackTrace();
        }
        imgMapa.setImageBitmap(mutableBitmap);
    }


    public void BuscarCaminho(String origem, String destino)
    {
        if(origem.equals("") || destino.equals("")) {
            //avisar
        }
        else if(origem.equals(destino))
        {
            //avisar que selecionou o mesmo destino e origem

        }
        else
        {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(openFileInput("GrafoTremEspanhaPortugal.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(spinnerEscolha.getSelectedItemPosition() == 0)
                caminhos.CriarAdjacencias(listaCidades, br, grafo, true);
            else
                caminhos.CriarAdjacencias(listaCidades, br, grafo, false);
            int o = listaCidades.data[(listaCidades.Hash(origem))].getFirst().data.indiceCidade;
            int d = listaCidades.data[(listaCidades.Hash(destino))].getFirst().data.indiceCidade;

            String menorCaminho = grafo.Caminho(o,d);
            if(menorCaminho.equals("Não há caminho"))
            {
                Toast.makeText(this, "Não há caminho!!", Toast.LENGTH_LONG).show();
            }
            else {
                dist_temp.setText("" + grafo.percurso[listaCidades.data[(listaCidades.Hash(destino))].getFirst().data.indiceCidade].distance);
                String[] caminhoSeparado = menorCaminho.split("/");
                int controle = 0;
                Cidade[] controleDeIndices = new Cidade[2];
                for (String caminhoMenor : caminhoSeparado) {

                    controleDeIndices[controle] = listaCidades.data[listaCidades.Hash(caminhoMenor)].getFirst().data;
                    controle++;
                    if (controle == 2) {
                        float inicioX = controleDeIndices[0].coordenadaX;
                        float inicioY = controleDeIndices[0].coordenadaY;

                        float fimX = controleDeIndices[1].coordenadaX;
                        float fimY = controleDeIndices[1].coordenadaY;
                        c.drawLine(inicioX * mutableBitmap.getWidth(), inicioY * mutableBitmap.getHeight(), fimX * mutableBitmap.getWidth(), fimY * mutableBitmap.getHeight(), p);
                        controleDeIndices[0] = controleDeIndices[1];
                        controle = 1;
                    }
                }
                imgMapa.setImageBitmap(mutableBitmap);
            }
        }
    }

    public void escreverArquivoInterno() throws FileNotFoundException {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {

            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("GrafoTremEspanhaPortugal.txt", Context.MODE_PRIVATE));
            String linha;
            br = new BufferedReader(new InputStreamReader(getAssets().open("GrafoTremEspanhaPortugal.txt"), "UTF-8"));

            linha = br.readLine();
            String dado = "";
            while (linha != null) {
                dado += linha + "\n";
                linha = br.readLine();
            }

            writer.write(dado);
            writer.flush();
            writer.close();
            OutputStreamWriter writer2 = new OutputStreamWriter(openFileOutput("Cidades.txt", Context.MODE_PRIVATE));
            br2 = new BufferedReader(new InputStreamReader(getAssets().open("Cidades.txt"), "UTF-8"));
            linha = br2.readLine();
            dado = "";
            while (linha != null) {
                dado += linha + "\n";
                linha = br2.readLine();
            }

            writer2.write(dado);
            writer2.flush();
            writer2.close();
        }
        catch(Exception err)
        {

        }
    }
}
