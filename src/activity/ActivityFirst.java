package activity;

import structure.StructFirstGrid;
import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.R;
import adapter.AdapterFirstGrid;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityFirst extends Activity {

    AdapterFirstGrid       adapter;
    public static Dialog   dialog;
    public static TextView txtOldName;
    public static TextView txtR;
    public static TextView txtTo;
    TextView               txtN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Global.column = 0;
        Global.firstGrid.clear();
        ActivityFillData.all_matrix.clear();
        ActivityFillData.players.clear();
        ActivityFillData.col = 0;
        ActivityFillData.row = 0;
        dialog = new Dialog(ActivityFirst.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_name);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        Button btnSaveD = (Button) dialog.findViewById(R.id.btnSaveD);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        final EditText edtNewName = (EditText) dialog.findViewById(R.id.edtNewName);
        txtOldName = (TextView) dialog.findViewById(R.id.txtOldName);
        txtR = (TextView) dialog.findViewById(R.id.txtR);
        txtTo = (TextView) dialog.findViewById(R.id.txtTo);
        txtN = (TextView) findViewById(R.id.txtN);

        final GridView firstGridView = (GridView) findViewById(R.id.firstGridView);
        adapter = new AdapterFirstGrid(Global.firstGrid);
        firstGridView.setAdapter(adapter);

        final Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE);

        btnNext.setTypeface(Global.EnglishFont);

        OnClickListener setNumberOfPlayers = new OnClickListener() {

            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                txtN.setText(btn.getText().toString());
                Global.column = Integer.parseInt(btn.getText().toString());
                //Toast.makeText(Global.context, btn.getText().toString(), Toast.LENGTH_SHORT).show();
                btnNext.setVisibility(View.VISIBLE);
                firstGridView.setNumColumns(Global.column);
                adapter = new AdapterFirstGrid(Global.firstGrid);
                firstGridView.setAdapter(adapter);
                populateFakeData();
                firstGridView.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(Global.context, android.R.anim.slide_in_left);
                firstGridView.setAnimation(anim);
            }
        };

        btn2.setOnClickListener(setNumberOfPlayers);
        btn3.setOnClickListener(setNumberOfPlayers);

        btnNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (Global.column == 3) {
                    if ((Global.firstGrid.get(3).txt.equals("")) || (Global.firstGrid.get(4).txt.equals("")) || (Global.firstGrid.get(5).txt.equals(""))) {
                        Toast.makeText(Global.context, "ERROR : Incorrect actions", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if (Global.column == 2) {
                    if ((Global.firstGrid.get(2).txt.equals("")) || (Global.firstGrid.get(3).txt.equals(""))) {
                        Toast.makeText(Global.context, "ERROR : Incorrect actions", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                Intent intent = new Intent(ActivityFirst.this, ActivityFillData.class);
                ActivityFirst.this.startActivity(intent);
                ActivityFirst.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                // finish();

            }
        });

        btnSaveD.setTypeface(Global.EnglishFont);
        btnSaveD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                edtNewName.requestFocus();
                StructFirstGrid item = Global.firstGrid.get(Global.selectedItem);
                item.txt = edtNewName.getText().toString();
                adapter.notifyDataSetChanged();
                edtNewName.setText("");
                dialog.dismiss();
            }
        });

        /*

        findViewById(R.id.btnSave).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.VISIBLE);
                if (edtNumberOfColumn.getText().toString().equals("")) {
                    return;
                }
                Global.column = Integer.parseInt(edtNumberOfColumn.getText().toString());
                if (Global.column < 2 || Global.column > 10) {
                    return;
                }
                firstGridView.setNumColumns(Global.column);
                adapter = new AdapterFirstGrid(Global.firstGrid);
                firstGridView.setAdapter(adapter);
                populateFakeData();
                firstGridView.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(Global.context, android.R.anim.slide_in_left);
                firstGridView.setAnimation(anim);
            }
        });
        */

    }


    private void populateFakeData() {
        int p = 1;
        int a = 1;
        Global.firstGrid.clear();
        for (int i = 0; i < 100; i++) {
            StructFirstGrid firstGrid = new StructFirstGrid();
            if (p <= Global.column) {
                firstGrid.txt = "P" + p;
                p++;
            }
            else {
                firstGrid.txt = "";
                a++;
            }
            Global.firstGrid.add(firstGrid);
        }
        adapter.notifyDataSetChanged();

    }

}
