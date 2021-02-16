package fer.nenr.zad7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Dataset {
    List<Double[]> inputs;
    List<Integer[]> outputs;

    public Dataset(String pathToFile) throws IOException {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(pathToFile));
        for(String line : lines){
            String[] nums = line.split("\\t");
            inputs.add(new Double[]{Double.parseDouble(nums[0]), Double.parseDouble(nums[1])});
            outputs.add(new Integer[]{Integer.parseInt(nums[2]), Integer.parseInt(nums[3]), Integer.parseInt(nums[4])});
        }
    }

    public Double[] inputAtIndex(int idx){
        return inputs.get(idx);
    }

    public Integer[] outputAtIndex(int idx){
        return outputs.get(idx);
    }

    public int size(){
        return inputs.size();
    }
}
