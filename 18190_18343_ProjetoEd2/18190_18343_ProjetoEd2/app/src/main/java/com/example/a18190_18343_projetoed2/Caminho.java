package com.example.a18190_18343_projetoed2;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Caminho {
    int cidadeOrigem;
    int cidadeDestino;
    int qtd;

    int inicioOrigem = 0;
    int tamanhoOrigem = 15;
    int inicioDestino = tamanhoOrigem + inicioOrigem;
    int tamanhoDestino = 15;
    int inicioDistanciaX = tamanhoDestino + inicioDestino;
    int tamanhoDistanciaX = 5;
    int inicioDistanciaY = inicioDistanciaX + tamanhoDistanciaX;
    int tamanhoDistanciaY = 5;

    public Caminho(int cidadeOrigem, int cidadeDestino) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        qtd = 0;
    }

    public Caminho(){
        qtd=0;
    }

    public void CriarAdjacencias(BucketHash bucket, BufferedReader br, Graph g){
        int origem;
       // int distancia;
        int destino;
        int distancia;
        int tempo;

        try {

            String linha;

            linha = br.readLine();
            while (linha != null) {
                String s = linha.substring(inicioOrigem, inicioDestino);
                s = s.trim();
                origem = bucket.data[(bucket.Hash(s))].getFirst().data.indiceCidade;


                s = linha.substring(inicioDestino, inicioDistanciaX).trim();
                s = s.trim();
                destino = bucket.data[(bucket.Hash(s))].getFirst().data.indiceCidade;
                s = linha.substring(inicioDistanciaX, inicioDistanciaY).trim();
                s = s.trim();
                distancia = Integer.parseInt(s);

                s = linha.substring(inicioDistanciaY).trim();
                s = s.trim();
                tempo = Integer.parseInt(s);
                g.NovaAresta(origem, destino, distancia, tempo);
                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
