package com.example.appskate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true); // Faz a música tocar em loop
        mediaPlayer.start(); // Começa a tocar a música

        ImageView share = findViewById(R.id.share);
        share.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Jogo Brave Game of Skate: https://play.google.com/store/apps/details?id=com.appsorteioo.sorteio");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        });



        ImageView info = findViewById(R.id.info);
        info.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Sobre o jogo")
                    .setMessage("Jogo criado e desenvolvido por BraveDevs.\n\nNo Brave Game of Skate, você começa escolhendo o estilo de manobra, chão, pista ou corrimão. Cada jogador tem 10 tentativas para acertar suas manobras," +
                            " cada manobra bem-sucedida, você ganha um ponto; se errar, perde um ponto.\n\nPrepare-se para testar seu domínio do skate com o Brave Game of Skate!\n\nTodos os direitos reservados. \n\n" +
                            "Nossas políticas de privacidade: https://www.blogger.com/blog/post/edit/preview/8028954219898156730/4974715448008899189")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

            // Torna o link clicável
            TextView messageView = dialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setMovementMethod(LinkMovementMethod.getInstance());
                Linkify.addLinks(messageView, Linkify.WEB_URLS);
            }
        });

    }



    @Override
    protected void onPause() {
        super.onPause();
        // Para a música quando a Activity não está visível
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
        // Para a música quando a Activity for destruída
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void frame_regras(View view) {
        Intent intent = new Intent(MainActivity.this, frame_regras.class);
        startActivity(intent);
    }

}
