package a.s.e.h.f.game_theory_project;

import java.text.DecimalFormat;
import java.util.ArrayList;
import activity.ActivityFillData;


public class Possibility {

    static int                row;
    static int                col;
    static int                colRemovedCount;
    static int                rowRemovedCount;
    static String[][]         matrix;                             // Copy of all_matrix
    static String[][]         finalMatrix;                        // optimized Matrix
    static float[][]          coefficientsMatrix;                 // for cramer
    static float[][]          constantsMatrix;                    // for cramer
    static float[][]          temp;                               // for cramer
    static ArrayList<Integer> ROWINDEX = new ArrayList<Integer>();
    static ArrayList<Integer> COLINDEX = new ArrayList<Integer>();
    static float              determinant;
    static String             rowCleared;
    static String             colCleared;
    static float              sumOfProb;
    static String             finalText;
    static boolean            rowClear;
    static boolean            colClear;


    public static String Execution() {
        resetExecution();

        while (true) {
            rowClear = false;
            colClear = false;
            rowRemove();
            colRemove();
            if ( !rowClear && !colClear) {
                break;
            }

        }
        setNewRow_Col();

        if (row == col) {
            finalText = "";
            fillFinalMatrix();
            readyCoefficientsMatrix();
            CalculationProb(1);
            if (finalText.equals("Problem can not be solved with probability")) {
                return finalText;
            }
            changeFinalMatrix();
            setNewRow_Col();
            readyCoefficientsMatrix();
            CalculationProb(0);

        }

        return finalText;
    }


    // ---------------------------------------------------------------------

    private static void resetExecution() {
        rowClear = false;
        colClear = false;
        rowCleared = "";
        colCleared = "";
        finalText = "Problem can not be solved with probability";
        row = 0;
        col = 0;
        colRemovedCount = 0;
        rowRemovedCount = 0;
        sumOfProb = 0;
        ROWINDEX.clear();
        COLINDEX.clear();
        row = ActivityFillData.players.get(ActivityFillData.players.size() - 2).actions.size();
        col = ActivityFillData.players.get(ActivityFillData.players.size() - 1).actions.size();
        matrix = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String s = ActivityFillData.all_matrix.get(0).matrix[i][j];

                if (s.contains(",r")) {
                    s = s.replace(",r", "");
                }
                if (s.contains(",c")) {
                    s = s.replace(",c", "");
                }
                if (s.contains(",t")) {
                    s = s.replace(",t", "");
                }
                if (s.contains(",|")) {
                    s = s.replace(",|", "");
                }
                if (s.contains("|")) {
                    s = s.replace("|", "");
                }
                if (s.contains(",|")) {
                    s = s.replace(",s", "");
                }

                ActivityFillData.all_matrix.get(0).matrix[i][j] = s;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String s = ActivityFillData.all_matrix.get(0).matrix[i][j];
                matrix[i][j] = s;
            }
        }

    }


    // ---------------------------------------------------------------------

    private static void rowRemove() {
        boolean first = true;
        float max = 0;
        ArrayList<Integer> rowIndex = new ArrayList<Integer>();
        for (int j = 0; j < col; j++) {
            first = true;

            for (int i = 0; i < row; i++) {
                if (first) {
                    if ( !matrix[i][j].equals("")) {
                        max = Value(0, matrix[i][j]);
                        rowIndex.add(i);
                        first = false;
                    }
                } else {
                    if ( !matrix[i][j].equals("") && Value(0, matrix[i][j]) > max) {
                        max = Value(0, matrix[i][j]);
                        rowIndex.clear();
                        rowIndex.add(i);
                    } else if ( !matrix[i][j].equals("") && Value(0, matrix[i][j]) == max) {
                        rowIndex.add(i);
                    }
                }
            }

            for (int temp = 0; temp < rowIndex.size(); temp++) {
                ROWINDEX.add(rowIndex.get(temp));

            }
            rowIndex.clear();
        }

        for (int i = 0; i < row; i++) {
            int index = -1;
            for (int k = 0; k < ROWINDEX.size(); k++) {
                if (ROWINDEX.get(k) == i) {
                    index = i;
                    break;
                }
            }
            if (index == -1 && !rowCleared.contains("-" + i + "-")) {
                clear("r", i);
            }
        }

    }


    // --------------------------------------------------------------------- 

    private static void colRemove() {
        boolean first = true;
        float max = 0;
        ArrayList<Integer> colIndex = new ArrayList<Integer>();
        for (int i = 0; i < row; i++) {
            first = true;
            for (int j = 0; j < col; j++) {
                if (first) {
                    if ( !matrix[i][j].equals("")) {
                        max = Value(1, matrix[i][j]);
                        colIndex.add(j);
                        first = false;
                    }
                } else {
                    if ( !matrix[i][j].equals("") && Value(1, matrix[i][j]) > max) {
                        max = Value(1, matrix[i][j]);
                        colIndex.clear();
                        colIndex.add(j);
                    } else if ( !matrix[i][j].equals("") && Value(1, matrix[i][j]) == max) {
                        colIndex.add(j);
                    }
                }
            }

            for (int temp = 0; temp < colIndex.size(); temp++) {
                COLINDEX.add(colIndex.get(temp));
            }
            colIndex.clear();
        }

        for (int i = 0; i < col; i++) {
            int index = -1;
            for (int k = 0; k < COLINDEX.size(); k++) {
                if (COLINDEX.get(k) == i) {
                    index = i;
                }

            }
            if (index == -1 && !colCleared.contains("-" + i + "-")) {
                clear("c", i);
            }
        }

    }


    // --------------------------------------------------------------------- 

    private static void setNewRow_Col() {
        row = row - rowRemovedCount;
        col = col - colRemovedCount;

    }


    // --------------------------------------------------------------------- 

    private static void fillFinalMatrix() {
        finalMatrix = new String[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                finalMatrix[i][j] = "";
            }
        }
        for (int i = 0; i < (row + rowRemovedCount); i++) {
            for (int j = 0; j < (col + colRemovedCount); j++) {
                if ( !matrix[i][j].equals("")) {
                    Enter(matrix[i][j]);
                }

            }

        }

    }


    // ---------------------------------------------------------------------    

    private static void readyCoefficientsMatrix() {
        int size = row - 1;
        coefficientsMatrix = new float[size][size];
        constantsMatrix = new float[size][1];
        temp = new float[1][size + 1];
        for (int i = 1; i < size + 1; i++) {
            float x1;
            float y1;
            float x2;
            float y2;
            for (int z = 0; z < size; z++) {
                x1 = Value(0, finalMatrix[0][z]);
                x2 = Value(0, finalMatrix[0][size]);

                y1 = Value(0, finalMatrix[i][z]);
                y2 = Value(0, finalMatrix[i][size]);

                temp[0][z] = (x1 - x2) - (y1 - y2);

            }
            x1 = Value(0, finalMatrix[0][col - 1]);
            y1 = Value(0, finalMatrix[i][col - 1]);
            temp[0][size] = y1 - x1;
            for (int index = 0; index < size; index++) {
                coefficientsMatrix[i - 1][index] = temp[0][index];
            }
            constantsMatrix[i - 1][0] = temp[0][size];

        }

    }


    // ---------------------------------------------------------------------    

    private static void CalculationProb(int player) {

        determinant = determinant(coefficientsMatrix, (row - 1));
        if (determinant == 0) {
            finalText = "Problem can not be solved with probability";
            return;
        }
        row = ActivityFillData.players.get(ActivityFillData.players.size() - 2).actions.size();
        col = ActivityFillData.players.get(ActivityFillData.players.size() - 1).actions.size();
        finalText += ActivityFillData.players.get(player).name + " select :" + "\n";
        int oldStep = FindNextAction( -1, player);
        int newStep = FindNextAction( -1, player);

        for (int c = 0; c < newStep; c++) {
            finalText += ActivityFillData.players.get(player).actions.get(c);
            finalText += " : with probably 0";
            finalText += "\n";
        }

        float temp;
        for (int c = 0; c < col - colRemovedCount - 1; c++) {
            if (c == 0) {

                for (int r = 0; r < (row - rowRemovedCount - 1); r++) {
                    temp = constantsMatrix[r][0];
                    constantsMatrix[r][0] = coefficientsMatrix[r][c];
                    coefficientsMatrix[r][c] = temp;
                }
            } else {

                for (int r = 0; r < (row - rowRemovedCount - 1); r++) {
                    temp = coefficientsMatrix[r][c];
                    coefficientsMatrix[r][c] = coefficientsMatrix[r][c - 1];
                    coefficientsMatrix[r][c - 1] = constantsMatrix[r][0];
                    constantsMatrix[r][0] = temp;
                }

            }
            float d = determinant(coefficientsMatrix, (row - rowRemovedCount - 1));

            finalText += ActivityFillData.players.get(player).actions.get(newStep);
            finalText += " :  ";
            String s = round(d / determinant) + "";
            sumOfProb += (d / determinant);

            finalText = finalText + "with probably " + s;
            finalText += "\n";
            oldStep = newStep;
            newStep = FindNextAction(oldStep, player);
            for (int index = oldStep + 1; index < newStep; index++) {
                finalText += ActivityFillData.players.get(player).actions.get(index);
                finalText += " : with probably 0";
                finalText += "\n";
            }

        }

        finalText += ActivityFillData.players.get(player).actions.get(newStep);
        finalText += " :  ";
        String s = round(1 - sumOfProb) + "";

        finalText = finalText + "with probably " + s;
        finalText += "\n";

        for (int index = newStep + 1; index < ActivityFillData.players.get(player).actions.size(); index++) {
            finalText += ActivityFillData.players.get(player).actions.get(index);
            finalText += " : with probably 0";
            finalText += "\n";
        }
        sumOfProb = 0;

    }


    // ---------------------------------------------------------------------

    private static void changeFinalMatrix() {
        for (int i = 0; i < (row - rowRemovedCount); i++) {
            for (int j = 0; j < (col - colRemovedCount); j++) {
                if (i > j) {
                    String temp = finalMatrix[i][j];
                    finalMatrix[i][j] = finalMatrix[j][i];
                    finalMatrix[j][i] = temp;
                }

            }

        }

        for (int i = 0; i < (row - rowRemovedCount); i++) {
            for (int j = 0; j < (col - colRemovedCount); j++) {
                float v1 = Value(0, finalMatrix[i][j]);
                float v2 = Value(1, finalMatrix[i][j]);
                finalMatrix[i][j] = "(" + v2 + "," + v1 + ")";
            }

        }
    }


    // --------------------------------------------------------------------- 

    private static float determinant(float[][] matrix, int size) {
        float result = 0;

        if (size == 0) {
            result = Value(0, finalMatrix[0][0]);
        }

        if (size == 1) {
            return (matrix[0][0]);

        }
        else if (size == 2) {
            return (((matrix[0][0]) * (matrix[1][1])) - ((matrix[0][1]) * (matrix[1][0])));

        }
        boolean even = false;
        for (int i = 0; i < (row - 1); i++) {
            float factor = matrix[i][0];
            if ( !even) {
                float m[][] = remove(matrix, size, i);
                result = result + (factor * determinant(m, size - 1));
                even = true;
            } else {
                float m[][] = remove(matrix, size, i);
                result = result - (factor * determinant(m, size - 1));
                even = false;

            }

        }

        return result;

    }


    // --------------------------------------------------

    private static float Value(int p, String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        String[] temp = s.split(",");
        float value = Float.parseFloat(temp[p]);
        return value;
    }


    private static void Enter(String s) {
        boolean done = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (finalMatrix[i][j].equals("") && !done) {
                    finalMatrix[i][j] = s;
                    done = true;
                    return;
                }
            }
        }
    }


    //------------------------------------------------------------------   

    private static float[][] remove(float[][] matrix, int size, int rowIndex) {
        float m[][] = new float[size - 1][size - 1];
        int row = 0;
        int col = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 1; c < size; c++) {
                if (r != rowIndex) {
                    m[row][col] = matrix[r][c];
                    col++;
                    if (col == size - 1) {
                        row++;
                        col = 0;
                    }
                }
            }

        }

        return m;

    }


    private static int FindNextAction(int index, int player) {
        String cleared;
        int rowOrCol;
        if (player == 0) {
            cleared = rowCleared;
            rowOrCol = row;
        } else {
            cleared = colCleared;
            rowOrCol = col;
        }

        for (int c = (index + 1); c < rowOrCol; c++) {
            if ( !cleared.contains("-" + c + "-")) {
                return c;
            }

        }
        return -1;

    }


    private static void clear(String s, int index) {
        if (s.equals("r")) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (i == index) {
                        matrix[i][j] = "";
                    }
                }
            }
            rowRemovedCount++;
            rowCleared = rowCleared + "-" + index + "-";
            rowClear = true;
        } else if (s.equals("c")) {

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (j == index) {
                        matrix[i][j] = "";
                    }
                }
            }
            colRemovedCount++;
            colCleared = colCleared + "-" + index + "-";
            colClear = true;
        }

    }


    private static float round(float f) {

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        float result = Float.valueOf(twoDForm.format(f));
        return result;
    }
}
