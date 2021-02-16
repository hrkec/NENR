package hr.fer.nenr.lab1;

public class Operations {
    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction unaryFunction){
        MutableFuzzySet result = new MutableFuzzySet(set.getDomain());
        for(DomainElement element : set.getDomain()){
            double value = set.getValueAt(element);
            result.set(element, unaryFunction.valueAt(value));
        }
        return result;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction binaryFunction){
        MutableFuzzySet result = new MutableFuzzySet(set1.getDomain());
        for(DomainElement element : set1.getDomain()){
            double a = set1.getValueAt(element);
            double b = set2.getValueAt(element);
            result.set(element, binaryFunction.valueAt(a, b));
        }
        return result;
    }

    public static IUnaryFunction zadehNot(){
        return new IUnaryFunction() {
            @Override
            public double valueAt(double x) {
                return 1 - x;
            }
        };
    }

    public static IBinaryFunction zadehAnd(){
        return new IBinaryFunction() {
            @Override
            public double valueAt(double a, double b) {
                return Math.min(a, b);
            }
        };
    }

    public static IBinaryFunction zadehOr(){
        return new IBinaryFunction() {
            @Override
            public double valueAt(double a, double b) {
                return Math.max(a, b);
            }
        };
    }

    public static IBinaryFunction hamacherTNorm(double v){
        return new IBinaryFunction() {
            @Override
            public double valueAt(double a, double b) {
                return (a * b) / (v + (1 - v) * (a + b - a * b));
            }
        };
    }

    public static IBinaryFunction hamacherSNorm(double v){
        return new IBinaryFunction() {
            @Override
            public double valueAt(double a, double b) {
                return (a + b - (2 - v) * a * b) / (1 - (1 - v) * a * b);
            }
        };
    }

    public static IBinaryFunction product(){
        return new IBinaryFunction() {
            @Override
            public double valueAt(double a, double b) {
                return a * b;
            }
        };
    }
}
