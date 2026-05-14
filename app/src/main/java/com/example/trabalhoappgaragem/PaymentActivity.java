package com.example.trabalhoappgaragem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentActivity extends AppCompatActivity {

    public static final String EXTRA_NOME_ESTACIONAMENTO = "com.example.trabalhoappgaragem.PAYMENT_PARKING_NAME";
    public static final String EXTRA_DATA_HORA = "com.example.trabalhoappgaragem.DATE_TIME";
    public static final String EXTRA_TIPO_VAGA = "com.example.trabalhoappgaragem.SPOT_TYPE";
    public static final String EXTRA_TOTAL = "com.example.trabalhoappgaragem.TOTAL";

    private View metodoPix;
    private View metodoCartao;
    private View metodoCarteira;
    private ImageView radioPix;
    private ImageView radioCartao;
    private ImageView radioCarteira;

    @Override
    protected void onCreate(Bundle estadoSalvo) {
        super.onCreate(estadoSalvo);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.paymentRoot), (view, recortes) -> {
            Insets barrasSistema = recortes.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(barrasSistema.left, barrasSistema.top, barrasSistema.right, barrasSistema.bottom);
            return recortes;
        });

        configurarTelaPagamento();
    }

    private void configurarTelaPagamento() {
        TextView textoDataHoraPagamento = findViewById(R.id.paymentDateTimeText);
        TextView textoTipoVagaPagamento = findViewById(R.id.paymentSpotText);
        TextView textoLocal = findViewById(R.id.placeText);
        TextView valorTotal = findViewById(R.id.totalValue);
        TextView botaoPagar = findViewById(R.id.payButton);

        metodoPix = findViewById(R.id.pixMethod);
        metodoCartao = findViewById(R.id.cardMethod);
        metodoCarteira = findViewById(R.id.walletMethod);
        radioPix = findViewById(R.id.pixRadio);
        radioCartao = findViewById(R.id.cardRadio);
        radioCarteira = findViewById(R.id.walletRadio);

        String nomeEstacionamento = getIntent().getStringExtra(EXTRA_NOME_ESTACIONAMENTO);
        String dataHora = getIntent().getStringExtra(EXTRA_DATA_HORA);
        String tipoVaga = getIntent().getStringExtra(EXTRA_TIPO_VAGA);
        String total = getIntent().getStringExtra(EXTRA_TOTAL);

        if (nomeEstacionamento != null) {
            textoLocal.setText(nomeEstacionamento);
        }
        if (dataHora != null) {
            textoDataHoraPagamento.setText(dataHora);
        }
        if (tipoVaga != null) {
            textoTipoVagaPagamento.setText("Vaga: " + tipoVaga);
        }
        if (total != null) {
            valorTotal.setText(total);
            botaoPagar.setText("Pagar " + total);
        }

        findViewById(R.id.paymentBackButton).setOnClickListener(view -> finish());
        metodoPix.setOnClickListener(view -> selecionarFormaPagamento(metodoPix, radioPix));
        metodoCartao.setOnClickListener(view -> selecionarFormaPagamento(metodoCartao, radioCartao));
        metodoCarteira.setOnClickListener(view -> selecionarFormaPagamento(metodoCarteira, radioCarteira));
        botaoPagar.setOnClickListener(view -> abrirTelaReservaAtiva());

        selecionarFormaPagamento(metodoPix, radioPix);
    }

    private void selecionarFormaPagamento(View metodoSelecionado, ImageView radioSelecionado) {
        metodoPix.setBackgroundResource(R.drawable.bg_reservation_row);
        metodoCartao.setBackgroundResource(R.drawable.bg_reservation_row);
        metodoCarteira.setBackgroundResource(R.drawable.bg_reservation_row);
        radioPix.setImageResource(R.drawable.ic_radio_unselected);
        radioCartao.setImageResource(R.drawable.ic_radio_unselected);
        radioCarteira.setImageResource(R.drawable.ic_radio_unselected);

        metodoSelecionado.setBackgroundResource(R.drawable.bg_payment_method_selected);
        radioSelecionado.setImageResource(R.drawable.ic_radio_selected);
    }

    private void abrirTelaReservaAtiva() {
        Intent intencao = new Intent(this, HomeActivity.class);
        intencao.putExtra(HomeActivity.EXTRA_ABRIR_RESERVA_ATIVA, true);
        intencao.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intencao);
    }
}
