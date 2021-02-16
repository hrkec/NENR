package hr.fer.nenr.lab1;

public class Debug {
    public static void print(IDomain domain, String headingText){
        if (headingText != null){
            System.out.println(headingText);
        }
        for(DomainElement e : domain){
            System.out.println("Element domene: " + e);
        }
        System.out.println("Kardinalitet domene je: " + domain.getCardinality());
        System.out.println();
    }

    public static void print(IFuzzySet set, String headingText){
        if (headingText != null){
            System.out.println(headingText);
        }
        IDomain domain = set.getDomain();
        for(DomainElement e : domain){
            System.out.format("d(%d)=%.6f\n", e.getComponentValue(0), set.getValueAt(e));
        }
        System.out.println();
    }

}
