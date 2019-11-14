package br.unicamp.cotuca.a18190_18343_projetoed;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cidade {
    int indiceCidade;
    String nomeCidade;
    float coordenadaX, coordenadaY;



    //declaraão e instânciação das constantes para a leitura do arquivo

     int inicioIndice =0;
     int tamanhoIndice =2;
     int inicioNome = tamanhoIndice + inicioIndice;
     int tamanhoNome = 16;
     int inicioX = inicioNome + tamanhoNome;
     int tamanhoX =5;
     int inicioY = inicioX+tamanhoX;
     int tamanhoY = 6;


    public Cidade(int indice, String nome, float cX, float cY) {
        setIndiceCidade(indice);
        setNomeCidade(nome);
        setCoordenadaX(cX);
        setCoordenadaY(cY);
    }

    //construtor que recebe como parametro a linha do arquivo
    public Cidade(String l){
       // indiceCidade = Convert.ToInt32(l.Substring(inicioId, tamanhoId));
    }

    public int getIndiceCidade() {
        return indiceCidade;
    }

    public void setIndiceCidade(int indiceCidade) {
        this.indiceCidade = indiceCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public float getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(float coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public float getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(float coordenadaY) {
        this.coordenadaY = coordenadaY;
    }
}
