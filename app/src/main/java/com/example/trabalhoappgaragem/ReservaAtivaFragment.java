package com.example.trabalhoappgaragem;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ReservaAtivaFragment extends Fragment {

    private static final String CODIGO_RESERVA_PADRAO = "#ABF3-27D1";
    private static final int TAMANHO_QR_CODE = 512;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup container, Bundle estadoSalvo) {
        return inflador.inflate(R.layout.fragment_reserva_ativa, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle estadoSalvo) {
        super.onViewCreated(view, estadoSalvo);
        configurarTelaReservaAtiva(view);
    }

    private void configurarTelaReservaAtiva(View view) {
        TextView textoCodigoReserva = view.findViewById(R.id.reservationCodeText);
        ImageView imagemQrCode = view.findViewById(R.id.qrCodeImage);

        textoCodigoReserva.setText("Código: " + CODIGO_RESERVA_PADRAO);
        imagemQrCode.setImageBitmap(criarBitmapQrCode("Reserva ativa " + CODIGO_RESERVA_PADRAO));

        view.findViewById(R.id.extendReservationButton).setOnClickListener(item ->
                Toast.makeText(requireContext(), "Extensão de reserva selecionada", Toast.LENGTH_SHORT).show()
        );
        view.findViewById(R.id.cancelReservationButton).setOnClickListener(item ->
                Toast.makeText(requireContext(), "Reserva cancelada", Toast.LENGTH_SHORT).show()
        );
    }

    private Bitmap criarBitmapQrCode(String conteudo) {
        QRCodeWriter escritor = new QRCodeWriter();

        try {
            BitMatrix matrizBits = escritor.encode(conteudo, BarcodeFormat.QR_CODE, TAMANHO_QR_CODE, TAMANHO_QR_CODE);
            Bitmap imagemBitmap = Bitmap.createBitmap(TAMANHO_QR_CODE, TAMANHO_QR_CODE, Bitmap.Config.RGB_565);

            for (int coluna = 0; coluna < TAMANHO_QR_CODE; coluna++) {
                for (int linha = 0; linha < TAMANHO_QR_CODE; linha++) {
                    imagemBitmap.setPixel(coluna, linha, matrizBits.get(coluna, linha) ? Color.BLACK : Color.WHITE);
                }
            }

            return imagemBitmap;
        } catch (WriterException excecao) {
            Toast.makeText(requireContext(), "Não foi possível gerar o QR Code", Toast.LENGTH_SHORT).show();
            return Bitmap.createBitmap(TAMANHO_QR_CODE, TAMANHO_QR_CODE, Bitmap.Config.RGB_565);
        }
    }
}
