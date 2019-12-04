package com.example.a18190_18343_projetoed2;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**Classe para objeto do tipo Caminho, onde serão contidos os caminhos entre uma cidade origem e uma cidade destino
 * @author Lorenna Nunes 18343 e Maria Eduarda 18190
 * @version
 * @since
 * */
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

    /** Método que cria a matriz de adjacência
     */
    public void CriarAdjacencias(BucketHash bucket, BufferedReader br, Graph g, ArrayList<String> caminhos, boolean dist){
        int origem;
        int destino;
        int distancia;
        int tempo;

        try {

            String linha;

            linha = br.readLine();
            while (linha != null) {
                String s = linha.substring(inicioOrigem, inicioDestino);
                s = s.trim();
                String origemS = s;
                origem = bucket.data[(bucket.Hash(s))].getFirst().data.indiceCidade;


                s = linha.substring(inicioDestino, inicioDistanciaX).trim();
                s = s.trim();
                String destinoS = s;
                destino = bucket.data[(bucket.Hash(s))].getFirst().data.indiceCidade;
                s = linha.substring(inicioDistanciaX, inicioDistanciaY).trim();
                s = s.trim();
                distancia = Integer.parseInt(s);

                s = linha.substring(inicioDistanciaY).trim();
                s = s.trim();
                tempo = Integer.parseInt(s);
                if(dist)
                    g.newEdge(origem, destino, distancia); // cria uma nova aresta(caminhoe entre cidades) usando a distancia se for o que o usuário pediu
                else
                    g.newEdge(origem, destino, tempo); // cria uma nova aresta(caminhoe entre cidades) usando a tempo se for o que o usuário pediu


                caminhos.add(origemS + "/"+ destinoS);
                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
