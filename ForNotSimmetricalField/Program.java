package ForNotSimmetricalField;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static int WIN_COUNT ;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;


    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkState(DOT_HUMAN, "Вы победили!"))
                    break;
                ;
                aiTurn();
                printField();
                if (checkState(DOT_AI, "Победил компьютер!"))
                    break;
                ;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация объектов игры
     */
    static void initialize() {
        System.out.println("Введите размерность игрового поля по оси X и по оси Y \n(через пробел (не менее 3-х): ");
        fieldSizeX = scanner.nextInt();
        fieldSizeY = scanner.nextInt();

        if (fieldSizeX < 3) {
            fieldSizeX = 3;
            System.out.println("Значение размера поля по оси X установлено - 3");
        } else {
            System.out.println("Значение размера поля по оси X установлено - "+ fieldSizeX);
        }
        if(fieldSizeY < 3) {
            fieldSizeX = 3;
            System.out.println("Значение размера поля по оси установлено - 3");
        } else {
            System.out.println("Значение размера поля по оси X установлено - "+ fieldSizeY);
        }

        System.out.println("Введите длину непрерывной серии,\nкоторая будет считаться выигрышной: ");
        WIN_COUNT = scanner.nextInt();
        if (WIN_COUNT > fieldSizeX || WIN_COUNT > fieldSizeY) {
            WIN_COUNT = 3;
            System.out.println("Длина серии установлена - 3");
        } else {
            System.out.println("Длина серии установлена - "+ WIN_COUNT);
        }

        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("-" + (x + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты хода X и Y\n(от 1 до 3 через пробел");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход игрока (Компьютера)
     */
    static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка на ничью
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки победы
     *
     * @param dot фишка игрока
     * @return
     */
    static boolean checkWin(char dot) {
        return  (checkDekart(dot) || checkDiagonal(dot));
    }

    /**
     * Проверка состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return1
     */
    static boolean checkState(char dot, String s){
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        else  if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

    /**
     * Метод проверки победы по декартовым направлениям
     * @param dot - значок игрока
     * @return
     */
    static boolean checkDekart(char dot){
        int countWin = 0;
        // Проверка победы по горизонтали
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == dot) {
                    countWin++;
                    if (countWin == WIN_COUNT) return true;
                } else {
                    countWin = 0;
                }
            }
        }
        //проверка победы по вертикали
        countWin = 0;
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[j][i] == dot) {
                    countWin++;
                    if (countWin == WIN_COUNT) return  true;
                } else {
                    countWin = 0;
                }
            }
        }
            return false;
    }

    /**
     * Метод проверки победы по диагональным направлениям
     * @param dot
     * @return
     */
    static boolean checkDiagonal(char dot) {
        //for (int i = 0; i < WIN_COUNT; i++) {                                 //начинаем от крайнего левого столбца

        int countWin1;
        int countWin2;
        // если рядов больше чем столбцов
        if (fieldSizeX >= fieldSizeY) {
            // Первая диагональ снизу вправо вверх / Просматриваем полные ряды
            int row;
            for (int i = fieldSizeX; i >= WIN_COUNT; i--) {
                if (i >= fieldSizeY) {
                    row = i - 1;
                    countWin1 = 0;
                    for (int col = 0; col < fieldSizeY; col++) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {countWin1 = 0;}
                        row--;
                    }
                    //  Вторая диагональ снизу влево вверх \ полные ряды
                    row = i - 1;
                    countWin1 = 0;
                    for (int col = fieldSizeY - 1; col >= 0; col--) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else countWin1 = 0;
                        row--;
                    }
                // Просматриваем верхушки  
                } else {
                    // Первая верхушка
                    row = i - 1;
                    countWin1 = 0;
                    countWin2 = 0;
                    for (int col = 0; col < i; col++) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {countWin1 = 0;}
                        // Зеркальная для первой
                        if (field[fieldSizeX-row -1][(fieldSizeY - col -1)] == dot) { 
                            countWin2++;
                            if (countWin2 == WIN_COUNT) return true;
                        } else {countWin2 = 0;}
                        row--;
                    }
                    // Вторая
                    row = i - 1;
                    countWin1 = 0;
                    countWin2 = 0;
                    for (int col =  fieldSizeY-1; col >= fieldSizeY-i; col--) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {countWin1 = 0;}
                        // Зеркальная для второй
                        if (field[fieldSizeX-row-1][fieldSizeY-col-1] == dot) {  
                            countWin2++;
                            if (countWin2 == WIN_COUNT) return true;
                        } else {countWin2 = 0;}
                        row--;
                    }
                }
            }
            // если рядов меньше чем столбцов
        } else {
            // Первая диагональ (полные ряды)
            int col;
            for (int i = 0; i <= fieldSizeY - WIN_COUNT; i++) {
                if (i <= fieldSizeY - fieldSizeX) {
                    col = i;
                    countWin1 = 0;
                    for (int row = 0; row < fieldSizeX; row++) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {
                            countWin1 = 0;
                        }
                        col++;
                    }
                    // Вторая диагональ (полные рядв)
                    col=i;
                    countWin1 = 0;

                    for (int row = fieldSizeX - 1; row >= 0; row--) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {
                            countWin1 = 0;
                        }
                        col++;
                    }

                } else {
                    // Верхушки
                    col = i;
                    countWin1 = 0;
                    countWin2 = 0;
                    for (int row = 0; row < fieldSizeY - i; row++) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {
                            countWin1 = 0;
                        }
                        if (field[(fieldSizeX - row -1)][fieldSizeY-col -1] == dot) { // Проверка зеркальной законцовки
                            countWin2++;
                            if (countWin2 == WIN_COUNT) return true;
                        } else {countWin2 = 0;}
                    col++;
                    }
                    col = i;
                    countWin1 = 0;
                    countWin2 = 0;
                    for (int row =  fieldSizeX-1; row >= fieldSizeX-(fieldSizeY-i); row--) {
                        if (field[row][col] == dot) {
                            countWin1++;
                            if (countWin1 == WIN_COUNT) return true;
                        } else {countWin1 = 0;}
                        if (field[fieldSizeX-row-1][fieldSizeY-col-1] == dot) {     // Проверка зеркальной законцовки
                            countWin2++;
                            if (countWin2 == WIN_COUNT) return true;
                        } else {countWin2 = 0;}

                        col++;
                    }
                }
            }
        }
        return false;
    }
}