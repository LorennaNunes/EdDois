package com.example.a18190_18343_projetoed2;

public class Queue<X extends Comparable<X>> extends List<X>{
    public void enqueue(X elemento) // inclui objeto “elemento”
    {
        Node<X> novoNo = new Node<X>(elemento, null);
        super.insertAfterEnd(novoNo);
    }
    public X dequeue() throws Exception
    {                     // retira da fila
        if (!isEmpty())
        {
            X elemento = super.getFirst().data;
            super.remove(null, getFirst());
            return elemento;
        }
        throw new Exception("Fila vazia");
    }
    public X begin() throws Exception
    {                     // sem retirá-lo da fila
        if (isEmpty())
            throw new Exception("Fila vazia");
        X o = super.getFirst().data; // acessa o 1o objeto genérico
        return o;
    }
    public X end() throws Exception
    {                  // sem retirá-lo da fila
        if (isEmpty())
            throw new Exception("Fila vazia");

        X o = super.getLast().data; // acessa o 1o objeto genérico
        return o;
    }
    public int size() // devolve número de elementos da fila
    {
        return super.qtd;  // tamanho da lista ligada herdada
    }
    public boolean isEmpty()
    {
        return super.isEmpty();
    }
}
