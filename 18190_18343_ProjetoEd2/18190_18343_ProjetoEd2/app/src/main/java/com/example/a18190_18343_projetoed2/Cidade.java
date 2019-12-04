package com.example.a18190_18343_projetoed2;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**Classe para objeto do tipo Cidade, onde serão contidos as cidades
 * @author Lorenna Nunes e Maria Eduarda
 * @version
 * @since
 * */
public class Cidade implements Comparable<Cidade> {
    int indiceCidade;
    String nomeCidade;
    float coordenadaX, coordenadaY;

    /**O construtor Cidade
     * @param nome nome da cidade
     * @param cX coordenada do eixo x
     * @param cY coordenada do eixo y
     * @param indice indice da cidade
     * */
    public Cidade(String nome, float cX, float cY, int indice) {
        setNomeCidade(nome);
        setCoordenadaX(cX);
        setCoordenadaY(cY);
        setIndiceCidade(indice);
    }

    /** Método para retorno do índice da cidade de acordo com o arquivo texto
     * @return int - número do indice*/
    public int getIndiceCidade() {
        return indiceCidade;
    }

    /** Configura o indice da cidade*/
    public void setIndiceCidade(int indiceCidade) {
        this.indiceCidade = indiceCidade;
    }

    /** Método para retorno do nome da cidade
     * @return String - nome da cidade*/
    public String getNomeCidade() {
        return nomeCidade;
    }

    /** Configura o nome da cidade*/
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    /** Método para retorno do eixo x da cidade
     * @return float - número da coordenada x*/
    public float getCoordenadaX() {
        return coordenadaX;
    }

    /** Configura a coordenada do eixo x da cidade*/
    public void setCoordenadaX(float coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /** Método para retorno do eixo y da cidade
     * @return float - número da coordenada y*/
    public float getCoordenadaY() {
        return coordenadaY;
    }

    /** Configura a coordenada do eixo y da cidade*/
    public void setCoordenadaY(float coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    /** Método que compara duas cidades através de seus nomes*/
    @Override
    public int compareTo(Cidade o) {

        return this.getNomeCidade().compareTo(o.getNomeCidade());
    }
}
