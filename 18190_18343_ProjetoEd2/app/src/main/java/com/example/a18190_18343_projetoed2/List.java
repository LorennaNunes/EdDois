package com.example.a18190_18343_projetoed2;

import android.widget.Spinner;

public class List<X extends Comparable<X>> {

    private Node<X> first, last, before, current;
    int qtd;
    public Node<X> getFirst() {
        return first;
    }

    public Node<X> getLast() {
        return last;
    }
    public Node<X> getBefore() {
        return before;
    }

    public Node<X> getCurrent() {
        return current;
    }

    public void setCurrent(Node<X> current) {
        this.current = current;
    }

    public int getQtd() {
        return qtd;
    }



    public List()
    {
        first = last = before = current = null;
        qtd = 0;
    }

    public boolean isEmpty()
    {
        return first == null;
    }


    protected void insertBeforeBeginning(Node<X> newNode)
    {
        if (isEmpty())
            last = newNode;
        newNode.prox = first;
        first = newNode;
        qtd++;
    }

    public void insertBeforeBeginning(X information)
    {
        if (information != null)
        {
            Node<X> newNode = new Node<X>(information, null);
            insertBeforeBeginning(newNode);
        }
    }
    public void insertAfterEnd(Node<X> newNode)
    {
        if (isEmpty())
            first = newNode;
        else
            last.prox = newNode;
        newNode.prox = null;
        last= newNode;
        qtd++;
    }

    public void insertAfterEnd(X information)
    {
        if (information != null)
        {
            Node<X> novoNo = new Node<X>(information, null);
            insertAfterEnd(novoNo);
        }
    }

    /*public void toList(Spinner onde)
    {

        for (current = first; current != null; current = current.prox)
            onde.Items.Add(current.data);

        // ou
        // atual = primeiro;
        // while (atual != null)
        // {
        //    onde.Items.Add(atual.Info);
        //    atual = atual.Prox;
        // }
    }*/

    public boolean exists(X searched)
    {
        before = null;
        current = first;


        if (isEmpty())
            return false;

        if (searched.compareTo(first.data) < 0)
            return false;
        if (searched.compareTo(last.data) > 0)
        {
            before = last;
            current = null;
            return false;
        }

        boolean find = false;
        boolean end = false;
        while (!find && !end)
        {
            if (current == null)
                end = true;
            else
                if (searched.compareTo(current.data) == 0)
                    find = true;
                else
                    if (current.data.compareTo(searched) > 0)
                        end = true;
                    else
                    {
                        before = current;
                        current = current.prox;
                    }
        }
        return find;
    }

    public void insertInOrder(X data)
    {
        if (!exists(data)) // existeDado configurou anterior e atual
        {                       // aqui temos certeza de que a chave não existe
            Node<X> novo = new Node<X>(data, null); // guarda dados no
            // novo nó
            if (isEmpty()) // se a lista está vazia, então o
                insertBeforeBeginning(novo); // novo nó é o primeiro da lista
            else
            if (before == null && current != null)
                insertBeforeBeginning(novo); // liga novo antes do primeiro
            else
                insertInMiddle(novo); // insere entre os nós anterior e atual
        }
    }
    private void insertInMiddle(Node<X> newNode)
    {
        // existeDado() encontrou intervalo de inclusão do novo nó
        before.prox = newNode; // liga anterior ao novo
        newNode.prox = current; // e novo no atual
        if (before == last) // se incluiu ao final da lista,
            last = newNode; // atualiza o apontador ultimo
        qtd++; // incrementa número de nós da lista
    }

    public boolean remove(X data)
    {
        if (!exists(data))
            return false;

        remove(before, current);

        return true;
    }
    protected void remove(Node<X> before, Node<X> current)
    {
        if (before == null && current != null) // o procurado é o 1o nó
        {
            first = current.prox;
            if (first == null) // lista ficou vazia
                last = null;      // ajustamos ultimo para não ficar lixo
        }
        else
        {
            before.prox = current.prox;
            if (current == last)
                last = before;
        }
        qtd--;
    }

    private void searchMinor( Node<X> antM, Node<X> atuM)
    {
        antM = before = null;
        atuM = current = first;
        while (current != null)
        {
            if (current.data.compareTo(atuM.data) < 0 )
            {
                antM = before;
                atuM = current;
            }
            before = current;
            current = current.prox;
        }
    }
    public void order()
    {
        Node<X> menor = null, antMenor = null, noAIncluir = null;
        List<X> orderList = new List<X>();
        while (!this.isEmpty())
        {
            searchMinor(antMenor,menor);
            noAIncluir = menor;  // reaproveitamos os nós da lista original
            this.remove(antMenor, menor);
            orderList.insertAfterEnd(noAIncluir);
        }
        this.first = orderList.first;
        this.last = orderList.last;
        this.qtd = orderList.qtd;
        this.before = this.before = null;
    }

    public Node<X> get(int position)
    {
        int i = 0;
        for(current = first;i < position && current != null; i++, current = current.prox)
        {
        }
        return current;
    }


}
