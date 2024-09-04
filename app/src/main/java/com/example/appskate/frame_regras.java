package com.example.appskate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class frame_regras extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private static final String TAG = "frame_regras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_regras);

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_m);
            mediaPlayer.setLooping(true); // Faz a música tocar em loop
            mediaPlayer.start(); // Começa a tocar a música
        } catch (Exception e) {
            Log.e(TAG, "Erro ao iniciar a música de fundo", e);
            // Adicionar tratamento de erros, como mostrar uma mensagem ao usuário
        }

        // Configura o botão para avançar para MainActivity
        Button btnAvancar = findViewById(R.id.btn_avancar);
        btnAvancar.setOnClickListener(view -> {
            Intent intent = new Intent(frame_regras.this, tela_jogo.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausa a música quando a Activity não está visível
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reinicia a música quando a Activity se torna visível
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Para a música e libera recursos quando a Activity for destruída
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "Erro ao liberar recursos do MediaPlayer", e);
            } finally {
                mediaPlayer = null;
            }
        }
    }
}
