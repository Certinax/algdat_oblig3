/*
////////////////////////////////////
////ALGORITMER OG DATASTRUKTURER////
////////////////////////////////////
///////Obligatorisk oppgave 3///////
////////////////////////////////////
/////////MATHIAS LUND AHRN//////////
////////////////////////////////////
//////////////S319217///////////////
 */
package no.certinax;

////////////////// ObligSBinTre /////////////////////////////////

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
        endringer++;                            // innlegging er en endring
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

    public int fjernAlle(T verdi)
    {
        int fjernet = 0;

        while(fjern(verdi)) {
            fjernet++;
        }

        return fjernet;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> current = rot, parent = null;   // q skal være forelder til p, r brukes for tilfeller med to barn

        while (current != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,current.verdi);      // sammenligner
            if (cmp < 0) { parent = current; current = current.venstre; }      // går til venstre
            else if (cmp > 0) { parent = current; current = current.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (current == null) return false;   // finner ikke verdi

        if (current.venstre == null || current.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> barn = current.venstre != null ? current.venstre : current.høyre;  // barn
            if(barn != null) {
                barn.forelder = parent;
            }

            if (current == rot) {
                rot = barn;
                //rot.forelder = null;
            }
            else if (current == parent.venstre) {
                parent.venstre = barn;
                //barn.forelder = current.forelder;
            }
            else {
                parent.høyre = barn;
                //barn.forelder = current.forelder;
            }

        }
        else  // Tilfelle 3)
        {
            Node<T> forelder = current, nextInorder = current.høyre;   // finner neste i inorden
            while (nextInorder.venstre != null)
            {
                forelder = nextInorder;    // s er forelder til r
                nextInorder = nextInorder.venstre;
            }

            current.verdi = nextInorder.verdi;   // kopierer verdien i r til p

            if (forelder != current) forelder.venstre = nextInorder.høyre;
            else forelder.høyre = nextInorder.høyre;

            if(nextInorder.høyre != null) {
                nextInorder.høyre.forelder = forelder;
            }
        }

        antall--;   // det er nå én node mindre i treet
        endringer++; // fjerne en verdi er en endring
        return true;
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
        if(!tom()) nullstill(rot);
        rot = null;
        antall = 0;
        endringer++;        // nullstill er en endring
    }

    private void nullstill(Node<T> p) {
        if(p.venstre != null) {
            nullstill(p.venstre);
            p.venstre = null;
        }
        if(p.høyre != null) {
            nullstill(p.høyre);
            p.høyre = null;
        }
        p.verdi = null;
    }


    private static <T> Node<T> nesteInorden(Node<T> p) {
            if(p.høyre != null) {
                p = p.høyre;
                while(p.venstre != null) {
                    p = p.venstre;
                }
                return p;
            }

            while(p.forelder != null) {
                if (p == p.forelder.høyre) {
                    p = p.forelder;
                } else {
                    return p.forelder;
                }
            }

        return null;
    }

    @Override
    public String toString()
    {
        if(antall == 0) return "[]";

        Node<T> p = rot;
        while(p.venstre != null) p = p.venstre;

        StringJoiner s = new StringJoiner(", ", "[", "]");

        while(p != null) {
            s.add(p.verdi.toString());
            p = nesteInorden(p);
        }

        return s.toString();
    }

    public String omvendtString()
    {

        if(antall == 0) return "[]";

        ArrayDeque<Node<T>> stakk = new ArrayDeque<>();

        Node<T> p = rot;
        while(p.venstre != null) p = p.venstre;

        StringJoiner s = new StringJoiner(", ", "[","]");

        while(p != null) {
            stakk.addFirst(p);
            p = nesteInorden(p);
        }

        while(!stakk.isEmpty()) {
            s.add(stakk.removeFirst().toString());
        }

        return s.toString();
    }

    public String høyreGren()
    {
        if(tom()) return "[]";

        Node<T> p = rot;

        StringJoiner s = new StringJoiner(", ", "[", "]");

        s.add(p.verdi.toString());

        while(!(p.venstre == null && p.høyre == null)) {
            if(p.høyre != null) {
                p = p.høyre;
            } else {
                p = p.venstre;
            }
            s.add(p.toString());
        }


        return s.toString();
    }

    private static <T> Node<T> grenByggern(Node<T> p, ArrayDeque<Node<T>> grenBygger) {
        while(p.høyre != null) {
            p = p.høyre;
            grenBygger.addFirst(p);
            while(p.venstre != null) {
                p = p.venstre;
                grenBygger.addFirst(p);
            }
        }
        return p;
    }
    public String lengstGren()
    {
        if(tom()) return "[]";

        /**
         * Fra Venstreside
         */

        StringJoiner s = new StringJoiner(", ", "[", "]");
        ArrayDeque<Node<T>> lengsteGren = new ArrayDeque<>();
        ArrayDeque<Node<T>> nestLengstGren = new ArrayDeque<>();


        Node<T> p = rot;
        lengsteGren.addFirst(p);

        // finner node lengst til venstre (altså første inorden)
        while(p.venstre != null) {
            p = p.venstre;
            lengsteGren.addFirst(p);
        }

        p = grenByggern(p, lengsteGren);

        // Kopiere lengsteGrenDeque over til nestelengst
        nestLengstGren = lengsteGren.clone();

        // finne neste bladnode
        while(p.forelder != null) {
            if(p == p.forelder.venstre) // sjekker om bladnode er venstrebarn
            {
                if(p.forelder.høyre != null) // må sjekke om det eksisterer et høyrebarn til p sin forelder
                {
                    p = p.forelder;
                    nestLengstGren.removeFirst();

                    p = grenByggern(p, nestLengstGren);

                    // Sjekker her om nåværende gren er lengre enn den lengste
                    if(nestLengstGren.size() > lengsteGren.size()) {
                        lengsteGren = nestLengstGren.clone();
                    }
                }
                else // p sin forelder har ikke høyrebarn
                {
                    p = p.forelder;
                    nestLengstGren.removeFirst();
                }
            }
            else // p er høyrebarn
            {
                // må gå oppover i treet for å finne en forelder som er et venstrebarn
                p = p.forelder;
                nestLengstGren.removeFirst();
            }
        }

        while(!lengsteGren.isEmpty()) {
            s.add(lengsteGren.removeLast().toString());
        }

        return s.toString();
    }

    public String[] grener()
    {
        // Finner antall bladnoder via hjelpemetode
        // Setter størrelsen til string tabellen vha antallet som blir returnert
        String[] grener = new String[antallBladnoder()];
        int i = 0;

        ArrayDeque<Node<T>> gren = new ArrayDeque<>();


        int antall = 0;
        Node<T> p = rot;
        if(rot == null) return grener;
        gren.addLast(p);

        // finner node lengst til venstre (altså første inorden)
        while(p.venstre != null) {
            p = p.venstre;
            gren.addLast(p);
        }

        // finne første bladnode
        while(p.høyre != null) {
            p = p.høyre;
            gren.addLast(p);
            while(p.venstre != null) {
                p = p.venstre;
                gren.addLast(p);
            }
        }

        grener[i++] = gren.toString();

        // finne neste bladnode
        while(p.forelder != null) {
            if(p == p.forelder.venstre) // sjekker om bladnode er venstrebarn
            {
                if(p.forelder.høyre != null) // må sjekke om det eksisterer et høyrebarn til p sin forelder
                {
                    p = p.forelder;
                    gren.removeLast();
                    while(p.høyre != null) {
                        p = p.høyre;
                        gren.addLast(p);
                        while(p.venstre != null) {
                            p = p.venstre;
                            gren.addLast(p);
                        }
                    }
                    // Legger til neste gren i string-arrayet
                    grener[i++] = gren.toString();
                }
                else // p sin forelder har ikke høyrebarn
                {
                    p = p.forelder;
                    gren.removeLast();
                }
            }
            else // p er høyrebarn
            {
                // må gå oppover i treet for å finne en forelder som er et venstrebarn
                p = p.forelder;
                gren.removeLast();
            }
        }

        return grener;
    }

    // Hjelpemetode
    public int antallBladnoder() {
        return rot == null ? 0 : antallBladnoder(rot);
    }

    // Hjelpemetode
    private static int antallBladnoder(Node<?> p) {
        if(p.venstre == null && p.høyre == null) return 1;

        return (p.venstre == null ? 0 : antallBladnoder(p.venstre)) +
                (p.høyre == null ? 0 : antallBladnoder(p.høyre));
    }

    private ArrayDeque<Node<T>> queue = new ArrayDeque<>();

    private void recursiveBladNodeVerdier(Node<T> p) {
        if(p.venstre != null) recursiveBladNodeVerdier(p.venstre);
        if(p.høyre != null) recursiveBladNodeVerdier(p.høyre);
        if(p.venstre == null && p.høyre == null) queue.addFirst(p);
    }


    public String bladnodeverdier()
    {

        if(tom()) return "[]";
        if(antall == 1) return "[" + rot.toString() + "]";
        recursiveBladNodeVerdier(rot);
        StringJoiner sj = new StringJoiner(", ","[","]");
        while(!queue.isEmpty()) {
            sj.add(queue.removeLast().toString());
        }
        return sj.toString();
    }

    public String postString()
    {
        ArrayDeque<Node<T>> list = new ArrayDeque<>();
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();

        Node<T> p = rot;
        Node<T> forelder = null;
        if(p == null) return "[]";
        queue.addFirst(p);

        while(!queue.isEmpty()) {
            Node current = queue.peek();

            if(forelder == null || forelder.venstre == current || forelder.høyre == current)
            {
                if(current.venstre != null)
                {
                    queue.addFirst(current.venstre);
                }
                else if (current.høyre != null)
                {
                    queue.addFirst(current.høyre);
                }
                else
                {
                    queue.pop();
                    list.addFirst(current);
                }
            }
            else if (current.venstre == forelder)
            {
                if (current.høyre != null)
                {
                    queue.addFirst(current.høyre);
                }
                else
                {
                    queue.pop();
                    list.addFirst(current);
                }
            }
            else if (current.høyre == forelder)
            {
                queue.pop();
                list.addFirst(current);
            }
            forelder = current;
        }

        StringJoiner sj = new StringJoiner(", ", "[","]");

        while(!list.isEmpty())
        {
            sj.add(list.removeLast().toString());
        }

        return sj.toString();

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
        private ArrayDeque<Node<T>> hjelpestakk = new ArrayDeque<>();

        private BladnodeIterator()              // konstruktør
        {
            if(rot != null) {
                while(p.venstre != null || p.høyre != null) {
                    if(p.venstre != null)
                        p = p.venstre;
                    else
                        p = p.høyre;
                }
            }

        }

        @Override
        public boolean hasNext()
        {
            return p != null;       // Denne skal IKKE endres!
        }

        @Override
        public T next()
        {

            if(!hasNext())
                throw new NoSuchElementException("Finnes ikke flere bladnoder i treet!");

            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException("Iteratorendringer er ikke like antall endringer!");

            removeOK = true;

            q = p;

            p = nesteInorden(p);

            if(p != null) {
                while (p.venstre != null || p.høyre != null) {
                    p = nesteInorden(p);
                    if(p == null)
                        break;
                }

            }

            return q.verdi;
        }

        @Override
        public void remove()
        {
            if(!removeOK)
                throw new IllegalStateException("Ikke lovlig tilstand!");
            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException("Iteratorendringer er ikke lik endringer!");
            removeOK = false;


            if(antall == 1) {
                rot = null;
            } else {
                if(q == q.forelder.venstre) {
                    q.forelder.venstre = null;
                } else {
                    q.forelder.høyre = null;
                }
            }

            antall--;
            endringer++;
            iteratorendringer++;
        }
    } // end class BladnodeIterator


} // class ObligSBinTre