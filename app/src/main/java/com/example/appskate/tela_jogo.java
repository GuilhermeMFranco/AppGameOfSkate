package com.example.appskate;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;

public class tela_jogo extends AppCompatActivity {



    private int pontos = 0;
    private int manobras = 0;
    private static final int MAX_MANOBRAS = 10;

    private TextView textPontos;
    private TextView resultadoTextView;
    private final Random random = new Random();
    private MediaPlayer clickSound;  // MediaPlayer para o som de clique

    private final String[] manobras1 = {
            "Ollie", "Kickflip", "Heelflip", "Pop Shove-it", "Frontside 180", "Backside 180",
            "Varial Kickflip", "Hardflip", "Laser Flip", "360 Shove-it", "Fakie Varial",
            "360 Flip", "Impossible", "Nollie", "Nollie Flip", "Nollie Heelflip",
            "Bigspin", "Fakie Flip", "Fakie 360 Flip", "Fakie Ollie", "Fakie Varial",
            "Manual Front", "Manual Back"
    };

    private final String[] manobras2 = {
            "Smith Grind", "Feeble Grind", "K grind", "Noseblunt Slide", "Blunt Slide",
            "50-50 Grind", "Miller Flip", "Backside Smith Grind", "Frontside Smith Grind",
            "Frontside Feeble Grind", "Backside Feeble Grind", "Crooked Grind",
            "Backside Crooked Grind", "Frontside Crooked Grind",
            "Smith Grind to Slide", "Feeble Grind to Slide", "Blunt Slide to Smith Grind",
            "Noseblunt Slide to Feeble Grind"
    };

    private final String[] manobras3 = {
            "Ollie", "Kickflip", "Heelflip", "Pop Shove-it", "Frontside 180",
            "Backside 180", "Tre Flip", "Nollie", "360 Flip", "Switch Flip",
            "Smith Grind", "Feeble Grind", "Backside Smith Grind", "Frontside Feeble Grind",
            "Frontside Noseblunt Slide", "Backside Noseblunt Slide", "Blunt Slide",
            "Noseblunt Slide", "Rock to Fakie", "Fakie Smith Grind", "Fakie Feeble Grind",
            "K Grind", "Rail Slide", "Overcrook Grind"
    };

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        Button btnSair = findViewById(R.id.btn_sair);
        btnSair.setOnClickListener(view -> {
            Log.d(TAG, "Botão Sair clicado");
            Intent intent = new Intent(tela_jogo.this, MainActivity.class);
            startActivity(intent);
            finish(); // Fecha a Activity atual
        });





        textPontos = findViewById(R.id.textPontos);
        resultadoTextView = findViewById(R.id.txtresultado);

        ImageButton acertouButton = findViewById(R.id.acertouButton);
        ImageButton errouButton = findViewById(R.id.errouButton);
        ImageButton resetarButton = findViewById(R.id.resetarButton);
        ImageButton btnEscolherManobra1 = findViewById(R.id.btn_escolhermanobra1);
        ImageButton btnEscolherManobra2 = findViewById(R.id.btn_escolhermanobra2);
        ImageButton btnEscolherManobra3 = findViewById(R.id.btn_escolhermanobra3);



        // Inicializar o MediaPlayer com o som de clique
        clickSound = MediaPlayer.create(this, R.raw.click_button);

        // Configurar eventos de toque para os botões
        configurarBotao(acertouButton);
        configurarBotao(errouButton);
        configurarBotao(resetarButton);
        configurarBotao(btnEscolherManobra1);
        configurarBotao(btnEscolherManobra2);
        configurarBotao(btnEscolherManobra3);

        // Configurar eventos de clique para os botões
        acertouButton.setOnClickListener(view -> {
            pontos++;
            manobras++;
            atualizarPontuacao();
            if (manobras == MAX_MANOBRAS) {
                mostrarDialogoFinal("Parabéns, você acertou 10 manobras!");
            }
        });

        errouButton.setOnClickListener(view -> {
            if (pontos > 0 && manobras > 0) {
                pontos--;
                manobras--;
                atualizarPontuacao();
            }
            if (manobras == 0) {
                mostrarDialogoFinal("Você errou! Não desista, tente outra vez!");
            }
        });

        resetarButton.setOnClickListener(view -> restartGame());

        btnEscolherManobra1.setOnClickListener(v -> {
            int index = random.nextInt(manobras1.length);
            String manobraEscolhida = manobras1[index];
            resultadoTextView.setText(manobraEscolhida);
        });

        btnEscolherManobra2.setOnClickListener(v -> {
            int index = random.nextInt(manobras2.length);
            String manobraEscolhida = manobras2[index];
            resultadoTextView.setText(manobraEscolhida);
        });

        btnEscolherManobra3.setOnClickListener(v -> {
            int index = random.nextInt(manobras3.length);
            String manobraEscolhida = manobras3[index];
            resultadoTextView.setText(manobraEscolhida);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configurarBotao(ImageButton botao) {
        botao.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_scale));
                clickSound.start();  // Tocar o som de clique
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.clearAnimation();
            }
            return false;
        });
    }

    @SuppressLint("SetTextI18n")
    private void atualizarPontuacao() {
        textPontos.setText("Pontos: " + pontos);
    }

    private void mostrarDialogoFinal(String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle("Fim de Jogo")
                .setMessage(mensagem)
                .setPositiveButton("Recomeçar", (dialog, which) -> restartGame())
                .setCancelable(false)
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void restartGame() {
        pontos = 0;
        manobras = 0;
        atualizarPontuacao();
        resultadoTextView.setText("Manobra:"); // Limpa a mensagem de resultado
    }

}
