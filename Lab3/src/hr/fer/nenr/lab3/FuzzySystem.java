package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.IFuzzySet;

import java.util.List;

public interface FuzzySystem {
    Rule getRule(int idx);
    void addRule(List<IFuzzySet> premise, IFuzzySet conclusion);
    IFuzzySet concludeSet(int[] values);
    int conclude(int[] values);
}
