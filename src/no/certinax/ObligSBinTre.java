package no.certinax;

////////////////// ObligSBinTre /////////////////////////////////

import java.sql.SQLOutput;
import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
    private static final class Node<T>      // en indre nodeklasse
    {
        private T verdi;                    // nodens verdi
        private Node<T> venstre, høyre;     // venstre og høyre barn
        private Node<T> forelder;           // forelder


        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
        {
            this.verdi = verdi;
            venstre = v; høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder) // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() { return "" + verdi; }
    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;              // p starter i roten
        int cmp = 0;                            // hjelpevariabel

        while (p != null)                       // fortsetter til p er ute av treet
        {
            q = p;
            cmp = comp.compare(verdi,p.verdi);  // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;  // flytter p

        }

        // p er nå null, dvs.ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q);               // oppretter en ny node

        if (q == null) rot = p;                 // p blir rotnode
        else if (cmp < 0) q.venstre = p;        // venstre barn til q
        else q.høyre = p;                       // høyre barn til q

        antall++;                               // én verdi mer i treet
        return true;                            // vellykket innlegging
    }

    @Override
    public boolean inneholder(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi)
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    @Override
    public int antall()
    {
        return antall;
    }

    public int antall(T verdi)
    {

        int count = 0;

        Stack<Node<T>> stack = new Stack<>();
        stack.add(rot);

        while(!stack.empty())
        {
            Node<T> p = stack.pop();
            int cmp = comp.compare(verdi, p.verdi);
            if(cmp == 0) count++;

            if(p.høyre != null) stack.add(p.høyre);
            if(p.venstre != null) stack.add(p.venstre);
        }

        return count;
    }


    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public Node<T> inorden() {
        if (tom())
            throw new NoSuchElementException("Treet er tom!");
        Node<T> p = rot;
        System.out.println("Roten: " + rot);
        System.out.println("Rotens venstre: " + rot.venstre + " Venstre for venstre: " + rot.venstre.venstre + " Høyre for venstre: " + rot.venstre.høyre);
        System.out.println("Rotens høyre: " + rot.høyre + " Venstre for høyre: " + rot.høyre.venstre + " Høyre for høyre: " + rot.høyre.høyre);
        //while(p.venstre != null) p = p.venstre;
        if(p.venstre == null && p.høyre == null) {
            return null;
        }
        System.out.println("kommer jeg hit?");
        return nesteInorden(p);
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {

        /*if(p.venstre != null) {
            p = p.venstre;
            return nesteInorden(p);
        }
        System.out.println(p);
       // T verdi = p.verdi;

        if(p.høyre != null) {
            p = p.høyre;
            return nesteInorden(p);
        }
        System.out.println(p);

        return p.forelder;*/
        if (p.venstre != null) {
            p = p.venstre;
            return nesteInorden(p);
        }

        //return p.forelder;

        if (p.høyre != null) {
            p = p.høyre;
            if (p.venstre != null) {
                p = p.venstre;
                nesteInorden(p);
            }
            return nesteInorden(p);
        }

        if (p.forelder.høyre != null) {
            p = p.forelder.høyre;
            return nesteInorden(p);
        }
        return p.forelder;
    }

    @Override
    public String toString()
    {
        if(antall == 1) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        for (int i = 1; i < antall-1; i++) {
            s.add(nesteInorden(inorden()).verdi.toString());
        }
        //if (!tom()) inorden(x -> s.add(x));

        return s.toString();
    }

    public String omvendtString()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String høyreGren()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String lengstGren()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String[] grener()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String bladnodeverdier()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String postString()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T>
    {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()              // konstruktør
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return p != null;       // Denne skal IKKE endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }
    } // end class BladnodeIterator


} // class ObligSBinTre