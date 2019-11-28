package br.unicamp.cotuca.teste;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

public class Grafo {
    private final int numeroVertices = 20;
    private Vertice[] vertices;
    private int[][] adjMatrix;
    int numVerts;
    //DataGridView dgv

    //DJIKSTRA
    DistOriginal []percurso;
    int infinito = 1000000;
    int verticeAtual;   // variavel usada para indicar o vértice atualmente sendo visitado
    int doInicioAteAtual;   // variavel usada para ajustar menor caminho com Djikstra
    int nTree;

    public Grafo(){
        vertices = new Vertice[numeroVertices];
        adjMatrix = new int[numeroVertices][numeroVertices];
        numVerts = 0;
        nTree = 0;

        for (int j = 0; j < numeroVertices; j++)      // zera toda a matriz
            for (int k = 0; k < numeroVertices; k++)
                adjMatrix[j][k] = infinito; // distância tão grande que não existe

        percurso = new DistOriginal[numeroVertices];
    }
    public void NovoVertice(String label)
    {
        vertices[numVerts] = new Vertice(label);
        numVerts++;

        /*
        if (dgv != null)  // se foi passado como parâmetro um dataGridView para exibição
        {              // se realiza o seu ajuste para a quantidade de vértices
            dgv.RowCount = numVerts + 1;
            dgv.ColumnCount = numVerts + 1;
            dgv.Columns[numVerts].Width = 45;
        }*/
    }

    public void NovaAresta(int origem, int destino)
    {
        adjMatrix[origem][destino] = 1;
    }

    public void NovaAresta(int origem, int destino, int peso)
    {
        adjMatrix[origem][destino] = peso;
    }

    public void ExibirVertice(int v)
    {
        //Console.Write(vertices[v].rotulo + " ");
    }

    public int SemSucessores() 	// encontra e retorna a linha de um vértice sem sucessores
    {
        boolean temAresta;
        for (int linha = 0; linha < numVerts; linha++)
        {
            temAresta = false;
            for (int col = 0; col < numVerts; col++)
                if (adjMatrix[linha][col] != infinito)
            {
                temAresta = true;
                break;
            }
            if (!temAresta)
                return linha;
        }
        return -1;
    }

    public void removerVertice(int vert)
    {
        /*
        if (dgv != null)
        {
            MessageBox.Show("Matriz de Adjacências antes de remover vértice " +
                    Convert.ToString(vert));
            exibirAdjacencias();
        }
        */
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
        /*
        if (dgv != null)
        {
            MessageBox.Show("Matriz de Adjacências após remover vértice " +
                    Convert.ToString(vert));
            exibirAdjacencias();
            MessageBox.Show("Retornando à ordenação");
        } */
    }
    private void moverLinhas(int row, int length)
    {
        if (row != numVerts - 1)
            for (int col = 0; col < length; col++)
                adjMatrix[row][col] = adjMatrix[row + 1][col];  // desloca para excluir
    }
    private void moverColunas(int col, int length)
    {
        if (col != numVerts - 1)
            for (int row = 0; row < length; row++)
                adjMatrix[row][col] = adjMatrix[row][col + 1]; // desloca para excluir
    }
    public void exibirAdjacencias()
    {
        /*
        dgv.RowCount = numVerts + 1;
        dgv.ColumnCount = numVerts + 1;
        for (int j = 0; j < numVerts; j++)
        {
            dgv.Rows[j + 1].Cells[0].Value = vertices[j].rotulo;
            dgv.Rows[0].Cells[j + 1].Value = vertices[j].rotulo;
            for (int k = 0; k < numVerts; k++)
            {
                if (adjMatrix[j, k] != infinito)
                {
                    dgv.Rows[j + 1].Cells[k + 1].Style.BackColor = System.Drawing.Color.Yellow;
                    dgv.Rows[j + 1].Cells[k + 1].Value = Convert.ToString(adjMatrix[j][k]);
                }
	                 else
                dgv.Rows[j + 1].Cells[k + 1].Value = "";
            }
        }*/
    }

    public String OrdenacaoTopologica()
    {
        Stack<String> gPilha = new Stack<String>(); // para guardar a sequência de vértices
        int origVerts = numVerts;
        while (numVerts > 0)
        {
            int currVertex = SemSucessores();
            if (currVertex == -1)
                return "Erro: grafo possui ciclos.";
            gPilha.push(vertices[currVertex].rotulo);   // empilha vértice
            removerVertice(currVertex);
        }
        String resultado = "Sequência da Ordenação Topológica: ";
        while (gPilha.capacity() > 0)
            resultado += gPilha.pop() + " ";    // desempilha para exibir
        return resultado;
    }

    private int ObterVerticeAdjacenteNaoVisitado(int v)
    {
        for (int j = 0; j <= numVerts - 1; j++)
            if ((adjMatrix[v][j] != infinito) && (!vertices[j].foiVisitado))
        return j;
        return -1;
    }

    public void PercursoEmProfundidade()//TextBox txt
    {
        //txt.Clear();
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[0].foiVisitado = true;
        //ExibirVertice(0, txt);
        gPilha.push(0);
        int v;
        while (gPilha.capacity() > 0)
        {
            v = ObterVerticeAdjacenteNaoVisitado(gPilha.peek());
            if (v == -1)
                gPilha.pop();
            else
            {
                vertices[v].foiVisitado = true;
                //ExibirVertice(v, txt);
                gPilha.push(v);
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].foiVisitado = false;
    }

    void ProcessarNo(int i)//, TextBox txt
    {
        //txt.Text += vertices[i].rotulo;
    }

    public void PercursoEmProfundidadeRec(int[][] adjMatrix, int numVerts, int part)//, TextBox txt
    {
        int i;
        ProcessarNo(part);
        vertices[part].foiVisitado = true;
        for (i = 0; i < numVerts; ++i)
            if (adjMatrix[part][i] != infinito && !vertices[i].foiVisitado)
        PercursoEmProfundidadeRec(adjMatrix, numVerts, i);
    }

    public void percursoPorLargura()//TextBox txt
    {
        //txt.Clear();
        Queue<Integer> gQueue = new Queue<Integer>() {
            @Override
            public boolean add(Integer integer) {
                return false;
            }

            @Override
            public boolean offer(Integer integer) {
                return false;
            }

            @Override
            public Integer remove() {
                return null;
            }

            @Nullable
            @Override
            public Integer poll() {
                return null;
            }

            @Override
            public Integer element() {
                return null;
            }

            @Nullable
            @Override
            public Integer peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Integer> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
        vertices[0].foiVisitado = true;
        //ExibirVertice(0, txt);
        gQueue.add(0);
        int vert1, vert2;
        while (gQueue.size() >0 )
        {
            vert1 = gQueue.remove(); // remove da fila
            vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            while (vert2 != -1)
            {
                vertices[vert2].foiVisitado = true;
                //ExibirVertice(vert2, txt);
                gQueue.add(vert2); // enfilera
                vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            }
        }
        for (int i = 0; i < numVerts; i++)
            vertices[i].foiVisitado = false;
    }

    public void ArvoreGeradoraMinima(int primeiro)//TextBox txt
    {
        //txt.Clear();
        Stack<Integer> gPilha = new Stack<Integer>(); // para guardar a sequência de vértices
        vertices[primeiro].foiVisitado = true;
        gPilha.push(primeiro);
        int currVertex, ver;
        while (gPilha.capacity() > 0)
        {
            currVertex = gPilha.peek();
            ver = ObterVerticeAdjacenteNaoVisitado(currVertex);
            if (ver == -1)
                gPilha.pop();
            else
            {
                vertices[ver].foiVisitado = true;
                gPilha.push(ver);
                //ExibirVertice(currVertex, txt);
                //txt.Text += "-->";
                //ExibirVertice(ver, txt);
                //txt.Text += "  ";
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].foiVisitado = false;
    }

    public String Caminho(int inicioDoPercurso, int finalDoPercurso, ArrayList lista)//
    {
        for (int j = 0; j < numVerts; j++)
            vertices[j].foiVisitado = false;

        vertices[inicioDoPercurso].foiVisitado = true;
        for (int j = 0; j < numVerts; j++)
        {
            // anotamos no vetor percurso a distância entre o inicioDoPercurso e cada vértice
            // se não há ligação direta, o valor da distância será infinity
            int tempDist = adjMatrix[inicioDoPercurso][j];
            percurso[j] = new DistOriginal(inicioDoPercurso, tempDist);
        }

        for (int nTree = 0; nTree < numVerts; nTree++)
        {
            // Procuramos a saída não visitada do vértice inicioDoPercurso com a menor distância
            int indiceDoMenor = ObterMenor();

            // e anotamos essa menor distância
            int distanciaMinima = percurso[indiceDoMenor].distancia;


            // o vértice com a menor distância passa a ser o vértice atual
            // para compararmos com a distância calculada em AjustarMenorCaminho()
            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].distancia;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            vertices[verticeAtual].foiVisitado = true;
            AjustarMenorCaminho(lista);
        }

        return ExibirPercursos(inicioDoPercurso, finalDoPercurso, lista);
    }

    public int ObterMenor()
    {
        int distanciaMinima = infinito;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(vertices[j].foiVisitado) && (percurso[j].distancia < distanciaMinima))
            {
                distanciaMinima = percurso[j].distancia;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    public void AjustarMenorCaminho(ArrayList lista)
    {
        for (int coluna = 0; coluna < numVerts; coluna++)
            if (!vertices[coluna].foiVisitado)       // para cada vértice ainda não visitado
            {
                // acessamos a distância desde o vértice atual (pode ser infinity)
                int atualAteMargem = adjMatrix[verticeAtual][coluna];

                // calculamos a distância desde inicioDoPercurso passando por vertice atual até
                // esta saída
                int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                // quando encontra uma distância menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma da distância
                // percorrida para nele chegar
                int distanciaDoCaminho = percurso[coluna].distancia;
                if (doInicioAteMargem < distanciaDoCaminho)
                {
                    percurso[coluna].verticePai = verticeAtual;
                    percurso[coluna].distancia = doInicioAteMargem;
                    ExibirTabela(lista);
                }
            }
        lista.add("==================Caminho ajustado==============");
    }

    public void ExibirTabela(ArrayList lista)
    {
        String dist = "";
        lista.add("Vértice\tVisitado?\tPeso\tVindo de");
        for (int i = 0; i < numVerts; i++)
        {
            if (percurso[i].distancia == infinito)
                dist = "inf";
            else
                dist = Integer.toString(percurso[i].distancia);

            lista.add(vertices[i].rotulo + "\t" + vertices[i].foiVisitado +
                    "\t\t" + dist + "\t" + vertices[percurso[i].verticePai].rotulo);
        }
        lista.add("-----------------------------------------------------");
    }

    public String ExibirPercursos(int inicioDoPercurso, int finalDoPercurso, ArrayList lista)
    {
        String linha = "", resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += vertices[j].rotulo + "=";
            if (percurso[j].distancia == infinito)
                linha += "inf";
            else
                linha += percurso[j].distancia;
            String pai = vertices[percurso[j].verticePai].rotulo;
            linha += "(" + pai + ") ";
        }
        lista.add(linha);
        lista.add("");
        lista.add("");
        lista.add("Caminho entre " + vertices[inicioDoPercurso].rotulo + " e " + vertices[finalDoPercurso].rotulo);
        lista.add("");

        int onde = finalDoPercurso;
        Stack<String> pilha = new Stack<String>();

        int cont = 0;
        while (onde != inicioDoPercurso)
        {
            onde = percurso[onde].verticePai;
            pilha.push(vertices[onde].rotulo);
            cont++;
        }

        while (pilha.capacity() != 0)
        {
            resultado += pilha.pop();
            if (pilha.capacity() != 0)
                resultado += " --> ";
        }

        if ((cont == 1) && (percurso[finalDoPercurso].distancia == infinito))
            resultado = "Não há caminho";
        else
            resultado += " --> " + vertices[finalDoPercurso].rotulo;
        return resultado;
    }
}
