package com.example.daya.kamus.tab;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.daya.kamus.PemrosesKata.Find;
import com.example.daya.kamus.R;
import com.example.daya.kamus.db.KamusHelper;
import com.example.daya.kamus.db.KamusModel.KamusModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EngFragment extends Fragment {
    private AutoCompleteTextView searchenglish;
    private KamusHelper kamusHelper;

    public EngFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_eng, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, daftar());

        searchenglish = view.findViewById(R.id.search_english);
        searchenglish.setThreshold(1);
        searchenglish.setAdapter(adapter);

        searchenglish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new Find(getActivity(), searchenglish.getText().toString(),getString(R.string.eng)).execute();
            }
        });

    }

    private ArrayList<String> daftar() {

        ArrayList<String> daftar_kata = new ArrayList<>();

        kamusHelper.open();
        ArrayList<KamusModel> kamusModelEnglishes = kamusHelper.getAllDataEng();
        kamusHelper.close();

        for (int w = 0; w < kamusModelEnglishes.size(); w++) {
            daftar_kata.add( kamusModelEnglishes.get(w).getWORD());
        }

        return daftar_kata;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (kamusHelper != null) {
            kamusHelper = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        kamusHelper = new KamusHelper(getActivity());
    }
}





















