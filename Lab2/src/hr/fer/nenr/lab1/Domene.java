package hr.fer.nenr.lab1;

public class Domene {
    public static void main(String[] args) {
        IDomain d1 = Domain.intRange(0, 5); // {0, 1, 2, 3, 4}
        Debug.print(d1, "Elementi domene d1:");

        IDomain d2 = Domain.intRange(0, 3); // {0, 1, 2}
        Debug.print(d2, "Elemeni domene d2:");

        IDomain d3 = Domain.combine(d1, d2);
        Debug.print(d3, "Elementi domene d3:");

        System.out.println(d3.elementForIndex(0));
        System.out.println(d3.elementForIndex(5));
        System.out.println(d3.elementForIndex(14));
        System.out.println(d3.indexOfElement(DomainElement.of(4,1)));

//        IDomain d4 = Domain.combine(d2, d1);
//        Debug.print(d4, "Elementi domene d4:");
//        System.out.println(d4.indexOfElement(DomainElement.of(2, 4)));
    }
}
