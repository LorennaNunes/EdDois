package com.example.a18190_18343_projetoed2;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Caminho {
    int cidadeOrigem;
    int cidadeDestino;
    int qtd;

    int[][][] adjacencias;

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
        adjacencias = new int [500][500][2];
    }

    public Caminho(){
        adjacencias = new int [500][500][2];
        qtd=0;
    }

    public void CriarAdjacencias(ArrayList<Cidade> array, BufferedReader br){
        String origem;
       // int distancia;
        String destino;
        int distancia;
        int tempo;

        try {

            String linha;

            linha = br.readLine();
            while (linha != null) {
                String s = linha.substring(inicioOrigem, inicioDestino);
                s = s.trim();
                origem = s;

                s = linha.substring(inicioDestino, inicioDistanciaX).trim();
                s = s.trim();
                destino = s;

                s = linha.substring(inicioDistanciaX, inicioDistanciaY).trim();
                s = s.trim();
                distancia = Integer.parseInt(s);

                s = linha.substring(inicioDistanciaY).trim();
                s = s.trim();
                tempo = Integer.parseInt(s);
                int i = 0;
                int j = 0;
                boolean podeX = true;
                boolean podeY = true;
                for(Cidade c : array)
                {
                    if(!c.getNomeCidade().equals(origem) && podeX) {
                        i++;
                    }
                    else
                        podeX = false;
                    if(!c.getNomeCidade().equals(destino) && podeY) {
                        j++;
                    }
                    else
                        podeY = false;

                    if(!podeX && !podeY)
                        break;
                }
                adjacencias[i][j][0] = distancia;
                adjacencias[i][j][1] = tempo;
                linha = br.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
