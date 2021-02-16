package hr.fer.nenr.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CompositeDomain extends Domain{
    private final List<SimpleDomain> domains = new ArrayList<>();

    public CompositeDomain(SimpleDomain... simpleDomains){
        this.domains.addAll(Arrays.asList(simpleDomains));
    }

    @Override
    public int getCardinality() {
        int cardinality = 1;
        for(SimpleDomain domain : this.domains){
            cardinality *= domain.getCardinality();
        }
        return cardinality;
    }

    @Override
    public IDomain getComponent(int idx) {
        return this.domains.get(idx);
    }

    @Override
    public int getNumberOfComponents() {
        return this.domains.size();
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new CompositeIterator();
    }

    private class CompositeIterator implements Iterator<DomainElement> {
        private final List<Iterator<DomainElement>> iterators = new ArrayList<>();
        private final List<Integer> currentValues = new ArrayList<>();
        private final int last = CompositeDomain.this.getCardinality();
        private int current = 0;

        public CompositeIterator() {
            for (IDomain domain : CompositeDomain.this.domains) {
                Iterator<DomainElement> iterator = domain.iterator();
                iterators.add(iterator);
            }

            for (Iterator<DomainElement> iterator : this.iterators) {
                int value = iterator.next().getComponentValue(0);
                currentValues.add(value);
            }
        }

        @Override
        public boolean hasNext() {
            return current < last;
        }

        @Override
        public DomainElement next() {
            if (current == 0) {
                current++;
                return new DomainElement(currentValues);
            }
            current++;
            int value;
            for (int i = iterators.size() - 1; i > -1; i--) {
                if (!iterators.get(i).hasNext()) {
                    iterators.set(i, domains.get(i).iterator());

                    value = iterators.get(i).next().getComponentValue(0);
                    currentValues.set(i, value);
                } else {
                    value = iterators.get(i).next().getComponentValue(0);
                    currentValues.set(i, value);

                    break;
                }
            }
            return new DomainElement(currentValues);
        }
    }
}
