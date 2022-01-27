public class Field {
    private final int[] pos = new int[2];
    private boolean isOccupied = false;
    private boolean isShip = false;
    private boolean isWater = true;
    private boolean isHit = false;
    private boolean isMiss = false;
    private boolean isSunk = false;

    public Field(int x, int y) {
        pos[0] = x;
        pos[1] = y;
    }

    // overloaded Constructor for loading session
    public Field(
            int x, int y,
            boolean isOccupied, boolean isShip, boolean isWater,
            boolean isHit, boolean isMiss, boolean isSunk
    ) {
        this.pos[0] = x;
        this.pos[1] = y;
        this.isOccupied = isOccupied;
        this.isShip = isShip;
        this.isWater = isWater;
        this.isHit = isHit;
        this.isMiss = isMiss;
        this.isSunk = isSunk;
    }

    @Override
    public String toString() {
        return "" + this.pos[0] + " " + this.pos[1] + " " + this.isOccupied + " " + this.isShip + " " + this.isWater + " " + this.isHit + " " + this.isMiss + " " + this.isSunk;
    }

    public int[] getPos() {
        return pos;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public boolean isMiss() {
        return isMiss;
    }

    public void setMiss(boolean miss) {
        isMiss = miss;
    }

    public boolean isWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setShip(boolean ship) {
        isWater = !ship;
        isShip = ship;
    }
}
