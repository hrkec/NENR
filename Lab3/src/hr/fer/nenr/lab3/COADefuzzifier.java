package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.DomainElement;
import hr.fer.nenr.lab1.IDomain;
import hr.fer.nenr.lab1.IFuzzySet;

public class COADefuzzifier implements Defuzzifier {
    @Override
    public int defuzzify(IFuzzySet set) {
        IDomain domain = set.getDomain();
        double b = 0, n = 0, value;
        for (DomainElement element : domain){
            value = set.getValueAt(element);
            b += value * element.getComponentValue(0);
            n += value;
        }

        return (int) (b / n);
    }
}
