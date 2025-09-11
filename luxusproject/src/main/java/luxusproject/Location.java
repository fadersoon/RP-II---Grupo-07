package luxusproject;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        // Classe original com condições invertidas, condições deveriam ser "<" em vez de ">" para declarar como negativo
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Negative x-coordinate: " + x);
        }
        if (y < 0) {
            throw new IllegalArgumentException(
                    "Negative y-coordinate: " + y);
        }
        this.x = x;
        this.y = y;
    }

    public Location nextLocation(Location destination) {
        int destX = destination.getX();
        int destY = destination.getY();
        int offsetX = x < destX ? 1 : x > destX ? -1 : 0;
        int offsetY = y < destY ? 1 : y > destY ? -1 : 0;
        if (offsetX != 0 || offsetY != 0) {
            return new Location(x + offsetX, y + offsetY);
        } else {
            return destination;
        }
    }

    public int distance(Location destination) {
        int xDist = Math.abs(destination.getX() - x);
        int yDist = Math.abs(destination.getY() - y);
        return Math.max(xDist, yDist);
    }

    public boolean equals(Object other) {
        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX()
                    && y == otherLocation.getY();
        } else {
            return false;
        }
    }

    public String toString() {
        return "location " + x + "," + y;
    }

    public int hashCode() {
        return (y << 16) + x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
