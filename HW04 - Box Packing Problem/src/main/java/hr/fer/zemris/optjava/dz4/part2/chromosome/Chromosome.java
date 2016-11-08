package hr.fer.zemris.optjava.dz4.part2.chromosome;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Chromosome {
    private final int boxHeight;
    private List<Box> boxes;

    public Chromosome(int boxHeight, List<Stick> sticks){
        this.boxHeight = boxHeight;
        boxes = new LinkedList<>();

        addSticks(sticks);
    }

    public Chromosome(List<Box> boxes){
        this.boxes = boxes;
        this.boxHeight = boxes.get(0).getHeight();
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void addSticks(List<Stick> sticks){
        for (Stick stick : sticks){
            boolean stickAdded = false;
            for (Box box : boxes){
                if (box.getCapacity() >= stick.getHeight()){
                    box.addStick(stick);
                    stickAdded = true;
                    break;
                }
            }

            if (!stickAdded){
                Box box = new Box(boxHeight);
                box.addStick(stick);
                boxes.add(box);
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner str = new StringJoiner(" | ", "[", "]");

        for (Box box : boxes){
            str.add(box.toString());
        }

        return str.toString();
    }
}
