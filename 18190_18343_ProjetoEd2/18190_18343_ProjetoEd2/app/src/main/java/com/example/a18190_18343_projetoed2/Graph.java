package com.example.a18190_18343_projetoed2;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Graph
{

    class Distance
    {
        public int distance;
        public int vertice;
        public Distance(int vp, int d)
        {
            distance = d;
            vertice = vp;
        }
    }

    class Time
    {
        public int time;
        public int vertice;
        public Time(int vp, int d)
        {
            time = d;
            vertice = vp;
        }
    }

    class Vertice
    {
        public boolean isVisited;
        public String label;
        private boolean isActive;

        public Vertice(String label)
        {
            this.label = label;
            isVisited = false;
            isActive = true;
        }
    }
    private Vertice[] vertices;
    int[][] adjacencias;
    int numVerts;

    /// DJIKSTRA
    Distance[] percurso;
    int infinity = 1000000;
    int verticeAtual;   // global usada para indicar o vértice atualmente sendo visitado
    int doInicioAteAtual;   // global usada para ajustar menor caminho com Djikstra
    int nTree;

    public Graph()
    {
        vertices = new Vertice[1000];
        adjacencias = new int[1000][1000];
        numVerts = 0;
        nTree = 0;

        for (int j = 0; j < 1000; j++)      // zera toda a matriz
            for (int k = 0; k < 1000; k++) {
                adjacencias[j][k] = infinity; // distância tão grande que não existe

            }

        percurso = new Distance[1000];
    }

    public void NewVertice(String label)
    {
        vertices[numVerts] = new Vertice(label);
        numVerts++;
    }

    public void NovaAresta(int origem, int destino)
    {
        adjacencias[origem][destino] = 1;
    }

    public void NovaAresta(int origem, int destino, int distancia)
    {
        adjacencias[origem][destino] = distancia;
    }

    public void ExibirVertice(int v)
    {
        System.out.println(vertices[v].label + " ");
    }

    public void ExibirVertice(int v, TextView txt)
    {
        txt.setText(txt.getText().toString() + vertices[v].label + " " );
    }

    public int NoSuccessors() 	// encontra e retorna a linha de um vértice sem sucessores
    {
        boolean has;
        for (int linha = 0; linha < numVerts; linha++)
        {
            has = false;
            for (int col = 0; col < numVerts; col++)
                if (adjacencias[linha][col] != infinity)
                {
                    has = true;
                    break;
                }
            if (!has)
                return linha;
        }
        return -1;
    }

    public void removerVertice(int vert)
    {
        if (vert != numVerts - 1)
        {
            for (int j = vert; j < numVerts - 1; j++)   // remove vértice do vetor
                vertices[j] = vertices[j + 1];

            // remove vértice da matriz
            for (int row = vert; row < numVerts; row++)
                moverLinhas(row, numVerts - 1);
            for (int col = vert; col < numVerts; col++)
                moverColunas(col, numVerts - 1);
        }
        numVerts--;

    }
    private void moverLinhas(int row, int length)
    {
        if (row != numVerts - 1)
            for (int col = 0; col < length; col++) {
                adjacencias[row][col] =adjacencias[row + 1][col];  // desloca para excluir

            }
    }
    private void moverColunas(int col, int length)
    {
        if (col != numVerts - 1)
            for (int row = 0; row < length; row++) {
                adjacencias[row][col] = adjacencias[row][col + 1]; // desloca para excluir

            }
    }
    /*public void exibirAdjacencias()
    {
        dgv.RowCount = numVerts + 1;
        dgv.ColumnCount = numVerts + 1;
        for (int j = 0; j < numVerts; j++)
        {
            dgv.Rows[j + 1].Cells[0].Value = vertices[j].rotulo;
            dgv.Rows[0].Cells[j + 1].Value = vertices[j].rotulo;
            for (int k = 0; k < numVerts; k++)
            {
                if (adjMatrix[j, k] != infinity)
                {
                    dgv.Rows[j + 1].Cells[k + 1].Style.BackColor = System.Drawing.Color.Yellow;
                    dgv.Rows[j + 1].Cells[k + 1].Value = Convert.ToString(adjMatrix[j, k]);
                }
                else
                dgv.Rows[j + 1].Cells[k + 1].Value = "";

            }
        }
    }*/

    public String OrdenacaoTopologica()
    {
        Stack<String> gPilha = new Stack<String>(); // para guardar a sequência de vértices
        int origVerts = numVerts;
        while (numVerts > 0)
        {
            int currVertex = NoSuccessors();
            if (currVertex == -1)
                return "Erro: grafo possui ciclos.";
            gPilha.Push(vertices[currVertex].label);   // empilha vértice
            removerVertice(currVertex);
        }
        String resultado = "Sequência da Ordenação Topológica: ";
        try {
            while (gPilha.Size() > 0)
                resultado += gPilha.Pop() + " ";    // desempilha para exibir
        }
        catch(Exception err)
        {

        }
        return resultado;
    }

    private int ObterVerticeAdjacenteNaoVisitado(int v)
    {
        for (int j = 0; j <= numVerts - 1; j++)
            if ((adjacencias[v][j] != infinity) && (!vertices[j].isVisited))
        return j;
        return -1;
    }

    public void PercursoEmProfundidade(TextView txt)
    {
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[0].isVisited = true;
        ExibirVertice(0, txt);
        gPilha.Push(0);
        int v;
        while (gPilha.Size() > 0)
        {
            try {
                v = ObterVerticeAdjacenteNaoVisitado(gPilha.Top());
                if (v == -1)
                    gPilha.Pop();
                else {
                    vertices[v].isVisited = true;
                    ExibirVertice(v, txt);
                    gPilha.Push(v);
                }
            }
            catch(Exception err)
            {

            }

        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].isVisited = false;
    }

    void ProcessarNo(int i, TextView txt)
    {
        txt.setText(txt.getText()+ vertices[i].label);
    }

    public void PercursoEmProfundidadeRec(int ad[][][], int numVerts, int part, TextView txt)
    {
        int i;
        ProcessarNo(part, txt);
        vertices[part].isVisited = true;
        for (i = 0; i < numVerts; ++i)
            if (ad[part][i][0] != infinity && !vertices[i].isVisited)
        PercursoEmProfundidadeRec(ad, numVerts, i, txt);
    }

    public void percursoPorLargura(TextView txt)
    {
        Queue<Integer> gQueue = new Queue<Integer>();
        vertices[0].isVisited = true;
        ExibirVertice(0, txt);
        gQueue.enqueue(0);
        int vert1, vert2;
        while (gQueue.size() >0 )
        {
            try {
                vert1 = gQueue.dequeue();
                vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
                while (vert2 != -1) {
                    vertices[vert2].isVisited = true;
                    ExibirVertice(vert2, txt);
                    gQueue.enqueue(vert2);
                    vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
                }
            }
            catch(Exception err){}
        }
        for (int i = 0; i < numVerts; i++)
            vertices[i].isVisited = false;
    }

    public void ArvoreGeradoraMinima(int primeiro, TextView txt)
    {
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[primeiro].isVisited = true;
        gPilha.Push(primeiro);
        int currVertex, ver;
        while (gPilha.Size() > 0)
        {
            try {
                currVertex = gPilha.Top();
                ver = ObterVerticeAdjacenteNaoVisitado(currVertex);
                if (ver == -1)
                    gPilha.Pop();
                else {
                    vertices[ver].isVisited = true;
                    gPilha.Push(ver);
                    ExibirVertice(currVertex, txt);
                    txt.setText(txt.getText()+"-->");
                    ExibirVertice(ver, txt);
                    txt.setText(txt.getText()+"  ");
                }
            }
            catch(Exception err)
            {}
        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].isVisited = false;
    }

    public String Caminho(int inicioDoPercurso, int finalDoPercurso,  ListView lista)
    {
        for (int j = 0; j < numVerts; j++)
            vertices[j].isVisited = false;

        vertices[inicioDoPercurso].isVisited = true;
        for (int j = 0; j < numVerts; j++)
        {
            // anotamos no vetor percurso a distância entre o inicioDoPercurso e cada vértice
            // se não há ligação direta, o valor da distância será infinity
            int tempDist = adjacencias[inicioDoPercurso][j];
            percurso[j] = new Distance(inicioDoPercurso, tempDist);
        }

        for (int nTree = 0; nTree < numVerts; nTree++)
        {
            // Procuramos a saída não visitada do vértice inicioDoPercurso com a menor distância
            int indiceDoMenor = ObterMenor();

            // e anotamos essa menor distância
            int distanciaMinima = percurso[indiceDoMenor].distance;


            // o vértice com a menor distância passa a ser o vértice atual
            // para compararmos com a distância calculada em AjustarMenorCaminho()
            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].distance;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            vertices[verticeAtual].isVisited = true;
            AjustarMenorCaminho(lista);
        }

        ArrayList<String> x = new ArrayList<>();
        return ExibirPercursos(inicioDoPercurso, finalDoPercurso, lista, x);
    }

    public int ObterMenor()
    {
        int distanciaMinima = infinity;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(vertices[j].isVisited) && (percurso[j].distance < distanciaMinima))
            {
                distanciaMinima = percurso[j].distance;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    public void AjustarMenorCaminho(ListView lista)
    {
        for (int coluna = 0; coluna < numVerts; coluna++)
            if (!vertices[coluna].isVisited)       // para cada vértice ainda não visitado
            {
                // acessamos a distância desde o vértice atual (pode ser infinity)
                int atualAteMargem = adjacencias[verticeAtual][coluna];

                // calculamos a distância desde inicioDoPercurso passando por vertice atual até
                // esta saída
                int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                // quando encontra uma distância menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma da distância
                // percorrida para nele chegar
                int distanciaDoCaminho = percurso[coluna].distance;
                if (doInicioAteMargem < distanciaDoCaminho)
                {
                    percurso[coluna].vertice = verticeAtual;
                    percurso[coluna].distance = doInicioAteMargem;
                    ExibirTabela(lista);
                }
            }
    }

    public void ExibirTabela(ListView lista)
    {
        String dist = "";
        for (int i = 0; i < numVerts; i++)
        {
            if (percurso[i].distance == infinity)
                dist = "inf";
            else
                dist = percurso[i].distance + "";

            //lista.Items.Add(vertices[i].label + "\t" + vertices[i].isVisited +
                    //"\t\t" + dist + "\t" + vertices[percurso[i].vertice].label);
            Log.d("aj: ", vertices[i].label + "\t" + vertices[i].isVisited +
                    "\t\t" + dist + "\t" + vertices[percurso[i].vertice].label);
        }
        Log.d("j: ", "\n\n\n");
        //lista.Items.Add("-----------------------------------------------------");
    }

    public String ExibirPercursos(int inicioDoPercurso, int finalDoPercurso, ListView lista, ArrayList<String> x)
    {
        String linha = "", resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += vertices[j].label + "=";
            if (percurso[j].distance == infinity)
                linha += "inf";
            else
                linha += percurso[j].distance;
            String pai = vertices[percurso[j].vertice].label;
            linha += "(" + pai + ") ";
        }
        x.add(linha);
        x.add("");
        x.add("");
        x.add("Caminho entre " + vertices[inicioDoPercurso].label +
                " e " + vertices[finalDoPercurso].label);
        x.add("");

        int onde = finalDoPercurso;
        Stack<String> pilha = new Stack<String>();

        int cont = 0;
        while (onde != inicioDoPercurso)
        {
            onde = percurso[onde].vertice;
            pilha.Push(vertices[onde].label);
            cont++;
        }

        while (pilha.Size() != 0)
        {
            try {
                resultado += pilha.Pop();
                if (pilha.Size() != 0)
                    resultado += "/";
            }
            catch(Exception err){}
        }

        if ((cont == 1) && (percurso[finalDoPercurso].distance == infinity))
            resultado = "Não há caminho";
        else
            resultado += "/" + vertices[finalDoPercurso].label;
        Log.d("Caminho: ", resultado);
        return resultado;
    }
}
