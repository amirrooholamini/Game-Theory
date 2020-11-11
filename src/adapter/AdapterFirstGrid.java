package adapter;

import java.util.ArrayList;
import structure.StructFirstGrid;
import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.R;
import activity.ActivityFirst;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class AdapterFirstGrid extends ArrayAdapter<StructFirstGrid> {

    public AdapterFirstGrid(ArrayList<StructFirstGrid> array) {
        super(Global.context, R.layout.adapter_gridview_activity_first, array);
    }


    private static class ViewHolder {

        public ViewGroup layoutRoot;
        public TextView  txtUtilities;


        public ViewHolder(View view) {
            txtUtilities = (TextView) view.findViewById(R.id.txtUtilities);
            layoutRoot = (ViewGroup) view.findViewById(R.id.layout_root);
        }


        public void fill(final ArrayAdapter<StructFirstGrid> adapter, final StructFirstGrid item, final int position) {
            txtUtilities.setText(item.txt);

            if (position >= 0 && position < Global.column) {
                txtUtilities.setBackgroundColor(Color.parseColor("#ff6600"));

            } else {
                //txtUtilities.setTypeface(Global.persionFont);
                txtUtilities.setBackgroundColor(Color.parseColor("#88558888"));
            }
            txtUtilities.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (txtUtilities.getText().toString().equals("")) {
                        ActivityFirst.txtR.setText("Naming");
                        if (position < Global.column) {
                            ActivityFirst.txtOldName.setText("Player");
                        } else {
                            ActivityFirst.txtOldName.setText("Action");
                        }
                        ActivityFirst.txtTo.setVisibility(View.GONE);
                    } else {
                        ActivityFirst.txtOldName.setText(txtUtilities.getText().toString());
                        ActivityFirst.txtR.setText("Rename");
                        ActivityFirst.txtTo.setVisibility(View.VISIBLE);
                    }
                    Global.selectedItem = position;
                    ActivityFirst.dialog.show();
                }
            });
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructFirstGrid item = getItem(position);
        if (convertView == null) {
            convertView = Global.inflater.inflate(R.layout.adapter_gridview_activity_first, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        return convertView;
    }


    @Override
    public int getCount() {
        return (10 * Global.column) + Global.column;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

}
