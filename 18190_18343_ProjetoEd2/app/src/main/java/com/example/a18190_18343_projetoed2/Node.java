package com.example.a18190_18343_projetoed2;

public class Node<X extends Comparable<X>> {
    public Node<X> prox;
    public X data;

    public Node(X insert, Node<X> p)
    {
        data = insert;
        prox = p;
    }

    public Node()
    {
        data = null;
        prox = null;
    }



}
