public class Ship {

    private static final int[] amounts = new int[4];
    private int size;
    private int[] startPoint;
    private final String direction;
    private int hitCounter;

    public Ship(int size, int[] startPoint, String direction) {
        this.size = size;
        this.startPoint = startPoint;
        this.direction = direction;
        hitCounter = size;
    }

    public static void calcAmount(int size) {
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = 0;
        }
        int temp;
        int rest = ((size * size) * 3) / 10;

        do {
            if (rest < 30) {
                if (rest > 13) {
                    amounts[0]++;
                    amounts[1]++;
                    amounts[2]++;
                    amounts[3]++;

                    rest = rest - 14;
                } else if (rest > 8) {
                    amounts[0]++;
                    amounts[1]++;
                    amounts[2]++;
                    rest = rest - 9;
                } else if (rest > 4) {
                    amounts[0]++;
                    amounts[1]++;
                    rest = rest - 5;
                } else {
                    amounts[0]++;
                    rest = rest - 2;
                }
            } else {
                temp = rest / 30;
                rest = rest - temp * 30;
                amounts[0] = 4 * temp;
                amounts[1] = 3 * temp;
                amounts[2] = 2 * temp;
                amounts[3] = temp;
            }
        } while (rest != 0 && rest != 1);
    }

    public static void lowerAmounts(int x) {
        amounts[x]--;
    }

    public static int[] getAmounts() {
        return amounts;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void reduceHitCounter() {
        hitCounter--;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int[] startPoint) {
        this.startPoint = startPoint;
    }

    public String getDirection() {
        return direction;
    }
}
