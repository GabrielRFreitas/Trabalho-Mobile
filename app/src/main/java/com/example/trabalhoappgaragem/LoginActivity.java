package com.example.trabalhoappgaragem;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL_PADRAO = "mailteste@teste.com";
    private static final String SENHA_PADRAO = "12345";

    private EditText campoEmail;
    private EditText campoSenha;
    private boolean senhaVisivel;

    @Override
    protected void onCreate(Bundle estadoSalvo) {
        super.onCreate(estadoSalvo);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginRoot), (view, recortes) -> {
            Insets barrasSistema = recortes.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(barrasSistema.left, barrasSistema.top, barrasSistema.right, barrasSistema.bottom);
            return recortes;
        });

        configurarTelaLogin();
    }

    private void configurarTelaLogin() {
        campoEmail = findViewById(R.id.emailEditText);
        campoSenha = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(view -> validarLogin());
        findViewById(R.id.passwordToggle).setOnClickListener(view -> alternarVisibilidadeSenha());
        findViewById(R.id.forgotPasswordText).setOnClickListener(view ->
                Toast.makeText(this, "Recuperação de senha em breve", Toast.LENGTH_SHORT).show()
        );
        findViewById(R.id.createAccountButton).setOnClickListener(view ->
                Toast.makeText(this, "Cadastro em breve", Toast.LENGTH_SHORT).show()
        );
    }

    private void validarLogin() {
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        if (EMAIL_PADRAO.equals(email) && SENHA_PADRAO.equals(senha)) {
            Intent intencao = new Intent(this, HomeActivity.class);
            intencao.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intencao);
        } else {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void alternarVisibilidadeSenha() {
        senhaVisivel = !senhaVisivel;

        if (senhaVisivel) {
            campoSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            campoSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        campoSenha.setSelection(campoSenha.getText().length());
    }
}
