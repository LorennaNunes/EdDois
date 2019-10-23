using System;

public class PilhaHerdaLista<Dado> : ListaSimples<Dado>, IStack<Dado> 
                                     where Dado : IComparable<Dado>
{
  public PilhaHerdaLista() : base()
  { 
    // construtor
  }
  public void Empilhar(Dado o)
  {
    NoLista<Dado> novoNo = new NoLista<Dado>(o, null);
    InserirAntesDoInicio(novoNo);
  }
  public Dado OTopo() 
  {
    Dado o;
    if (EstaVazia())
       throw new PilhaVaziaException("Underflow da pilha");
    o = Primeiro.Info;
    return o;
  }
  public Dado Desempilhar() 
  {
    if (EstaVazia())
       throw new PilhaVaziaException("Underflow da pilha");
    Dado o = Primeiro.Info;
    RemoverNo(null, Primeiro);  // remove o primeiro nó da lista ligada herdada
    return o; // devolve o objeto que estava no topo
  }

  public int Tamanho()
  {
    return QuantosNos;
  }

  public new bool EstaVazia()
  {
    return base.EstaVazia;
  }
}
