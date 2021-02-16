package hr.fer.nenr.lab1;

public abstract class Domain implements IDomain {
    public static IDomain intRange(int lower, int upper){
        return new SimpleDomain(lower, upper);
    }

    public static IDomain combine(IDomain first, IDomain second){
        return new CompositeDomain((SimpleDomain) first, (SimpleDomain) second);
    }

    @Override
    public int indexOfElement(DomainElement domainElement) {
        int idx = 0;
        for(DomainElement element : this){
            if(element.equals(domainElement)){
                break;
            }
            idx++;
        }
        if(idx > this.getCardinality()){
            return -1;
        }
        return idx;
    }

    @Override
    public DomainElement elementForIndex(int idx) {
        int i = 0;
        for(DomainElement element: this){
            if(i == idx){
                return element;
            }
            i++;
        }
        // Element nije pronađen -> vrati pogrešku!
        throw new ArrayIndexOutOfBoundsException("Nevaljani indeks elementa domene.");
    }
}
