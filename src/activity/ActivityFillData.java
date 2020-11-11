package activity;

import java.util.ArrayList;
import structure.StructGridFill;
import a.s.e.h.f.game_theory_project.BestResponseStrategy;
import a.s.e.h.f.game_theory_project.DominantAndDominatedStrategy;
import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.Matrix;
import a.s.e.h.f.game_theory_project.Player;
import a.s.e.h.f.game_theory_project.Possibility;
import a.s.e.h.f.game_theory_project.R;
import adapter.AdapterGridFill;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;


public class ActivityFillData extends Activity {

    AdapterGridFill                 adapter;
    public static ArrayList<Player> players    = new ArrayList<Player>();
    public static int               col        = 0;
    public static int               row        = 0;
    public static Dialog            dialog;
    int                             step       = 1;
    public static ArrayList<Matrix> all_matrix = new ArrayList<Matrix>();
    private ViewAnimator            viewAnimator;
    private boolean                 firstLayout;
    LinearLayout                    layout1;
    LinearLayout                    layout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_data);

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        firstLayout = true;
        viewAnimator = (ViewFlipper) findViewById(R.id.viewFlipper);
        final Button btnNext = (Button) findViewById(R.id.btnNext);
        final TextView txtP3 = (TextView) findViewById(R.id.txtP3);
        if (Global.column == 2) {
            txtP3.setVisibility(View.GONE);
        }
        final GridView gridFill = (GridView) findViewById(R.id.gridFill);
        dialog = new Dialog(ActivityFillData.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fill);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        final TextView txtResult = (TextView) findViewById(R.id.txtResult);
        final EditText edt1 = (EditText) dialog.findViewById(R.id.edt1);
        final EditText edt2 = (EditText) dialog.findViewById(R.id.edt2);
        final EditText edt3 = (EditText) dialog.findViewById(R.id.edt3);
        edt1.setTypeface(Global.NumberFont);
        edt2.setTypeface(Global.NumberFont);
        edt3.setTypeface(Global.NumberFont);
        Button btnFill = (Button) dialog.findViewById(R.id.btnFill);
        if (Global.column == 2) {
            edt3.setVisibility(View.GONE);
        }

        btnNext.setTypeface(Global.EnglishFont);
        txtP3.setTypeface(Global.EnglishFont);
        btnFill.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String t1 = edt1.getText().toString().toString();
                String t2 = edt2.getText().toString().toString();
                String t3 = edt3.getText().toString().toString();
                if (Global.column == 2) {
                    t3 = " ";
                }
                if (t1.equals("") || t2.equals("") || t3.equals("")) {
                    Toast.makeText(Global.context, "Invalid value", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (t1.equals(".") || t2.equals(".") || t3.equals(".") || t1.equals("-") || t2.equals("-") || t3.equals("-")) {
                    Toast.makeText(Global.context, "Invalid value", Toast.LENGTH_SHORT).show();
                    return;
                }
                StructGridFill item = Global.gridFill.get(Global.selectedItem);
                String text = "(" + t1 + ",";
                text += t2;
                if (Global.column == 3) {
                    text += "," + t3;
                }
                text += ")";
                item.txt = text;
                item.color = "#88333333";
                adapter.notifyDataSetChanged();
                edt1.setText("");
                edt2.setText("");
                edt3.setText("");
                edt1.requestFocus();
                edt2.clearFocus();
                edt3.clearFocus();
                dialog.dismiss();
            }
        });

        int column = Global.column;
        players.clear();
        all_matrix.clear();
        for (int i = 0; i < column; i++) {
            Player player = new Player();
            player.name = Global.firstGrid.get(i).txt;
            players.add(player);
        }

        ArrayList<String> actions = new ArrayList<String>();
        for (int i = 0; i < players.size(); i++) {
            actions = new ArrayList<String>();
            for (int j = i + column; j < (11 * column); j = j + column) {
                if ( !Global.firstGrid.get(j).txt.equals("")) {
                    actions.add(Global.firstGrid.get(j).txt);
                }
            }
            players.get(i).actions = actions;
        }
        txtP3.setText(players.get(0).name + " = " + players.get(0).actions.get((step - 1)));
        if (Global.column == 3) {
            col = (players.get(2).actions.size() + 1);
            row = (players.get(1).actions.size() + 1);
        } else {
            col = (players.get(1).actions.size() + 1);
            row = (players.get(0).actions.size() + 1);
        }

        btnNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                for (int i = 0; i < Global.gridFill.size(); i++) {
                    String t = Global.gridFill.get(i).txt;
                    if (t.equals("")) {
                        Toast.makeText(Global.context, "All fields must be initialized", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                ArrayList<StructGridFill> temp = new ArrayList<StructGridFill>();
                step++;
                int size = 0;
                if (Global.column == 2) {
                    size = 1;
                } else {
                    size = players.get(0).actions.size();
                }

                if (step <= size) {
                    txtP3.setText(players.get(0).name + " = " + players.get(0).actions.get((step - 1)));
                }

                temp = new ArrayList<StructGridFill>();
                String[][] myMatrix = new String[row - 1][col - 1];

                for (int i = 0; i < Global.gridFill.size(); i++) {
                    String t = Global.gridFill.get(i).txt;
                    if (t.contains(",") && t.contains(")") && t.contains("(")) {
                        temp.add(Global.gridFill.get(i));
                    }
                }
                for (int i = 0; i < temp.size(); i++) {
                    int x = (int) (i / (col - 1));
                    int y = (int) (i % (col - 1));
                    myMatrix[x][y] = temp.get(i).txt;

                }
                Matrix m = new Matrix();
                m.matrix = myMatrix;
                all_matrix.add(m);
                if (step > size) {
                    //LayoutParams params = layout2.getLayoutParams();
                    // params.height = layout1.getHeight();
                    String response = "-----Dominant & Dominated Strategy-----" + "\n" + "\n";
                    response = response + DominantAndDominatedStrategy.Execution();
                    response = response + "-----Best Response Strategy-----" + "\n" + "\n";
                    response = response + BestResponseStrategy.Execution();
                    response = response + "\n";
                    txtResult.setTypeface(Global.EnglishFont);
                    //response = response.replaceAll(",|", "");
                    //response = response.replaceAll(",s", "");
                    //response = response.replaceAll(",t", "");
                    //response = response.replaceAll(",r", "");
                    // response = response.replaceAll(",c", "");
                    if (Global.column == 2) {
                        response = response + "-----Possibility-----" + "\n" + "\n";
                        response = response + Possibility.Execution();

                    }

                    txtResult.setText(response);
                    animation.AnimationFactory.flipTransition(viewAnimator, animation.AnimationFactory.FlipDirection.LEFT_RIGHT);
                    firstLayout = false;
                    /*
                        Intent intent = new Intent(ActivityFillData.this, ActivityStrategyForm.class);
                        ActivityFillData.this.startActivity(intent);*/
                    //  Toast.makeText(Global.context, "End", Toast.LENGTH_LONG).show();
                    return;
                }
                clearData();
                Animation anim1 = AnimationUtils.loadAnimation(Global.context, android.R.anim.slide_in_left);
                gridFill.setAnimation(anim1);
                if (step < players.get(0).actions.size()) {
                    if (step == players.get(0).actions.size()) {
                        btnNext.setText("Finish");
                    }

                }

            }
        });

        adapter = new AdapterGridFill(Global.gridFill);
        gridFill.setNumColumns(col);
        gridFill.setAdapter(adapter);
        populateFakeData();
    }


    private void populateFakeData() {
        AdapterGridFill.col = col;
        AdapterGridFill.row = row;
        Global.gridFill.clear();
        for (int i = 0; i < col * row; i++) {
            StructGridFill grid = new StructGridFill();
            grid.txt = "";
            grid.color = "#88a62626";
            if (i == 0) {
                grid.color = "#88a62626";
                int a = (players.size() - 1);
                int b = (players.size() - 2);
                String txt = players.get(b).name;
                txt += " / ";
                txt += players.get(a).name;
                grid.txt = txt;

            } else {
                grid.color = "#88ffffff";
                if (i < col) {
                    grid.txt = players.get(players.size() - 1).actions.get(i - 1);
                } else if (i % col == 0) {
                    int p = i / col;
                    grid.txt = players.get(players.size() - 2).actions.get(p - 1);
                }
            }
            Global.gridFill.add(grid);
        }
        adapter.notifyDataSetChanged();

    }


    private void clearData() {
        Global.gridFill.clear();
        for (int i = 0; i < col * row; i++) {
            StructGridFill grid = new StructGridFill();
            grid.txt = "";
            grid.color = "#88a62626";
            if (i == 0) {
                grid.color = "#88a62626";
                int a = (players.size() - 1);
                int b = (players.size() - 2);
                String txt = players.get(b).name;
                txt += " / ";
                txt += players.get(a).name;
                grid.txt = txt;
            } else {
                grid.color = "#88ffffff";
                if (i < col) {
                    grid.txt = players.get(players.size() - 1).actions.get(i - 1);
                } else if (i % col == 0) {
                    int p = i / col;
                    grid.txt = players.get(players.size() - 2).actions.get(p - 1);
                }
            }
            Global.gridFill.add(grid);
        }
        adapter.notifyDataSetChanged();

    }

    /*
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ( !firstLayout) {
                    animation.AnimationFactory.flipTransition(viewAnimator, animation.AnimationFactory.FlipDirection.LEFT_RIGHT);
                    firstLayout = true;
                    return false;
                }
            }
            return super.onKeyDown(keyCode, event);
        }
        */
}
