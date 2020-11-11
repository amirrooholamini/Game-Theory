package activity;

import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ActivityGroupMembers extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        final LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        final LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.layoutRoot);

        final TextView t1 = (TextView) findViewById(R.id.t1);
        final TextView t2 = (TextView) findViewById(R.id.t2);
        final TextView t3 = (TextView) findViewById(R.id.t3);
        final TextView t4 = (TextView) findViewById(R.id.t4);
        final TextView t5 = (TextView) findViewById(R.id.t5);
        final TextView t6 = (TextView) findViewById(R.id.t6);
        final TextView t7 = (TextView) findViewById(R.id.t7);
        final TextView t8 = (TextView) findViewById(R.id.t8);
        final TextView t9 = (TextView) findViewById(R.id.t9);
        final TextView t10 = (TextView) findViewById(R.id.t10);

        Animation animation1 = AnimationUtils.loadAnimation(Global.context, R.anim.anim1);
        Animation animation2 = AnimationUtils.loadAnimation(Global.context, R.anim.anim2);
        final Animation animation1_out = AnimationUtils.loadAnimation(Global.context, R.anim.anim1_out);
        final Animation animation2_out = AnimationUtils.loadAnimation(Global.context, R.anim.anim2_out);

        t1.setTypeface(Global.persionFont);
        t2.setTypeface(Global.persionFont);
        t3.setTypeface(Global.persionFont);
        t4.setTypeface(Global.persionFont);
        t5.setTypeface(Global.persionFont);
        t6.setTypeface(Global.persionFont);
        t7.setTypeface(Global.persionFont);
        t8.setTypeface(Global.persionFont);
        t9.setTypeface(Global.persionFont);
        t10.setTypeface(Global.persionFont);

        layout1.startAnimation(animation1);
        layout2.startAnimation(animation2);

        OnClickListener exit = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                t1.setClickable(false);
                t2.setClickable(false);
                t3.setClickable(false);
                t4.setClickable(false);
                t5.setClickable(false);
                t6.setClickable(false);
                t7.setClickable(false);
                t8.setClickable(false);
                t9.setClickable(false);
                t10.setClickable(false);
                layout1.setClickable(false);
                layout2.setClickable(false);
                layoutRoot.setClickable(false);
                layoutRoot.setScrollContainer(false);
                layout1.setScrollContainer(false);
                layout2.setScrollContainer(false);

                layout1.startAnimation(animation1_out);
                layout2.startAnimation(animation2_out);
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1600);
                            Intent intent = new Intent(ActivityGroupMembers.this, ActivityFirst.class);
                            ActivityGroupMembers.this.startActivity(intent);
                            ActivityGroupMembers.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                t.start();
            }
        };

        t1.setOnClickListener(exit);
        t2.setOnClickListener(exit);
        t3.setOnClickListener(exit);
        t4.setOnClickListener(exit);
        t5.setOnClickListener(exit);
        t6.setOnClickListener(exit);
        t7.setOnClickListener(exit);
        t8.setOnClickListener(exit);
        t9.setOnClickListener(exit);
        t10.setOnClickListener(exit);
        layout1.setOnClickListener(exit);
        layout2.setOnClickListener(exit);
        layoutRoot.setOnClickListener(exit);

        /*
                Global.HANDLER.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(ActivityGroupMembers.this, ActivityFirst.class);
                        ActivityGroupMembers.this.startActivity(intent);
                        finish();
                    }
                }, 3000);
                */
    }
}
