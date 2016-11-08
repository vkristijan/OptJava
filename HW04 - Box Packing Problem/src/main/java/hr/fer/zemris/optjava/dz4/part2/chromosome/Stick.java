package hr.fer.zemris.optjava.dz4.part2.chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Stick implements Comparable<Stick>{
    private static int COUNT = 0;

    private final int height;
    private final int id;

    public Stick(int height) {
        this.height = height;
        this.id = COUNT;

        COUNT++;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Stick o) {
        return Integer.compare(this.height, o.height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stick stick = (Stick) o;

        return id == stick.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(height);
    }
}
