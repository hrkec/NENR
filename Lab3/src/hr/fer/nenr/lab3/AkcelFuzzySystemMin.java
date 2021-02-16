package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.IFuzzySet;
import hr.fer.nenr.lab1.MutableFuzzySet;
import hr.fer.nenr.lab1.Operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AkcelFuzzySystemMin implements FuzzySystem {
    private final Defuzzifier def;
    private final List<Rule> rules;

    public AkcelFuzzySystemMin(Defuzzifier def) {
        this.def = def;
        this.rules = new ArrayList<>();

        this.addRule(Arrays.asList(null, null, null, null, SimulatorUtil.GOING_TOO_SLOW, SimulatorUtil.RIGHT_WAY), SimulatorUtil.SPEED_UP);
        this.addRule(Arrays.asList(null, null, null, null, SimulatorUtil.GOING_TOO_FAST, null), SimulatorUtil.SLOW_DOWN);
        this.addRule(Arrays.asList(null, null, null, null, null, SimulatorUtil.WRONG_WAY), SimulatorUtil.SLOW_DOWN);
//        this.addRule(Arrays.asList(SimulatorUtil.CLOSE_TO_SHORE, SimulatorUtil.CLOSE_TO_SHORE, SimulatorUtil.CLOSE_TO_SHORE, SimulatorUtil.CLOSE_TO_SHORE, null, null), SimulatorUtil.SLOW_DOWN);
//        this.addRule(Arrays.asList(SimulatorUtil.CLOSE_TO_SHORE, null, null, null, null, null), SimulatorUtil.SLOW_DOWN);
//        this.addRule(Arrays.asList(null, SimulatorUtil.CLOSE_TO_SHORE, null, null, null, null), SimulatorUtil.SLOW_DOWN);
//        this.addRule(Arrays.asList(null, null, SimulatorUtil.CLOSE_TO_SHORE, null, null, null), SimulatorUtil.SLOW_DOWN);
//        this.addRule(Arrays.asList(null, null, null, SimulatorUtil.CLOSE_TO_SHORE, null, null), SimulatorUtil.SLOW_DOWN);

    }

    @Override
    public IFuzzySet concludeSet(int[] values) {
        List<IFuzzySet> appliedRules = new ArrayList<>();
        for (Rule rule : rules) {
            IFuzzySet appliedSet = rule.apply(values);
            appliedRules.add(appliedSet);
        }
        IFuzzySet result = new MutableFuzzySet(appliedRules.get(0).getDomain());
        for (IFuzzySet fuzzySet : appliedRules) {
            result = Operations.binaryOperation(result, fuzzySet, Operations.zadehOr());
        }
        return result;
    }

    @Override
    public int conclude(int[] values) {
        IFuzzySet result = concludeSet(values);
        return def.defuzzify(result);
    }

    @Override
    public Rule getRule(int idx) {
        return rules.get(idx);
    }

    @Override
    public void addRule(List<IFuzzySet> premise, IFuzzySet conclusion) {
        rules.add(new Rule(premise, conclusion));
    }
}
