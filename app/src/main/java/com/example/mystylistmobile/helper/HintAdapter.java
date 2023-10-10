package com.example.mystylistmobile.helper;

import android.widget.ArrayAdapter;

public class HintAdapter extends ArrayAdapter<CharSequence> {

    public HintAdapter(ArrayAdapter<CharSequence> adapter, CharSequence hint) {
        super(adapter.getContext(), android.R.layout.simple_spinner_dropdown_item, addHintToItems(adapter, hint));
    }

    private static CharSequence[] addHintToItems(ArrayAdapter<CharSequence> adapter, CharSequence hint) {
        int count = adapter.getCount();
        CharSequence[] items = new CharSequence[count + 1];

        // Add the hint as the first item
        items[0] = hint;

        // Copy the items from the original adapter
        for (int i = 0; i < count; i++) {
            items[i + 1] = adapter.getItem(i);
        }
        return items;
    }
}
