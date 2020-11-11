package adapter;

import java.util.ArrayList;
import structure.StructGridFill;
import a.s.e.h.f.game_theory_project.Global;
import a.s.e.h.f.game_theory_project.R;
import activity.ActivityFillData;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class AdapterGridFill extends ArrayAdapter<StructGridFill> {

    public static int col = ActivityFillData.col;
    public static int row = ActivityFillData.row;


    public AdapterGridFill(ArrayList<StructGridFill> array) {
        super(Global.context, R.layout.adapter_grid_fill, array);
    }


    private static class ViewHolder {

        public TextView txtV;


        public ViewHolder(View view) {
            txtV = (TextView) view.findViewById(R.id.txtV);
        }


        public void fill(final ArrayAdapter<StructGridFill> adapter, final StructGridFill item, final int position) {
            txtV.setText(item.txt);
            txtV.setTypeface(Global.NumberFont);

            txtV.setBackgroundColor(Color.parseColor(item.color));

            txtV.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Global.selectedItem = position;
                    String text = txtV.getText().toString();
                    if (text.equals("") || text.contains(",")) {
                        activity.ActivityFillData.dialog.show();
                    }
                }
            });
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Log.i("LOG", "COL * ROW = " + (col * row));
        ViewHolder holder;

        StructGridFill item = getItem(position);
        if (convertView == null) {
            convertView = Global.inflater.inflate(R.layout.adapter_grid_fill, parent, false);
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
        return col * row;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

}
