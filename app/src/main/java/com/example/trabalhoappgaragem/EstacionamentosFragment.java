package com.example.trabalhoappgaragem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class EstacionamentosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup container, Bundle estadoSalvo) {
        return inflador.inflate(R.layout.fragment_estacionamentos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle estadoSalvo) {
        super.onViewCreated(view, estadoSalvo);
        configurarCardsEstacionamento(view);
    }

    private void configurarCardsEstacionamento(View view) {
        view.findViewById(R.id.centralParkingCard).setOnClickListener(item ->
                abrirTelaReserva("Estacionamento Central", "R$17,00", "12")
        );
        view.findViewById(R.id.shoppingParkingCard).setOnClickListener(item ->
                abrirTelaReserva("Shopping Norte", "R$22,00", "8")
        );
        view.findViewById(R.id.avenueParkingCard).setOnClickListener(item ->
                abrirTelaReserva("Garagem Avenida", "R$15,00", "5")
        );
        view.findViewById(R.id.aeroParkingCard).setOnClickListener(item ->
                abrirTelaReserva("Aero Park", "R$28,00", "18")
        );
    }

    private void abrirTelaReserva(String nomeEstacionamento, String preco, String vagas) {
        Intent intencao = new Intent(requireContext(), MainActivity.class);
        intencao.putExtra(MainActivity.EXTRA_NOME_ESTACIONAMENTO, nomeEstacionamento);
        intencao.putExtra(MainActivity.EXTRA_PRECO, preco);
        intencao.putExtra(MainActivity.EXTRA_VAGAS, vagas);
        startActivity(intencao);
    }
}
