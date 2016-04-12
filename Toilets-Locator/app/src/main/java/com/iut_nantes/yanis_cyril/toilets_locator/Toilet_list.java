package com.iut_nantes.yanis_cyril.toilets_locator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cyril on 01/04/2016.
 */
public class Toilet_list extends BaseAdapter {
    private List<Toilets> list;
    private Context context;

    public Toilet_list(Context context, int resource, List<Toilets> list) {
        super();
        this.list = list;
        this.context = context;
        Log.i("Toilet_list.construct", list.toString());
    }

    public void setList(List<Toilets> listS) {
        list = listS;
        Log.i("Toilet_list.setList", list.toString());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) list.get(position).get_id();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toilets_list, parent, false);
        TextView contenu = (TextView) view.findViewById(R.id.contenu);
        ImageView typeIcon = (ImageView) view.findViewById(R.id.typeIcon);
        ImageView autoIcon = (ImageView) view.findViewById(R.id.autoIcon);
        ImageView handicapIcon = (ImageView) view.findViewById(R.id.handicapIcon);

        contenu.setText(String.valueOf(list.get(position).get_commune()));

        if (list.get(position).get_type().equalsIgnoreCase("Bâti")) {
            typeIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.batiment));
        } else {
            typeIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.immobilier));
        }
        if (list.get(position).get_auto().equalsIgnoreCase("Oui")) {
            autoIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.automatic));
        } else {
            autoIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.non_automatic));
        }
        if (list.get(position).get_accessibilite().equalsIgnoreCase("Oui")) {
            handicapIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.handicap));
        } else {
            handicapIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.non_handicap));
        }
        return view;
    }
}
