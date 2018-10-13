package com.example.iponlai.tcc_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText inserirponto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inserirponto = (EditText) findViewById(R.id.inserirponto);

        //definindo os pontos do mapa

        Ponto pontounip = new Ponto();
        pontounip.nomedoponto = "Unip";
        pontounip.latitude = -23.253756998574232;
        pontounip.longitude = -45.9417450428009;

        Ponto pontocasa = new Ponto();
        pontocasa.nomedoponto = "Casa";
        pontocasa.latitude = -21.253756998574232;
        pontocasa.longitude = -45.9417450428009;

        Ponto pontosubway = new Ponto();
        pontosubway.nomedoponto = "subway";
        pontosubway.latitude = -23.3040886320065;
        pontosubway.longitude = -45.96221297979355;


        //Criando dicionario de pontos

        final Map<String,Ponto> pontos = new HashMap<String, Ponto>();

        //Populando o dicionario

        pontos.put("unip", pontounip);
        pontos.put("casa", pontocasa);
        pontos.put("subway", pontosubway);

        //Criando som de alerta
        final MediaPlayer mp_error = MediaPlayer.create(this, R.raw.alerta);
        final MediaPlayer mp_okay = MediaPlayer.create(this, R.raw.ponto_existente);
        final MediaPlayer mp_welcome = MediaPlayer.create(this, R.raw.welcome);

        //mensagem inicial
        mp_welcome.start();

        Button ActivityBtn = (Button) findViewById(R.id.botaoG);
        ActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = inserirponto.getText().toString().toLowerCase();

                if (pontos.containsKey(texto)) {

                    mp_okay.start();

                    Uri gmmIntenUri = Uri.parse("google.navigation:q=" + pontos.get(texto).latitude + "," + pontos.get(texto).longitude + "&mode=w");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else {
                    mp_error.start();
                }
            }
        });

    }

    public class Ponto {

        public String nomedoponto; //PontoX
        public double latitude;    //00,0000
        public double longitude;   //00,0000
    }
}
