package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.DomainElement;
import hr.fer.nenr.lab1.IDomain;
import hr.fer.nenr.lab1.IFuzzySet;
import hr.fer.nenr.lab1.MutableFuzzySet;

import java.util.List;

public class Rule {
    private final List<IFuzzySet> premise;
    private final IFuzzySet conclusion;

    public Rule(List<IFuzzySet> premise, IFuzzySet conclusion) {
        this.premise = premise;
        this.conclusion = conclusion;
    }

    public IFuzzySet apply(int[] values){
        double value = 1;
        for(int i = 0; i < premise.size(); i++){
            if(premise.get(i) == null) continue;

            DomainElement element = DomainElement.of(values[i]);
            value = Double.min(value, premise.get(i).getValueAt(element));
//            value *= premise.get(i).getValueAt(element);
        }
        return cutoff(conclusion, value);
    }

    public static IFuzzySet cutoff(IFuzzySet set, double value){
        IDomain domain = set.getDomain();
        MutableFuzzySet result = new MutableFuzzySet(domain);
        for(DomainElement element: domain){
            result = result.set(element, set.getValueAt(element) * value);
        }
        return result;
    }
}

