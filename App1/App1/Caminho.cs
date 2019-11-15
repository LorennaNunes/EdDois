using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace App1
{
    class Caminho
    {
        int cidadeOrigem;
        int cidadeDestino;
        int qtd;

        int[,] adjacencias;

        static int inicioOrigem = 0;
        static int tamanhoOrigem = 15;
        static int inicioDestino = tamanhoOrigem + inicioOrigem;
        static int tamanhoDestino = 16;
        static int inicioDistanciaX = tamanhoDestino + inicioDestino;
        static int tamanhoDistanciaX = 5;
        static int inicioDistanciaY = inicioDistanciaX + tamanhoDistanciaX;
        static int tamanhoDistanciaY = 5;

        public Caminho(int cidadeOrigem, int cidadeDestino)
        {
            this.cidadeOrigem = cidadeOrigem;
            this.cidadeDestino = cidadeDestino;

            qtd = 0;
            adjacencias = new int[53,53];
        }

        public Caminho()
        {
            adjacencias = new int[53,53];
            qtd = 0;
        }

        public void CriarAdjacencias()
        {
            String origem;
            // int distancia;
            String destino;
            String linha;

            try
            {
                StreamReader stream = new StreamReader("GrafoTremEspanhaPortugal.txt");
                linha = stream.ReadLine();
                while (!stream.EndOfStream)
                {
                    //origem = Convert.ToInt32(linha.Substring(inicioOrigem, tamanhoOrigem));
                    origem = linha.Substring(inicioOrigem, tamanhoOrigem);
                    destino =
                    linha = stream.ReadLine();
                }
            }
            catch (Exception e)
            {
            }

        }
    }
}