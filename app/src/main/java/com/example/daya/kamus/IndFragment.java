package com.example.daya.kamus;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.daya.kamus.PemrosesKata.Find;
import com.example.daya.kamus.db.KamusHelper;
import com.example.daya.kamus.db.KamusModel.KamusModel;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndFragment extends Fragment {

    AutoCompleteTextView search_indonesia;
    KamusHelper kamusHelper;
    public IndFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ind, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, daftar());

        search_indonesia = view.findViewById(R.id.search_indonesia);
        search_indonesia.setThreshold(1);
        search_indonesia.setAdapter(adapter);

        search_indonesia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new Find(getActivity(), search_indonesia.getText().toString(),"idn").execute();
            }
        });
    }

    public ArrayList<String> daftar() {

        kamusHelper = new KamusHelper(getActivity());
        ArrayList<String> daftar_kata = new ArrayList<>();

        kamusHelper.open();
        ArrayList<KamusModel> kamusModelEnglishes = kamusHelper.getAllDataIDN();
        kamusHelper.close();
        Log.i(TAG, "daftar: id " + kamusModelEnglishes.size());

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
