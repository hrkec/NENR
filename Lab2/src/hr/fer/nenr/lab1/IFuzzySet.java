package hr.fer.nenr.lab1;

public interface IFuzzySet {
    IDomain getDomain();
    double getValueAt(DomainElement element);
}
