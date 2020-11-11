package a.s.e.h.f.game_theory_project;

import activity.ActivityFillData;


public class DominantAndDominatedStrategy {

    static int     table        = 0;
    static int     row          = 0;
    static int     col          = 0;
    static String  ne;
    static String  remove;
    static boolean rowCleared   = false;
    static boolean colCleared   = false;
    static boolean tableCleared = false;
    static boolean findNash     = false;


    public static String Execution() {
        findNash = false;
        ne = "";
        remove = "";
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
        while (true) {
            rowCleared = false;
            colCleared = false;
            tableCleared = false;
            rowCompare();
            columnCompare();
            if (Global.column == 3) {
                tableCompare();
            }
            if (Global.column == 3 && !rowCleared && !colCleared && !tableCleared) {
                break;
            }
            if (Global.column == 2 && !rowCleared && !colCleared) {
                break;
            }
        }
        singleCompare();
        // Log.i("LOG", "NE = " + "NE = " + ne);
        //remove = remove.replaceAll("/", "");
        String response = "";
        if (findNash) {
            response = remove + "NE : " + "\n" + ne + "\n";
        } else {
            response = remove + "There is no NE" + "\n";
        }
        return response;
    }


    private static float Value(int p, String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        String[] temp = s.split(",");
        float value = Float.parseFloat(temp[p]);
        return value;
    }


    private static String HS(int table, int row, int col) {
        return (ActivityFillData.all_matrix.get(table).matrix[row][col]);

    }


    private static void clear(String s, int p) {
        if (s.equals("r")) {
            rowCleared = true;
            // Log.i("LOG", "The row " + p + " is dominated");
            String r = "The row " + (p + 1) + " is dominated";
            if ( !remove.contains(r)) {
                remove = remove + r + "\n";
            }
            for (int t = 0; t < table; t++) {
                for (int k = 0; k < col; k++) {
                    String value = ActivityFillData.all_matrix.get(t).matrix[p][k];
                    ActivityFillData.all_matrix.get(t).matrix[p][k] = value + ",r";
                }
            }

        } else if (s.equals("c")) {
            colCleared = true;
            //Log.i("LOG", "The column " + p + " is dominated");
            String r = "The column " + (p + 1) + " is dominated";
            if ( !remove.contains(r)) {
                remove = remove + r + "\n";
            }
            for (int t = 0; t < table; t++) {
                for (int i = 0; i < row; i++) {
                    String value = ActivityFillData.all_matrix.get(t).matrix[i][p];
                    ActivityFillData.all_matrix.get(t).matrix[i][p] = value + ",c";
                }
            }

        } else if (s.equals("t")) {
            tableCleared = true;
            //Log.i("LOG", "the table " + p + " is dominated");
            String r = "The table " + (p + 1) + " is dominated";
            if ( !remove.contains(r)) {
                remove = remove + r + "\n";
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    String value = ActivityFillData.all_matrix.get(p).matrix[i][j];
                    ActivityFillData.all_matrix.get(p).matrix[i][j] = value + ",t";
                }
            }

        }
    }


    private static void rowCompare() {
        boolean isFirstTime = true;
        boolean active = true;
        float s = 0;
        float d = 0;
        for (int i = 0; i < row - 1; i++) {
            for (int j = i + 1; j < row; j++) {
                s = 0.0f;
                d = 0.0f;
                active = true;
                if (HS(0, i, 0).contains("r")) {
                    active = false;
                }
                isFirstTime = true;
                for (int t = 0; t < table; t++) {
                    if (HS(0, j, 0).contains("r")) {
                        active = false;
                    }
                    for (int k = 0; k < col; k++) {
                        String v1 = HS(t, i, k);
                        String v2 = HS(t, j, k);
                        if (v1.contains("r") || v1.contains("c") || v1.contains("t") || v2.contains("r") || v2.contains("c") || v2.contains("t")) {
                            continue;
                        }
                        if (active) {
                            if (isFirstTime) {
                                s = Value(0, HS(t, i, k)) - Value(0, HS(t, j, k));
                                if (s != 0) {
                                    isFirstTime = false;
                                }
                            } else {
                                d = Value(0, HS(t, i, k)) - Value(0, HS(t, j, k));
                                if (d != 0) {
                                    if ((s * d) < 0) {
                                        active = false;
                                    }
                                }
                            }

                        }

                    }

                }
                if (active) {
                    if (s > 0) {
                        clear("r", j);
                    } else if (s < 0) {
                        clear("r", i);

                    }

                }

            }
        }

    }


    private static void columnCompare() {
        boolean isFirstTime = true;
        boolean active = true;
        float s = 0;
        float d = 0;
        for (int k = 0; k < col - 1; k++) {
            for (int j = k + 1; j < col; j++) {
                s = 0;
                d = 0;
                active = true;
                if (HS(0, 0, k).contains("c")) {
                    active = false;
                }
                isFirstTime = true;
                for (int t = 0; t < table; t++) {
                    if (HS(0, 0, j).contains("c")) {
                        active = false;
                    }
                    for (int i = 0; i < row; i++) {
                        String v1 = HS(t, i, k);
                        String v2 = HS(t, i, j);
                        if (v1.contains("r") || v1.contains("c") || v1.contains("t") || v2.contains("r") || v2.contains("c") || v2.contains("t")) {
                            continue;
                        }
                        if (active) {
                            if (isFirstTime) {
                                s = Value(1, HS(t, i, k)) - Value(1, HS(t, i, j));
                                if (s != 0) {
                                    isFirstTime = false;
                                }
                            } else {
                                d = Value(1, HS(t, i, k)) - Value(1, HS(t, i, j));
                                if (d != 0) {
                                    if ((s * d) < 0) {
                                        active = false;
                                    }
                                }
                            }

                        }

                    }

                }
                if (active) {
                    if (s > 0) {
                        clear("c", j);
                    } else if (s < 0) {
                        clear("c", k);

                    }

                }

            }
        }

    }


    private static void tableCompare() {
        // Log.i("LOG", "tableCompare");
        boolean isFirstTime = true;
        boolean active = true;
        float s = 0;
        float d = 0;
        for (int t = 0; t < table - 1; t++) {
            for (int j = t + 1; j < table; j++) {
                s = 0;
                d = 0;
                active = true;
                if (HS(t, 0, 0).contains("t")) {
                    active = false;
                }
                isFirstTime = true;
                for (int i = 0; i < row; i++) {
                    if (HS(j, 0, 0).contains("t")) {
                        //Log.i("LOG", "active = false");
                        active = false;
                    }
                    for (int k = 0; k < col; k++) {
                        // Log.i("LOG", "NEW");
                        String v1 = HS(t, i, k);
                        String v2 = HS(j, i, k);
                        if (v1.contains("r") || v1.contains("c") || v1.contains("t") || v2.contains("r") || v2.contains("c") || v2.contains("t")) {
                            // String st = "continue : t,i,k = " + t + "," + i + "," + k + ",";
                            // st += value(t, i, k) + "|" + value(j, i, k);
                            // Log.i("LOG", st);
                            continue;
                        }
                        if (active) {
                            // Log.i("LOG", "1");
                            if (isFirstTime) {
                                s = Value(2, HS(t, i, k)) - Value(2, HS(j, i, k));
                                //Log.i("LOG", "first time and t,i,k = " + t + "," + i + "," + k + "," + " and s = " + s);
                                //Log.i("LOG", "first time and j,i,k = " + j + "," + i + "," + k + ",");
                                if (s != 0) {
                                    // Log.i("LOG", "2");
                                    isFirstTime = false;
                                }
                            } else {
                                // Log.i("LOG", "3");
                                d = Value(2, HS(t, i, k)) - Value(2, HS(j, i, k));
                                if (d != 0) {
                                    if ((s * d) < 0) {
                                        active = false;
                                    }
                                }
                            }

                        }

                    }

                }
                if (active) {
                    if (s > 0) {
                        clear("t", j);
                    } else if (s < 0) {
                        clear("t", t);

                    }

                }

            }
        }

    }


    private static void singleCompare() {

        for (int i = 0; i < row; i++) {
            for (int t = 0; t < table; t++) {
                for (int j = 0; j < col; j++) {
                    if ( !HS(t, i, j).contains("r") && !HS(t, i, j).contains("c") && !HS(t, i, j).contains("t")) {
                        // Log.i("LOG", "t,i,j = " + t + "," + i + "," + j + ",");
                        float v0 = Value(0, HS(t, i, j));
                        float v1 = Value(1, HS(t, i, j));
                        // Log.i("LOG", "v0 = " + v0);
                        //Log.i("LOG", "v1 = " + v1);
                        float v2 = 0;
                        if (Global.column == 3) {
                            v2 = Value(2, HS(t, i, j));
                        }
                        boolean NE = true;
                        for (int r = 0; r < row; r++) {
                            if ( !HS(t, r, j).contains("r") && !HS(t, r, j).contains("c") && !HS(t, r, j).contains("t")) {
                                if (v0 < Value(0, HS(t, r, j))) {
                                    //  Log.i("LOG", "v0 < HS(0, value(t, r, j)) | r=" + r);
                                    ActivityFillData.all_matrix.get(t).matrix[i][j] += ",s";
                                    NE = false;
                                    break;
                                }
                            }

                        }
                        if (NE) {
                            for (int c = 0; c < col; c++) {
                                if ( !HS(t, i, c).contains("r") && !HS(t, i, c).contains("c") && !HS(t, i, c).contains("t")) {
                                    if (v1 < Value(1, HS(t, i, c))) {
                                        //  Log.i("LOG", "v1 < HS(0, value(t, i, c)) | c=" + c);
                                        ActivityFillData.all_matrix.get(t).matrix[i][j] += ",s";
                                        NE = false;
                                        break;
                                    }
                                }

                            }
                        }
                        if (NE && Global.column == 3) {
                            for (int tbl = 0; tbl < table; tbl++) {
                                if ( !HS(tbl, i, j).contains("r") && !HS(tbl, i, j).contains("c") && !HS(tbl, i, j).contains("t")) {
                                    if (v1 < Value(2, HS(tbl, i, j))) {
                                        ActivityFillData.all_matrix.get(t).matrix[i][j] += ",s";
                                        NE = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (NE) {
                            findNash = true;
                            String p1 = "";
                            String p2 = "";
                            String p3 = "";
                            //  Log.i("LOG", "p1 = " + p1);
                            //   Log.i("LOG", "p2 = " + p2);

                            if (Global.column == 3) {
                                p1 = ActivityFillData.players.get(0).actions.get(t);
                                p2 = ActivityFillData.players.get(1).actions.get(i);
                                p3 = "," + ActivityFillData.players.get(2).actions.get(j);
                            } else {
                                p1 = ActivityFillData.players.get(0).actions.get(i);
                                p2 = ActivityFillData.players.get(1).actions.get(j);
                            }
                            String s = "U(" + p1 + "," + p2 + p3 + ") = ";
                            s = s + ActivityFillData.all_matrix.get(t).matrix[i][j];
                            ne = ne + s + "\n";

                        }

                    }
                }
            }
        }
    }
}
