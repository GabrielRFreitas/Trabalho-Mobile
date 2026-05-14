package com.example.trabalhoappgaragem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HistoricoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup container, Bundle estadoSalvo) {
        return inflador.inflate(R.layout.fragment_historico, container, false);
    }
}
