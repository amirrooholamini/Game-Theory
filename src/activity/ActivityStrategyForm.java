package activity;

import a.s.e.h.f.game_theory_project.DominantAndDominatedStrategy;
import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ActivityStrategyForm extends Activity {

    String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_form);
        response = "";
        TextView txtResponse = (TextView) findViewById(R.id.txtResponse);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                response = DominantAndDominatedStrategy.Execution();
            }
        });
        thread.start();

        txtResponse.setTypeface(Global.EnglishFont);
        txtResponse.setText(response);

    }

}
