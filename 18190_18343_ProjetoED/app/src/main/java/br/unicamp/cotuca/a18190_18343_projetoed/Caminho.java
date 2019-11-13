package br.unicamp.cotuca.a18190_18343_projetoed;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Caminho {
    int cidadeOrigem;
    int cidadeDestino;
    int qtd;

    int[][] adjacencias;

    int inicioOrigem = 0;
    int tamanhoOrigem = 15;
    int inicioDestino = tamanhoOrigem + inicioOrigem;
    int tamanhoDestino = 16;
    int inicioDistanciaX = tamanhoDestino + inicioDestino;
    int tamanhoDistanciaX = 5;
    int inicioDistanciaY = inicioDistanciaX + tamanhoDistanciaX;
    int tamanhoDistanciaY = 5;

    public Caminho(int cidadeOrigem, int cidadeDestino) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;

        qtd = 0;
        adjacencias = new int [53][53];
    }

    public Caminho(){
        adjacencias = new int[53][53];
        qtd=0;
    }

    public void CriarAdjacencias(){
        String origem;
       // int distancia;
       String destino;
        String linha;

        try {
            FileInputStream stream = new FileInputStream("GrafoTremEspanhaPortugal.txt");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            linha = br.readLine();
            while (linha != null) {
                //origem = Convert.ToInt32(linha.Substring(inicioOrigem, tamanhoOrigem));
                origem = linha.substring(inicioOrigem, tamanhoOrigem);
                destino =
                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
