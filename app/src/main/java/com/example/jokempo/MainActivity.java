package com.example.jokempo;

import static com.example.jokempo.R.id.btnPedra;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView jogador1;
    ImageView jogador2;

    ImageButton btnPedra;
    ImageButton btnPapel;
    ImageButton btnTesoura;

    Animation some;
    Animation aparece;

    int jogada1 = 0;
    int jogada2 = 0;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alex_play);

            jogador1 = findViewById(R.id.jogador1);
            jogador2 = findViewById(R.id.jogador2);

            btnPedra = findViewById(R.id.btnPedra);
            btnPapel = findViewById(R.id.btnPapel);
            btnTesoura = findViewById(R.id.btnTesoura);

            jogador1.setScaleX(-1);

            some = new AlphaAnimation(1,0);
            aparece = new AlphaAnimation(0,1);

            some.setDuration(1500);
            aparece.setDuration(100);

            some.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    jogador2.setVisibility((View.INVISIBLE));
                    jogador2.startAnimation(aparece);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationStart(Animation animation) {
                    jogador2.setVisibility(View.VISIBLE);
                }
            });

            aparece.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    verificaJogada();
                    jogador2.setVisibility((View.VISIBLE));
                    ativaDesativaButton(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationStart(Animation animation) {
                    sorteiaJogadaInimigo();
                    jogador2.setVisibility(View.INVISIBLE);
                }
            });

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void jogarPedra(View view){
        mudarImagem(R.drawable.pedra);
        jogada1=1;
    }

    public void jogarPapel(View view){
        mudarImagem(R.drawable.papel);
        jogada1=2;
    }

    public void jogarTesoura(View view){
        mudarImagem(R.drawable.tesoura);
        jogada1 = 3;
    }

    public void mudarImagem(@DrawableRes int resId){
        tocaSom();
        ativaDesativaButton(false);
        jogador1.setImageResource(resId);

        jogador2.setImageResource(R.drawable.interrogacao);
        jogador2.startAnimation((some));
    }

    public void sorteiaJogadaInimigo(){
        Random r = new Random();
        int numRandom = r.nextInt(3);

        switch (numRandom){
            case 0:
                jogador2.setImageResource(R.drawable.pedra);
                jogada2 = 1;
                break;
            case 1:
                jogador2.setImageResource(R.drawable.papel);
                jogada2 = 2;
                break;
            case 2:
                jogador2.setImageResource(R.drawable.tesoura);
                jogada2 = 3;
                break;
        }
    }

    public void verificaJogada(){
        if (jogada1 == jogada2){
            Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
        }else if((jogada1==1 && jogada2==3) || (jogada1==2 && jogada2==1) || (jogada1==3 && jogada2==2)){
            Toast.makeText(this, "Jogador 1 venceu!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Jogador 2 venceu!", Toast.LENGTH_SHORT).show();
        }
    }

    public void tocaSom(){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void ativaDesativaButton(boolean status){
        btnPedra.setEnabled(status);
        btnPapel.setEnabled(status);
        btnTesoura.setEnabled(status);
    }





}