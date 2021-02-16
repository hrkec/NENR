package hr.fer.nenr.lab1;

public interface IDomain extends Iterable<DomainElement> {
    int getCardinality();
    IDomain getComponent(int idx);
    int getNumberOfComponents();
    int indexOfElement(DomainElement domainElement);
    DomainElement elementForIndex(int idx);
}
