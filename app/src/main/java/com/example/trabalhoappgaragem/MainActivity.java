package com.example.trabalhoappgaragem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOME_ESTACIONAMENTO = "com.example.trabalhoappgaragem.PARKING_NAME";
    public static final String EXTRA_PRECO = "com.example.trabalhoappgaragem.PRICE";
    public static final String EXTRA_VAGAS = "com.example.trabalhoappgaragem.VACANCIES";

    private TextView textoData;
    private TextView textoHorarioEntrada;
    private TextView textoDuracao;
    private TextView valorPreco;
    private TextView contadorVagas;
    private View opcaoCarro;
    private View opcaoMoto;
    private View opcaoPreferencial;
    private String nomeEstacionamentoSelecionado = "Estacionamento Central";
    private String precoSelecionado = "R$17,00";
    private String vagasSelecionadas = "12";
    private String tipoVagaSelecionado = "Carro";

    private final Calendar dataSelecionada = Calendar.getInstance();
    private int horaSelecionada = 14;
    private int minutoSelecionado = 30;
    private int duracaoSelecionada = 2;

    @Override
    protected void onCreate(Bundle estadoSalvo) {
        super.onCreate(estadoSalvo);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, recortes) -> {
            Insets barrasSistema = recortes.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(barrasSistema.left, barrasSistema.top, barrasSistema.right, barrasSistema.bottom);
            return recortes;
        });

        configurarInteracoesReserva();
    }

    private void configurarInteracoesReserva() {
        textoData = findViewById(R.id.dateText);
        textoHorarioEntrada = findViewById(R.id.startTimeText);
        textoDuracao = findViewById(R.id.durationText);
        valorPreco = findViewById(R.id.priceValue);
        contadorVagas = findViewById(R.id.vacanciesCount);
        opcaoCarro = findViewById(R.id.carOption);
        opcaoMoto = findViewById(R.id.motorcycleOption);
        opcaoPreferencial = findViewById(R.id.preferentialOption);

        lerDadosEstacionamentoDaIntent();

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
        findViewById(R.id.dateRow).setOnClickListener(view -> mostrarSeletorData());
        findViewById(R.id.startRow).setOnClickListener(view -> mostrarSeletorHorario());
        findViewById(R.id.durationRow).setOnClickListener(view -> mostrarSeletorDuracao());
        findViewById(R.id.continueButton).setOnClickListener(view -> abrirTelaPagamento());

        opcaoCarro.setOnClickListener(view -> selecionarTipoVaga(opcaoCarro));
        opcaoMoto.setOnClickListener(view -> selecionarTipoVaga(opcaoMoto));
        opcaoPreferencial.setOnClickListener(view -> selecionarTipoVaga(opcaoPreferencial));

        atualizarTextoData();
        atualizarTextoHorario();
        atualizarTextoDuracao();
        atualizarInfoEstacionamento();
        selecionarTipoVaga(opcaoCarro);
    }

    private void lerDadosEstacionamentoDaIntent() {
        String nomeEstacionamento = getIntent().getStringExtra(EXTRA_NOME_ESTACIONAMENTO);
        String preco = getIntent().getStringExtra(EXTRA_PRECO);
        String vagas = getIntent().getStringExtra(EXTRA_VAGAS);

        if (nomeEstacionamento != null) {
            nomeEstacionamentoSelecionado = nomeEstacionamento;
        }
        if (preco != null) {
            precoSelecionado = preco;
        }
        if (vagas != null) {
            vagasSelecionadas = vagas;
        }
    }

    private void atualizarInfoEstacionamento() {
        valorPreco.setText(precoSelecionado);
        contadorVagas.setText(vagasSelecionadas);
    }

    private void selecionarTipoVaga(View opcaoSelecionada) {
        opcaoCarro.setBackgroundResource(R.drawable.bg_reservation_option);
        opcaoMoto.setBackgroundResource(R.drawable.bg_reservation_option);
        opcaoPreferencial.setBackgroundResource(R.drawable.bg_reservation_option);

        opcaoSelecionada.setBackgroundResource(R.drawable.bg_reservation_option_selected);

        if (opcaoSelecionada == opcaoCarro) {
            tipoVagaSelecionado = "Carro";
        } else if (opcaoSelecionada == opcaoMoto) {
            tipoVagaSelecionado = "Moto";
        } else {
            tipoVagaSelecionado = "Preferencial";
        }
    }

    private void mostrarSeletorData() {
        DatePickerDialog dialogo = new DatePickerDialog(
                this,
                (view, ano, mes, diaDoMes) -> {
                    dataSelecionada.set(Calendar.YEAR, ano);
                    dataSelecionada.set(Calendar.MONTH, mes);
                    dataSelecionada.set(Calendar.DAY_OF_MONTH, diaDoMes);
                    atualizarTextoData();
                },
                dataSelecionada.get(Calendar.YEAR),
                dataSelecionada.get(Calendar.MONTH),
                dataSelecionada.get(Calendar.DAY_OF_MONTH)
        );
        dialogo.show();
    }

    private void mostrarSeletorHorario() {
        TimePickerDialog dialogo = new TimePickerDialog(
                this,
                (view, horaDoDia, minuto) -> {
                    horaSelecionada = horaDoDia;
                    minutoSelecionado = minuto;
                    atualizarTextoHorario();
                },
                horaSelecionada,
                minutoSelecionado,
                true
        );
        dialogo.show();
    }

    private void mostrarSeletorDuracao() {
        String[] opcoesHoras = new String[12];
        for (int i = 0; i < opcoesHoras.length; i++) {
            int quantidadeHoras = i + 1;
            opcoesHoras[i] = quantidadeHoras == 1 ? "1 hora" : quantidadeHoras + " horas";
        }

        new AlertDialog.Builder(this)
                .setTitle("Permanência")
                .setItems(opcoesHoras, (dialogo, posicao) -> {
                    duracaoSelecionada = posicao + 1;
                    atualizarTextoDuracao();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void atualizarTextoData() {
        String dataFormatada = formatarData(dataSelecionada);

        if (ehHoje(dataSelecionada)) {
            textoData.setText("Hoje, " + dataFormatada);
        } else {
            textoData.setText(dataFormatada + " " + dataSelecionada.get(Calendar.YEAR));
        }
    }

    private void atualizarTextoHorario() {
        textoHorarioEntrada.setText(String.format(Locale.getDefault(), "%02d:%02d", horaSelecionada, minutoSelecionado));
    }

    private void atualizarTextoDuracao() {
        String duracao = duracaoSelecionada == 1 ? "1 hora" : duracaoSelecionada + " horas";
        textoDuracao.setText(duracao);
    }

    private void abrirTelaPagamento() {
        Intent intencao = new Intent(this, PaymentActivity.class);
        intencao.putExtra(PaymentActivity.EXTRA_NOME_ESTACIONAMENTO, nomeEstacionamentoSelecionado);
        intencao.putExtra(PaymentActivity.EXTRA_DATA_HORA, montarDataHoraPagamento());
        intencao.putExtra(PaymentActivity.EXTRA_TIPO_VAGA, tipoVagaSelecionado);
        intencao.putExtra(PaymentActivity.EXTRA_TOTAL, precoSelecionado);
        startActivity(intencao);
    }

    private String montarDataHoraPagamento() {
        Calendar dataInicio = (Calendar) dataSelecionada.clone();
        dataInicio.set(Calendar.HOUR_OF_DAY, horaSelecionada);
        dataInicio.set(Calendar.MINUTE, minutoSelecionado);

        Calendar dataFim = (Calendar) dataInicio.clone();
        dataFim.add(Calendar.HOUR_OF_DAY, duracaoSelecionada);

        String horarioInicio = formatarHorario(dataInicio);
        String horarioFim = formatarHorario(dataFim);

        return formatarData(dataSelecionada) + " " + horarioInicio + " - " + horarioFim;
    }

    private String formatarData(Calendar data) {
        int dia = data.get(Calendar.DAY_OF_MONTH);
        String mes = data.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("pt", "BR"));

        if (mes == null) {
            mes = "";
        } else if (!mes.isEmpty()) {
            mes = mes.substring(0, 1).toUpperCase(new Locale("pt", "BR")) + mes.substring(1);
        }

        return dia + " " + mes;
    }

    private String formatarHorario(Calendar data) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                data.get(Calendar.HOUR_OF_DAY),
                data.get(Calendar.MINUTE));
    }

    private boolean ehHoje(Calendar data) {
        Calendar hoje = Calendar.getInstance();
        return data.get(Calendar.YEAR) == hoje.get(Calendar.YEAR)
                && data.get(Calendar.DAY_OF_YEAR) == hoje.get(Calendar.DAY_OF_YEAR);
    }
}
