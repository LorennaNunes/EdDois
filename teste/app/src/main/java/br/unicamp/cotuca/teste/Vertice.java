package br.unicamp.cotuca.teste;

public class Vertice {
        public Boolean foiVisitado;
        public String rotulo;
        private Boolean estaAtivo;

        public Vertice(String label)
        {
            this.rotulo = label;
            this.foiVisitado = false;
            this.estaAtivo = true;
        }
}
