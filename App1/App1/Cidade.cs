using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;


    public class Cidade
    {
        int indiceCidade;
        String nomeCidade;
        double coordenadaX, coordenadaY;



        //declaraão e instânciação das constantes para a leitura do arquivo

        static int inicioIndice = 0;
        static int tamanhoIndice = 2;
        static int inicioNome = inicioIndice + tamanhoIndice;
        static int tamanhoNome = 16;
        static int inicioX = inicioNome + tamanhoNome;
        static int tamanhoX = 5;
        static int inicioY = inicioX + tamanhoX;
        static int tamanhoY = 6;


        public Cidade(int indice, String nome, double cX, double cY)
        {
            setIndiceCidade(indice);
            setNomeCidade(nome);
            setCoordenadaX(cX);
            setCoordenadaY(cY);
        }

        //construtor que recebe como parametro a linha do arquivo
        public Cidade(String l)
        {
            // indiceCidade = Convert.ToInt32(l.Substring(inicioId, tamanhoId));
        }

        public int getIndiceCidade()
        {
            return indiceCidade;
        }

        public void setIndiceCidade(int indiceCidade)
        {
            this.indiceCidade = indiceCidade;
        }

        public String getNomeCidade()
        {
            return nomeCidade;
        }

        public void setNomeCidade(String nomeCidade)
        {
            this.nomeCidade = nomeCidade;
        }

        public double getCoordenadaX()
        {
            return coordenadaX;
        }

        public void setCoordenadaX(double coordenadaX)
        {
            this.coordenadaX = coordenadaX;
        }

        public double getCoordenadaY()
        {
            return coordenadaY;
        }

        public void setCoordenadaY(double coordenadaY)
        {
            this.coordenadaY = coordenadaY;
        }
    }