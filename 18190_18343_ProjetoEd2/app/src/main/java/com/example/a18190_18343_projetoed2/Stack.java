package com.example.a18190_18343_projetoed2;

public class Stack<X extends Comparable<X>>{

    private Node<X> top;
    private int size;

    public int Size()
    {
        return size;
    }
    public boolean isEmpty()
    {
        return (top == null);
    }
    public void Push(X o)
    {
        Node<X> novoNo = new Node<X>(o, top);
        top = novoNo;
        size++;
    }
    public X Top() throws Exception
    {
        X o;
        if (isEmpty())
            throw new Exception("Underflow");
        o = top.data;
        return o;
    }
    public X Pop() throws Exception
    {
        if (isEmpty())
            throw new Exception("Underflow");
        X o = top.data;
        top = top.prox;
        size--;
        return o;
    }
}
