package hr.fer.nenr.lab1;

import java.util.Arrays;
import java.util.List;

public class DomainElement {
    private final int[] values;

    public DomainElement(int... values) {
        this.values = values;
    }

    public DomainElement(List<Integer> values) {
        this.values = values.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getNumberOfComponents(){
        return this.values.length;
    }

    public int getComponentValue(int idx){
        return this.values[idx];
    }

    @Override
    public String toString() {
        if (this.getNumberOfComponents() == 1){
            return Integer.toString(this.values[0]);
        } else {
            StringBuilder sb = new StringBuilder("(");
            for(int value : this.values){
                sb.append(value).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append(")");
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()){
            return false;
        }
        DomainElement other = (DomainElement) o;
        for (int i = 0; i < this.values.length; i++){
            if (this.values[i] != other.values[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.values);
    }

    public static DomainElement of(int[] values){
        return new DomainElement(values);
    }

    public static DomainElement of(int value){
        return of(new int[]{value});
    }

    public static DomainElement of(int value1, int... values){
        int[] vals = new int[values.length + 1];
        vals[0] = value1;
        System.arraycopy(values, 0, vals, 1, values.length);
        return of(vals);
    }

    public int[] getValues() {
        return values;
    }
}

