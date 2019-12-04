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
    int[][] matrix;
    int numVerts;
    int finalP;

    /// DJIKSTRA
    Distance[] percurse;
    int infinity = 1000000;
    int currentVertice;   // global usada para indicar o vértice atualmente sendo visitado
    int beginToEnd;   // global usada para ajustar menor caminho com Djikstra
    int nTree;

    public Graph()
    {
        vertices = new Vertice[1000];
        matrix = new int[1000][1000];
        numVerts = 0;
        nTree = 0;

        for (int j = 0; j < 1000; j++)      // zera toda a matriz
            for (int k = 0; k < 1000; k++) {
                matrix[j][k] = infinity; // distância tão grande que não existe

            }
        percurse = new Distance[1000];
    }

    public void newVertice(String label)
    {
        vertices[numVerts] = new Vertice(label);
        numVerts++;
    }

    public void newEdge(int origem, int destino)
    {
        matrix[origem][destino] = 1;
    }

    public void newEdge(int origem, int destino, int distancia)
    {
        matrix[origem][destino] = distancia;
    }

    public void showVertice(int v)
    {
        System.out.println(vertices[v].label + " ");
    }

    public void showVertice(int v, TextView txt)
    {
        txt.setText(txt.getText().toString() + vertices[v].label + " " );
    }

    public int noSuccessors() 	// encontra e retorna a linha de um vértice sem sucessores
    {
        boolean has;
        for (int linha = 0; linha < numVerts; linha++)
        {
            has = false;
            for (int col = 0; col < numVerts; col++)
                if (matrix[linha][col] != infinity)
                {
                    has = true;
                    break;
                }
            if (!has)
                return linha;
        }
        return -1;
    }

    public void removeVertice(int vert)
    {
        if (vert != numVerts - 1)
        {
            for (int j = vert; j < numVerts - 1; j++)   // remove vértice do vetor
                vertices[j] = vertices[j + 1];

            // remove vértice da matriz
            for (int row = vert; row < numVerts; row++)
                moveLines(row, numVerts - 1);
            for (int col = vert; col < numVerts; col++)
                moveColumns(col, numVerts - 1);
        }
        numVerts--;

    }
    private void moveLines(int row, int length)
    {
        if (row != numVerts - 1)
            for (int col = 0; col < length; col++) {
                matrix[row][col] = matrix[row + 1][col];  // desloca para excluir

            }
    }
    private void moveColumns(int col, int length)
    {
        if (col != numVerts - 1)
            for (int row = 0; row < length; row++) {
                matrix[row][col] = matrix[row][col + 1]; // desloca para excluir

            }
    }

    public String topologicOrder()
    {
        Stack<String> gPilha = new Stack<String>(); // para guardar a sequência de vértices
        while (numVerts > 0)
        {
            int currVertex = noSuccessors();
            if (currVertex == -1)
                return "Erro: grafo possui ciclos.";
            gPilha.Push(vertices[currVertex].label);   // empilha vértice
            removeVertice(currVertex);
        }
        String result = "Sequência da Ordenação Topológica: ";
        try {
            while (gPilha.Size() > 0)
                result += gPilha.Pop() + " ";    // desempilha para exibir
        }
        catch(Exception err)
        {

        }
        return result;
    }

    private int getAdjacentVerticeUnvisited(int v)
    {
        for (int j = 0; j <= numVerts - 1; j++)
            if ((matrix[v][j] != infinity) && (!vertices[j].isVisited))
        return j;
        return -1;
    }

    public void depthCourse(TextView txt) throws Exception
    {
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[0].isVisited = true;
        showVertice(0, txt);
        gPilha.Push(0);
        int v;
        //enquanto a pilha não for vazia
        while (gPilha.Size() > 0)
        {
            try {
                v = getAdjacentVerticeUnvisited(gPilha.Top());
                if (v == -1)
                    gPilha.Pop();
                else {
                    vertices[v].isVisited = true;
                    showVertice(v, txt);
                    gPilha.Push(v);
                }
            }
            catch(Exception err)
            {
                throw new Exception("Underflow de pilha");
            }

        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].isVisited = false;
    }

    void processNode(int i, TextView txt)
    {
        txt.setText(txt.getText()+ vertices[i].label);
    }

    public void depthCourseRec(int ad[][][], int numVerts, int part, TextView txt)
    {
        int i;
        processNode(part, txt);
        vertices[part].isVisited = true;
        for (i = 0; i < numVerts; ++i)
            if (ad[part][i][0] != infinity && !vertices[i].isVisited)
        depthCourseRec(ad, numVerts, i, txt);
    }

    public void percursePorLargura(TextView txt)
    {
        Queue<Integer> gQueue = new Queue<Integer>();
        vertices[0].isVisited = true;
        showVertice(0, txt);
        gQueue.enqueue(0);
        int vert1, vert2;
        while (gQueue.size() >0 )
        {
            try {
                vert1 = gQueue.dequeue();
                vert2 = getAdjacentVerticeUnvisited(vert1);
                while (vert2 != -1) {
                    vertices[vert2].isVisited = true;
                    showVertice(vert2, txt);
                    gQueue.enqueue(vert2);
                    vert2 = getAdjacentVerticeUnvisited(vert1);
                }
            }
            catch(Exception err){}
        }
        for (int i = 0; i < numVerts; i++)
            vertices[i].isVisited = false;
    }

    public void minimalGeneratingTree(int first, TextView txt)
    {
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[first].isVisited = true;
        gPilha.Push(first);
        int currVertex, ver;
        while (gPilha.Size() > 0)
        {
            try {
                currVertex = gPilha.Top();
                ver = getAdjacentVerticeUnvisited(currVertex);
                if (ver == -1)
                    gPilha.Pop();
                else {
                    vertices[ver].isVisited = true;
                    gPilha.Push(ver);
                    showVertice(currVertex, txt);
                    txt.setText(txt.getText()+"-->");
                    showVertice(ver, txt);
                    txt.setText(txt.getText()+"  ");
                }
            }
            catch(Exception err)
            {}
        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].isVisited = false;
    }

    public String path(int beginPercurse, int finalPercurse)
    {
        for (int j = 0; j < numVerts; j++)
            vertices[j].isVisited = false;

        vertices[beginPercurse].isVisited = true;
        for (int j = 0; j < numVerts; j++)
        {
            // anotamos no vetor percurse a distância entre o inicioDopercurse e cada vértice
            // se não há ligação direta, o valor da distância será infinity
            int tempDist = matrix[beginPercurse][j];
            percurse[j] = new Distance(beginPercurse, tempDist);
        }

        for (int nTree = 0; nTree < numVerts; nTree++)
        {
            // Procuramos a saída não visitada do vértice inicioDopercurse com a menor distância
            int indiceDoMenor = obtainSmaller();

            // e anotamos essa menor distância
            int distanciaMinima = percurse[indiceDoMenor].distance;


            // o vértice com a menor distância passa a ser o vértice atual
            // para compararmos com a distância calculada em AjustarMenorCaminho()
            currentVertice = indiceDoMenor;
            beginToEnd = percurse[indiceDoMenor].distance;

            // visitamos o vértice com a menor distância desde o inicioDopercurse
            vertices[currentVertice].isVisited = true;
            adjustSmallerPath();
        }

        ArrayList<String> x = new ArrayList<>();
        return showPercurse(beginPercurse, finalPercurse, x);
    }

    public int obtainSmaller()
    {
        int distanciaMinima = infinity;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(vertices[j].isVisited) && (percurse[j].distance < distanciaMinima))
            {
                distanciaMinima = percurse[j].distance;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    public void adjustSmallerPath()
    {
        for (int column = 0; column < numVerts; column++)
            if (!vertices[column].isVisited)       // para cada vértice ainda não visitado
            {
                // acessamos a distância desde o vértice atual (pode ser infinity)
                int current = matrix[currentVertice][column];

                // calculamos a distância desde inicioDopercurse passando por vertice atual até
                // esta saída
                int beginToMargin = beginToEnd + current;

                // quando encontra uma distância menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma da distância
                // percorrida para nele chegar
                int distanciaDoCaminho = percurse[column].distance;
                if (beginToMargin < distanciaDoCaminho)
                {
                    percurse[column].vertice = currentVertice;
                    percurse[column].distance = beginToMargin;
                    showTable();
                }
            }
    }

    public void showTable()
    {
        String dist = "";
        for (int i = 0; i < numVerts; i++)
        {
            if (percurse[i].distance == infinity)
                dist = "inf";
            else
                dist = percurse[i].distance + "";

            Log.d("aj: ", vertices[i].label + "\t" + vertices[i].isVisited +
                    "\t\t" + dist + "\t" + vertices[percurse[i].vertice].label);
        }
        Log.d("j: ", "\n\n\n");
    }

    public String showPercurse(int beginPercurse, int finalPercurse, ArrayList<String> x)
    {
        finalP = finalPercurse;
        String linha = "", resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += vertices[j].label + "=";
            if (percurse[j].distance == infinity)
                linha += "inf";
            else
                linha += percurse[j].distance;
            String pai = vertices[percurse[j].vertice].label;
            linha += "(" + pai + ") ";
        }
        x.add(linha);
        x.add("");
        x.add("");
        x.add("Caminho entre " + vertices[beginPercurse].label +
                " e " + vertices[finalPercurse].label);
        x.add("");

        int onde = finalPercurse;
        Stack<String> pilha = new Stack<String>();

        int cont = 0;
        while (onde != beginPercurse)
        {
            onde = percurse[onde].vertice;
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

        if ((cont == 1) && (percurse[finalPercurse].distance == infinity))
            resultado = "Não há caminho";
        else
            resultado += "/" + vertices[finalPercurse].label;
        Log.d("Caminho: ", resultado);
        return resultado;
    }
}
