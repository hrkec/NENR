package hr.fer.nenr.lab1;

public class MutableFuzzySet implements IFuzzySet{
    private final IDomain domain;
    double[] memberships;

    public MutableFuzzySet(IDomain domain){
        this.domain = domain;
        int cardinality = domain.getCardinality();
        this.memberships = new double[cardinality];
    }

    @Override
    public IDomain getDomain() {
        return this.domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        int idx = this.domain.indexOfElement(element);
        return memberships[idx];
    }

    public MutableFuzzySet set(DomainElement e, double mu){
        int idx = this.domain.indexOfElement(e);
        this.memberships[idx] = mu;
        return this;
    }
}
