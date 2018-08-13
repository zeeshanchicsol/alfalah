package com.chicsol.alfalah.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.modal.WebArd;
import com.chicsol.alfalah.utils.Constants;

import java.util.List;

/**
 * Created by Android on 11/14/2016.
 */

public class MySpinnerAdapter extends ArrayAdapter<WebArd> {
    // Initialise custom font, for example:
    String SelectedID=null;
    Typeface font = Typeface.createFromAsset(getContext().getAssets(),
            Constants.font_centurygothic);
    List<WebArd> itemslist;
    // (In reality I used a manager which caches the Typeface objects)
    // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

    public MySpinnerAdapter(Context context, int resource, List<WebArd> items) {
        super(context, resource, items);
        this.itemslist = items;
    }
    public void updateDataList(List<WebArd> newlist) {
        itemslist.clear();
        itemslist.addAll(newlist);
        this.notifyDataSetChanged();
    }
    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);


        view.setTypeface(font);
        view.setText(itemslist.get(position).getName());
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        view.setTextColor(getContext().getResources().getColor(R.color.colorRegistrationFields));
        view.setTag(itemslist.get(position));
        return view;
    }




    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        view.setText(itemslist.get(position).getName());
  /*      if (position == 0) {
            view.setTextColor(getContext().getResources().getColor(R.color.colorTextRed));
        }*/
        view.setTag(itemslist.get(position));
        return view;
    }
}