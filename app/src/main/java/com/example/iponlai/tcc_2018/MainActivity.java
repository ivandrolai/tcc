package com.example.iponlai.tcc_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    EditText inserirponto;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inserirponto = (EditText) findViewById(R.id.inserirponto);
        Button ActivityBtn = (Button) findViewById(R.id.botaoG);


        //DEFININDO AS COORDENADAS DOS PONTOS
        Ponto unipdutra = new Ponto();
        unipdutra.nomedoponto = "Unip dutra";
        unipdutra.latitude = -23.253756998574232;
        unipdutra.longitude = -45.9417450428009;

        Ponto limoeiro = new Ponto();
        limoeiro.nomedoponto = "Limoeiro";
        limoeiro.latitude = -23.2506420609090332;
        limoeiro.longitude = -45.942635536193859;

        Ponto rodjacarei = new Ponto();
        rodjacarei.nomedoponto = "Rodoviaria Jacarei";
        rodjacarei.latitude = -23.277777031584147;
        rodjacarei.longitude = -45.95430850982666;

        Ponto tannevescentro = new Ponto();
        tannevescentro.nomedoponto = "Tancredo Neves Centro";
        tannevescentro.latitude = -23.181636426650826;
        tannevescentro.longitude = -45.81026315689087;

        Ponto tannevesbairro = new Ponto();
        tannevesbairro.nomedoponto = "Tancredo Neves Bairro";
        tannevesbairro.latitude = -23.181675877070973;
        tannevesbairro.longitude = -45.81053674221039;

        Ponto jdmotorspani = new Ponto();
        jdmotorspani.nomedoponto = "Jardim Motorama Spani";
        jdmotorspani.latitude = -23.17656695088593;
        jdmotorspani.longitude = -45.82826614379883;

        //CRIANDO DICIONARIO DE PONTOS
        final Map<String,Ponto> pontos = new HashMap<String, Ponto>();

        //POPULANDO O DICIONARIO
        pontos.put("unip dutra sentido jacarei", unipdutra);
        pontos.put("limoeiro", limoeiro);
        pontos.put("rodoviaria de jacarei", rodjacarei);
        pontos.put("tancredo neves sentido centro", tannevescentro);
        pontos.put("tancredo neves sentido bairro", tannevesbairro);
        pontos.put("jardim motorama spani", jdmotorspani);

        //CRIANDO SONS DE ALERTA
        //final MediaPlayer mp_error = MediaPlayer.create(this, R.raw.alerta);
        //final MediaPlayer mp_okay = MediaPlayer.create(this, R.raw.pontoexistente);
        final MediaPlayer mp_welcome = MediaPlayer.create(this, R.raw.welcome);

        //MENSAGEM INICIAL
        mp_welcome.start();

        //CONVERTE TEXTO EM VOZ
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        //ATIVIDADE QUE OCORRE QUANDO O BOTÃO FOR PRESSIONADO
        ActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //RETIRA TODOS OS ACENTOS DA SENTENÇA E TRANSFORMA A FRASE EM LETRAS MINUSCULAS
                String texto = StringUtils.semAcentos(inserirponto.getText().toString().toLowerCase());

                if (pontos.containsKey(texto)) {

                    String existe = "O ponto: " + texto + " existe! Aguarde um momento, gerando o melhor trajeto!";
                    Toast.makeText(getApplicationContext(), existe, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(existe, TextToSpeech.QUEUE_FLUSH, null);

                    Uri gmmIntenUri = Uri.parse("google.navigation:q=" + pontos.get(texto).latitude + "," + pontos.get(texto).longitude + "&mode=w");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntenUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);

                }
                else {

                    String naoexiste = "O ponto: " + texto + " não existe!!";
                    Toast.makeText(getApplicationContext(), naoexiste, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(naoexiste, TextToSpeech.QUEUE_FLUSH, null);

                }
            }
        });

    }

    /*public void onPause() {
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }*/



    public class Ponto {

        public String nomedoponto; //PontoX
        public double latitude;    //00,0000
        public double longitude;   //00,0000
    }
}

