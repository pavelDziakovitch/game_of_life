import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);

        boolean[][] gamefield = new boolean[20][40];
        boolean[][] tempGamefield = new boolean[gamefield.length][gamefield[0].length];
        int genNumber = 1;

        fillArrays(gamefield, tempGamefield);
        //OUTPUT BASE-LAYOUT
        printArray(gamefield,genNumber);

        //repeat as long as user has not types "AUS"
        while(!userInput.nextLine().equals("AUS")) {
            genNumber++;
            checkAndChangeArray(gamefield, tempGamefield);
            printArray(gamefield,genNumber);
        }
        userInput.close();
    }

    private static void fillArrays(boolean[][] gamefield, boolean[][] tempGamefield) {

        Random randGen = new Random();

        //RANGE = 7 x 7, where true can spawn, the leftovers are false
        int startX = gamefield.length/2-4;
        int endX = gamefield.length/2+3;
        int startY = gamefield[0].length/2-4;
        int endY = gamefield[0].length/2+3;

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                gamefield[i][j] = randGen.nextBoolean();
                tempGamefield[i][j] = randGen.nextBoolean();
            }
        }
    }

    private static void printArray(boolean[][] gamefield, int genNumber) {

        System.out.println("GAME OF LIFE\nGeneration : " + genNumber);
        for (int x = 0; x < gamefield.length; x++) {
            for (int y = 0; y < gamefield[0].length; y++) {
                if (gamefield[x][y]) {
                    System.out.printf("%2s ", "#");
                } else {
                    System.out.printf("%2s ", "-");
                }
            }
            System.out.println();
        }
    }

    private static void checkAndChangeArray(boolean[][] gamefield, boolean[][] tempGamefield) {

        //Checking if alive or dead, how many neighbours there are and makes changes to temp array accordingly
        for (int x = 0; x < gamefield.length; x++) {
            for (int y = 0; y < gamefield[0].length; y++) {
                int neighbours = neighboursCounter(gamefield, x, y);
                if (!gamefield[x][y]) {
                    if (neighbours == 3) {
                        tempGamefield[x][y] = true;
                    }
                } else {
                    if (neighbours < 2) {
                        tempGamefield[x][y] = false;
                    }

                    if (neighbours > 3) {
                        tempGamefield[x][y] = false;
                    }
                }
            }
        }
        //Overwritte old Layout with new Layout
        for (int x = 0; x < gamefield.length; x++) {
           for (int y = 0; y < gamefield[0].length; y++) {
                gamefield[x][y] = tempGamefield[x][y];
            }
        }
    }

    private static int neighboursCounter(boolean[][] gamefield, int x, int y) {

        int livingNeightborCount = 0;

        for (int aroundX = x - 1; aroundX <= x + 1; aroundX++) {
            for (int aroundY = y - 1; aroundY <= y + 1; aroundY++) {
                if(aroundX >= 0 && aroundX < gamefield.length && aroundY >= 0 && aroundY < gamefield[0].length){
                    if (gamefield[aroundX][aroundY] && (aroundX != x || aroundY != y)) {
                        livingNeightborCount++;
                    }
                }
            }
        }
        return livingNeightborCount;
    }
}

//https://pretagteam.com/question/detect-a-key-press-in-console