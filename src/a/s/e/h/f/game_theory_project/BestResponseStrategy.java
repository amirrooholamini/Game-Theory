package a.s.e.h.f.game_theory_project;

import java.util.ArrayList;
import activity.ActivityFillData;


public class BestResponseStrategy {

    static int     table    = 0;
    static int     row      = 0;
    static int     col      = 0;
    static boolean findNash = false;


    public static String Execution() {

        findNash = false;

        if (Global.column == 3) {
            table = ActivityFillData.players.get(ActivityFillData.players.size() - 3).actions.size();
        } else {
            table = 1;
        }
        row = ActivityFillData.players.get(ActivityFillData.players.size() - 2).actions.size();
        col = ActivityFillData.players.get(ActivityFillData.players.size() - 1).actions.size();

        for (int t = 0; t < table; t++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    String s = ActivityFillData.all_matrix.get(t).matrix[i][j];
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
                    ActivityFillData.all_matrix.get(t).matrix[i][j] = s;
                }
            }
        }

        rowCompare();
        colCompare();
        if (Global.column == 3) {
            tableCompare();
        }
        String ne = checkNE();
        // Log.i("LOG", "ne = " + ne);

        for (int t = 0; t < table; t++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    String s = ActivityFillData.all_matrix.get(t).matrix[i][j];
                    if (s.contains(",|")) {
                        s = s.replace(",|", "");
                    }
                    ActivityFillData.all_matrix.get(t).matrix[i][j] = s;
                }
            }
        }
        if (findNash) {
            return ne;
        } else {
            return "There is no NE";
        }
    }


    private static void rowCompare() {
        float max = 0;
        boolean first = true;
        ArrayList<Integer> rowIndex = new ArrayList<Integer>();
        for (int t = 0; t < table; t++) {
            for (int j = 0; j < col; j++) {
                first = true;
                for (int i = 0; i < row; i++) {
                    if (first) {
                        max = Value(0, HS(t, i, j));
                        rowIndex.clear();
                        rowIndex.add(i);
                        first = false;
                    }
                    else if (Value(0, HS(t, i, j)) > max) {
                        //  Log.i("LOG", ">");
                        max = Value(0, HS(t, i, j));
                        rowIndex.clear();
                        rowIndex.add(i);
                    } else if (Value(0, HS(t, i, j)) == max) {
                        // Log.i("LOG", "==");
                        rowIndex.add(i);
                    } //else {
                      //  Log.i("LOG", "<");
                      //}

                }
                for (int index = 0; index < rowIndex.size(); index++) {
                    // Log.i("LOG", ActivityFillData.all_matrix.get(t).matrix[rowIndex.get(index)][j]);
                    String value = ActivityFillData.all_matrix.get(t).matrix[rowIndex.get(index)][j];
                    ActivityFillData.all_matrix.get(t).matrix[rowIndex.get(index)][j] = value + ",|";
                }
                max = 0;
            }
        }

    }


    private static void colCompare() {
        float max = 0;
        boolean first = true;
        ArrayList<Integer> colIndex = new ArrayList<Integer>();
        for (int t = 0; t < table; t++) {
            for (int i = 0; i < row; i++) {
                first = true;
                for (int j = 0; j < col; j++) {
                    if (first) {
                        max = Value(1, HS(t, i, j));
                        colIndex.clear();
                        colIndex.add(j);
                        first = false;
                    }
                    else if (Value(1, HS(t, i, j)) > max) {
                        //  Log.i("LOG", ">");
                        max = Value(1, HS(t, i, j));
                        colIndex.clear();
                        colIndex.add(j);
                    } else if (Value(1, HS(t, i, j)) == max) {
                        // Log.i("LOG", "==");
                        colIndex.add(j);
                    } //else {
                      //  Log.i("LOG", "<");
                      //}

                }
                for (int index = 0; index < colIndex.size(); index++) {
                    //Log.i("LOG", ActivityFillData.all_matrix.get(t).matrix[i][colIndex.get(index)]);
                    String value = ActivityFillData.all_matrix.get(t).matrix[i][colIndex.get(index)];
                    ActivityFillData.all_matrix.get(t).matrix[i][colIndex.get(index)] = value + ",|";
                }
                max = 0;
            }
        }

    }


    private static void tableCompare() {
        float max = 0;
        boolean first = true;
        ArrayList<Integer> tableIndex = new ArrayList<Integer>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                first = true;
                for (int t = 0; t < table; t++) {
                    if (first) {
                        max = Value(2, HS(t, i, j));
                        tableIndex.clear();
                        tableIndex.add(t);
                        first = false;
                    }

                    else if (Value(2, HS(t, i, j)) > max) {
                        //  Log.i("LOG", ">");
                        max = Value(2, HS(t, i, j));
                        tableIndex.clear();
                        tableIndex.add(t);
                    } else if (Value(2, HS(t, i, j)) == max) {
                        // Log.i("LOG", "==");
                        tableIndex.add(t);
                    } //else {
                      //  Log.i("LOG", "<");
                      //}

                }
                for (int index = 0; index < tableIndex.size(); index++) {
                    // Log.i("LOG", ActivityFillData.all_matrix.get(tableIndex.get(index)).matrix[i][j]);
                    String value = ActivityFillData.all_matrix.get(tableIndex.get(index)).matrix[i][j];
                    ActivityFillData.all_matrix.get(tableIndex.get(index)).matrix[i][j] = value + ",|";
                }
                max = 0;
            }
        }

    }


    private static String checkNE() {
        String ne = "NE :" + "\n";
        for (int t = 0; t < table; t++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    String s = ActivityFillData.all_matrix.get(t).matrix[i][j];
                    String v0 = "";
                    String v1 = "";
                    String v2 = "";
                    if ((Global.column == 3 && s.contains(",|,|,|")) || (Global.column == 2 && s.contains(",|,|"))) {
                        findNash = true;
                        if (Global.column == 3) {
                            v0 = activity.ActivityFillData.players.get(0).actions.get(t);
                            v1 = activity.ActivityFillData.players.get(1).actions.get(i);
                            v2 = activity.ActivityFillData.players.get(2).actions.get(j);
                            v2 = "," + v2;
                        } else {
                            v0 = activity.ActivityFillData.players.get(0).actions.get(i);
                            v1 = activity.ActivityFillData.players.get(1).actions.get(j);
                        }

                        if (s.contains(",|,|,|")) {
                            s = s.replace(",|,|,|", "");
                        }

                        else if (s.contains(",|,|")) {
                            s = s.replace(",|,|", "");
                        }

                        if (s.contains(",s")) {
                            s.replace(",s", "");
                        }

                        if (s.contains(",r")) {
                            s.replace(",r", "");
                        }

                        if (s.contains(",c")) {
                            s.replace(",c", "");
                        }

                        if (s.contains(",t")) {
                            s.replace(",t", "");
                        }
                        ne = ne + "U(" + v0 + "," + v1 + v2 + ") = ";
                        ne = ne + s + "\n";
                    }

                }
            }
        }
        return ne;

    }


    private static String HS(int table, int row, int col) {
        return (ActivityFillData.all_matrix.get(table).matrix[row][col]);

    }


    private static float Value(int p, String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        String[] temp = s.split(",");
        float value = Float.parseFloat(temp[p]);
        return value;
    }
}
