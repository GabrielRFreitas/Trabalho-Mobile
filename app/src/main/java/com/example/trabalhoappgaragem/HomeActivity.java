package com.example.trabalhoappgaragem;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_ABRIR_RESERVA_ATIVA = "com.example.trabalhoappgaragem.OPEN_ACTIVE_RESERVATION";

    private BottomNavigationView navegacaoInferior;

    @Override
    protected void onCreate(Bundle estadoSalvo) {
        super.onCreate(estadoSalvo);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeRoot), (view, recortes) -> {
            Insets barrasSistema = recortes.getInsets(WindowInsetsCompat.Type.systemBars());
            BottomNavigationView navegacaoInferior = findViewById(R.id.homeBottomNavigation);
            view.setPadding(barrasSistema.left, barrasSistema.top, barrasSistema.right, 0);
            navegacaoInferior.setPadding(
                    navegacaoInferior.getPaddingLeft(),
                    navegacaoInferior.getPaddingTop(),
                    navegacaoInferior.getPaddingRight(),
                    barrasSistema.bottom
            );
            return recortes;
        });

        configurarNavegacaoInferior();
        if (estadoSalvo == null) {
            mostrarFragmento(R.id.nav_home);
            abrirAbaRecebida(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intencao) {
        super.onNewIntent(intencao);
        setIntent(intencao);
        abrirAbaRecebida(intencao);
    }

    private void configurarNavegacaoInferior() {
        navegacaoInferior = findViewById(R.id.homeBottomNavigation);
        navegacaoInferior.setOnItemSelectedListener(item -> {
            mostrarFragmento(item.getItemId());
            return true;
        });
    }

    private void abrirAbaRecebida(Intent intencao) {
        if (intencao.getBooleanExtra(EXTRA_ABRIR_RESERVA_ATIVA, false)) {
            navegacaoInferior.setSelectedItemId(R.id.nav_active_reservation);
        }
    }

    private void mostrarFragmento(int idItem) {
        Fragment fragmento;

        if (idItem == R.id.nav_home) {
            fragmento = new EstacionamentosFragment();
        } else if (idItem == R.id.nav_active_reservation) {
            fragmento = new ReservaAtivaFragment();
        } else if (idItem == R.id.nav_history) {
            fragmento = new HistoricoFragment();
        } else {
            fragmento = new EstacionamentosFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, fragmento)
                .commit();
    }
}
