package hr.fer.nenr.lab1;

public class CalculatedFuzzySet implements IFuzzySet {
    private final IDomain domain;
    private final IIntUnaryFunction intUnaryFunction;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
        this.domain = domain;
        this.intUnaryFunction = intUnaryFunction;
    }

    @Override
    public IDomain getDomain() {
        return this.domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return this.intUnaryFunction.valueAt(this.domain.indexOfElement(element));
    }
}
