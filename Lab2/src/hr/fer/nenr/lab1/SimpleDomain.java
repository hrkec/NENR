package hr.fer.nenr.lab1;

import java.util.Iterator;

public class SimpleDomain extends Domain{
    private final int first, last;

    public SimpleDomain(int first, int last) {
        this.first = first; this.last = last;
    }

    public int getCardinality(){
        return this.last - this.first;
    }

    public IDomain getComponent(int idx){
        return this;
    }

    public int getNumberOfComponents(){
        return 1;
    }

    public int getFirst() {
        return this.first;
    }

    public int getLast() {
        return this.last;
    }

    @Override
    public Iterator<DomainElement> iterator(){
        return new Iterator<>() {
            private int current = first;

            @Override
            public boolean hasNext() {
                return current < last;
            }

            @Override
            public DomainElement next() {
                return new DomainElement(current++);
            }
        };
    }
}
