package day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Star {
    int posx;
    int posy;
    List<Integer> adjacentNumbers;

    public Star(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
        adjacentNumbers = new ArrayList<>();
    }

    public void addNumber(int number) {
        adjacentNumbers.add(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return posx == star.posx && posy == star.posy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posx, posy);
    }
}
