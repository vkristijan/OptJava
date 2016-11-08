package hr.fer.zemris.optjava.dz4.part2.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Box {
    private int height;
    private int capacity;
    private List<Stick> sticks;

    public Box(int height){
        this.height = height;
        this.sticks = new ArrayList<>();

        capacity = height;
    }

    public Box(int height, List<Stick> sticks){
        this.height = height;
        this.sticks = sticks;

        calculateCapacity();
    }

    private void calculateCapacity() {
        capacity = height;
        for (Stick stick : sticks){
            capacity -= stick.getHeight();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getCapacity(){
        return capacity;
    }

    public List<Stick> getSticks() {
        return sticks;
    }

    public boolean addStick(Stick stick){
        if (stick.getHeight() <= capacity){
            sticks.add(stick);
            capacity -= stick.getHeight();
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringJoiner str = new StringJoiner(", ");

        for (Stick stick : sticks){
            str.add(stick.toString());
        }

        return str.toString();
    }
}
