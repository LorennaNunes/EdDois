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

/**A main do projeto onde são feitas as rotas dos caminhos escolhidos
 * @author Lorenna Nunes 18343 e Maria Eduarda 18190
 * @version
 * @since
 * */
public class MainActivity extends AppCompatActivity {
    Button btnBuscar, btnNovaCidade, btnNovoCaminho, btnCriar;
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

    ArrayList<String> caminhosParaAdicionarCaminho;
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
        btnCriar = findViewById(R.id.btnCriarArquivos);

        dist_temp = (EditText) findViewById(R.id.txtDistTemp);
        spinnerDe = (Spinner) findViewById(R.id.spinnerDe);
        spinnerPara = (Spinner) findViewById(R.id.spinnerPara);
        spinnerEscolha = (Spinner) findViewById(R.id.spinnerE);
        imgMapa = (ImageView) findViewById(R.id.imgMapa);

        caminhosParaAdicionarCaminho = new ArrayList<String>();

        //caso o botão para buscar o caminho entre as cidades for selecionado
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cidades();
                mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);
                c  = new Canvas(mutableBitmap);
                BuscarCaminho(spinnerDe.getSelectedItem().toString(), spinnerPara.getSelectedItem().toString());
            }
        });
        //botão para recriar os arquivos originais no armazenamento interno
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    escreverArquivoInterno();
                    Cidades();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // avisa que é necessário recriar os arquivos na inicialização em cada novo emulador
        Toast.makeText(this, "É necessário recriar os arquivos na inicialização do programa em um novo emulador!", Toast.LENGTH_LONG).show();

        //cria os  objetos necessários para fazer o canvas na imagem e todas as configurações
        BitmapFactory.Options myoptions = new BitmapFactory.Options();
        myoptions.inScaled = true;
        myoptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

        bitmapO = BitmapFactory.decodeResource(getResources(), R.drawable.mapaep, myoptions);
        p = new Paint();
        p.setColor(Color.RED);

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5f);
        workingBitmap = Bitmap.createBitmap(bitmapO);
        mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        c = new Canvas(mutableBitmap);

        //cria o grafo, bucketHash, os caminhos e um arrayList necessário para fazer os spinners com dados dos nomes das cidades
        grafo = new Graph();
        listaCidades = new BucketHash();
        nomesCidades = new ArrayList<>();
        caminhos = new Caminho();
        Cidades();


        ArrayList<String> obj = new ArrayList<>(); // arrayList com as possiveis escolhas de seleção de caminho
        obj.add("Distancia");
        obj.add("Tempo");
        ArrayAdapter<String> adapterEscolha = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, obj);
        ArrayAdapter<String> adapter = new  ArrayAdapter(this,android.R.layout.simple_spinner_item, nomesCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDe.setAdapter(adapter);
        spinnerPara.setAdapter(adapter);
        spinnerEscolha.setAdapter(adapterEscolha);


        //caso o botão de criar uma nova cidade for selecionado
        btnNovaCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCidade.class);
                Bundle b = new Bundle();
                b.putSerializable("ultimoIndice", nomesCidades.size()); // passa o ultimo indice de cidades existente no arquivo
                b.putSerializable("cidadesNome", nomesCidades); // nomes das cidades para não adicionar uma mesma cidade
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        //caso o botão de criar um novo caminho for selecionado
        btnNovoCaminho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCaminho.class);
                Bundle b = new Bundle();
                b.putSerializable("cidadesNome", nomesCidades); // manda os nomes das cidades para adicionar nos Spinner da classe TelaCaminho
                b.putSerializable("caminhos", caminhosParaAdicionarCaminho);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    /** Método que lê as cidades do arquivo texto e as adiciona em uma lista
     */
    public void Cidades(){
        String nome;
        String linha;
        float distanciaX;
        float distanciaY;
        int id;
        int adjacencias[][][] = new int[500][500][2];

        //constantes para a leitura do arquivo
        final int inicioIndice =0;
        final int tamanhoIndice =2;
        final int inicioNome = tamanhoIndice + inicioIndice;
        final int tamanhoNome = 16;
        final int inicioX = inicioNome + tamanhoNome;

        try {
            //leitura do arquivo
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Cidades.txt")));
            linha = br.readLine();
            nomesCidades = new ArrayList<>();
            
            //enquanto houver elementos na linha que está sendo lida
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

                cidade = new Cidade(nome,distanciaX,distanciaY, id); // adiciona uma nova cidade
                listaCidades.Insert(cidade); // insere a cidade no BucketHash
                grafo.newVertice(nome); // adiciona a cidade no grafo


                c.drawCircle(distanciaX*mutableBitmap.getWidth(),distanciaY*mutableBitmap.getHeight(),10,p); // desenha o ponto na localização da cidade
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
        //se o usuario não escolher cidade de origem e destino
        if(origem.equals("") || destino.equals("")) {
            Toast.makeText(getApplicationContext(), "Selecione as cidades de origem e de destino", Toast.LENGTH_SHORT).show();
        }
        //se o usuário tiver escolhido a mesma cidade como origem e destino
        else if(origem.equals(destino))
        {
            Toast.makeText(getApplicationContext(),"Para haver uma rota as cidades de origem e destino devem ser diferentes", Toast.LENGTH_LONG).show();
        }
        else
        {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(openFileInput("GrafoTremEspanhaPortugal.txt"))); // abre o arquivo do armazenamento interno para leitura
            } catch (IOException e) {
                e.printStackTrace();
            }
            //caso o usuário escolher o percurso pola distância entre as cidades
            if(spinnerEscolha.getSelectedItemPosition() == 0)
                //criamos sua matriz de adjacência
                caminhos.CriarAdjacencias(listaCidades, br, grafo, caminhosParaAdicionarCaminho,true);
            else
                //caso o usuário escolher o percurso pelo tempo entre as cidades
                //criamos sua matriz de adjacência
                caminhos.CriarAdjacencias(listaCidades, br, grafo,caminhosParaAdicionarCaminho, false);

            int o = listaCidades.data[(listaCidades.Hash(origem))].getFirst().data.indiceCidade; // define o id da cidade de origem
            int d = listaCidades.data[(listaCidades.Hash(destino))].getFirst().data.indiceCidade;// define o id da cidade de destino

            String menorCaminho = grafo.path(o,d); // acha o menor caminho da cidade de origem para a cidade de destino escolhidas
            if(menorCaminho.equals("Não há caminho")) // caso não encontre caminho
            {
                Toast.makeText(this, "Não há caminho!!", Toast.LENGTH_LONG).show();
            }
            else {
                if(spinnerEscolha.getSelectedItemPosition()==0) // caso o usuário tenha escolhido pela distancia mostra a distancia do percurso
                    dist_temp.setText("" + "Distância: " + grafo.percurse[listaCidades.data[(listaCidades.Hash(destino))].getFirst().data.indiceCidade].distance + " quilômetros");
                else // caso o usuario tenha escolhido pelo tempo mostra o tempo do percurso
                    dist_temp.setText("" + "Tempo: " + grafo.percurse[listaCidades.data[(listaCidades.Hash(destino))].getFirst().data.indiceCidade].distance + " minutos");
                String[] caminhoSeparado = menorCaminho.split("/"); // serpara o caminho em cidades
                int controle = 0;
                Cidade[] controleDeIndices = new Cidade[2]; // um vetor para saber o trecho de caminho que precisa mostrar para o usuario na imagem
                for (String caminhoMenor : caminhoSeparado) {

                    controleDeIndices[controle] = listaCidades.data[listaCidades.Hash(caminhoMenor)].getFirst().data; // adiciona a cidade atual lida após pesquisa pelo nome no buckethash no controle
                    controle++;
                    if (controle == 2) { // caso tenha duas cidades é necessário desenhar a linha entre elas
                        float inicioX = controleDeIndices[0].coordenadaX;
                        float inicioY = controleDeIndices[0].coordenadaY;

                        float fimX = controleDeIndices[1].coordenadaX;
                        float fimY = controleDeIndices[1].coordenadaY;
                        //adiciona a linha
                        c.drawLine(inicioX * mutableBitmap.getWidth(), inicioY * mutableBitmap.getHeight(), fimX * mutableBitmap.getWidth(), fimY * mutableBitmap.getHeight(), p);
                        controleDeIndices[0] = controleDeIndices[1]; // a primeira posição recebe a segunda
                        controle = 1; // controle recebe 1 já que falta uma cidade apenas para completar o percurso
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

            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("GrafoTremEspanhaPortugal.txt", Context.MODE_PRIVATE), "UTF-8"); // cria um arquivo interno para escrever os caminhos
            String linha;
            br = new BufferedReader(new InputStreamReader(getAssets().open("GrafoTremEspanhaPortugal.txt"), "UTF-8")); // abre o arquivo dentro de assets com os caminhos

            linha = br.readLine();
            String dado = "";
            while (linha != null) {
                dado += linha + "\n";
                linha = br.readLine();
            }

            writer.write(dado); // escreve o arquivo de caminhos de assets dentro do arquivo de armazenamento interno
            writer.flush();
            writer.close();
            OutputStreamWriter writer2 = new OutputStreamWriter(openFileOutput("Cidades.txt", Context.MODE_PRIVATE), "UTF-8");// cria um arquivo interno para escrever os caminhos
            br2 = new BufferedReader(new InputStreamReader(getAssets().open("Cidades.txt"), "UTF-8"));//abre o arquivo de cidades do assets
            linha = br2.readLine();
            dado = "";
            while (linha != null) {
                dado += linha + "\n";
                linha = br2.readLine();
            }

            writer2.write(dado);// escreve o arquivo de cidades de assets dentro do arquivo de armazenamento interno
            writer2.flush();
            writer2.close();
        }
        catch(Exception err)
        {

        }
    }
}
