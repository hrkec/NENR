package hr.fer.nenr.lab2;

import hr.fer.nenr.lab1.*;

public class Relations {
    public static boolean isUtimesURelation(IFuzzySet relation){
        IDomain domain = relation.getDomain();
        IDomain component1 = domain.getComponent(0);
        IDomain component2 = domain.getComponent(1);
        return component1.equals(component2);
    }

    public static boolean isSymmetric(IFuzzySet relation){
        boolean uTimesU = isUtimesURelation(relation);
        if(!uTimesU){
            return false;
        }

        int l, r;
        IDomain domain = relation.getDomain();
        for(DomainElement element : domain){
            l = element.getComponentValue(0);
            r = element.getComponentValue(1);

            if(l != r){
                DomainElement symmetricElement = DomainElement.of(r, l);
                if(relation.getValueAt(element) != relation.getValueAt(symmetricElement)){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation){
        boolean uTimesU = isUtimesURelation(relation);
        if(!uTimesU){
            return false;
        }

        int l, r;
        IDomain domain = relation.getDomain();
        for(DomainElement element : domain){
            l = element.getComponentValue(0);
            r = element.getComponentValue(1);
            if(l == r) {
                if(relation.getValueAt(element) != 1){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        boolean uTimesU = isUtimesURelation(relation);
        if (!uTimesU) {
            return false;
        }

        IDomain domain = relation.getDomain();
        IDomain domainComponent = domain.getComponent(0);
        DomainElement left, right;

        double max, a, b, value;
        for(DomainElement elem : domain){
            max = 0;
            for(DomainElement middle : domainComponent){
                left = DomainElement.of(elem.getComponentValue(0), middle.getComponentValue(0));
                right = DomainElement.of(middle.getComponentValue(0), elem.getComponentValue(1));
                a = relation.getValueAt(left);
                b = relation.getValueAt(right);
                max = Math.max(max, Math.min(a, b));
            }
            value = relation.getValueAt(elem);
            if(value < max){
                return false;
            }
        }
        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet relation1, IFuzzySet relation2) {
        IDomain domain1 = relation1.getDomain();
        IDomain domain2 = relation2.getDomain();

        IDomain x = domain1.getComponent(0);
        IDomain y = domain2.getComponent(0);
        IDomain z = domain2.getComponent(1);

        IDomain compositionDomain = Domain.combine(x, z);
        MutableFuzzySet composition = new MutableFuzzySet(compositionDomain);

        double max, a, b;
        for(DomainElement elem : compositionDomain){
            DomainElement left, right;
            max = 0;
            for(DomainElement middle : y){
                left = DomainElement.of(elem.getComponentValue(0), middle.getComponentValue(0));
                right = DomainElement.of(middle.getComponentValue(0), elem.getComponentValue(1));
                a = relation1.getValueAt(left);
                b = relation2.getValueAt(right);
                max = Math.max(max, Math.min(a, b));
            }
            composition.set(elem, max);
        }
        return composition;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return Relations.isReflexive(relation) && Relations.isSymmetric(relation) && Relations.isMaxMinTransitive(relation);
    }
}
