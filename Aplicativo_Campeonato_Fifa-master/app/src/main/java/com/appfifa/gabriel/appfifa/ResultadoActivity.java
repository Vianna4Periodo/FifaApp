package com.appfifa.gabriel.appfifa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.dao.GolDao;
import com.appfifa.gabriel.appfifa.dao.JogoDao;
import com.appfifa.gabriel.appfifa.dao.PlayerDao;
import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Gol;
import com.appfifa.gabriel.appfifa.model.Jogo;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.List;

public class ResultadoActivity extends AppCompatActivity {

    private ListView listaResultados;
    private TextView goleador;
    private Campeonato campeonato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent intent = getIntent();
        campeonato = (Campeonato) intent.getSerializableExtra("campeonato");

        DAO dao = new DAO(this);
        PlayerDao playerDao = new PlayerDao(dao);
        GolDao golDao = new GolDao(dao);

        List<Gol> gols = golDao.buscaGoleador(campeonato);
        goleador = findViewById(R.id.goleador);
        goleador.setText("Goleador: "+gols.get(0).getJogador());

        List<Gol> golsPlayer = golDao.buscaPlayerArtilheiro(campeonato);
        Player player = golsPlayer.get(0).getPlayer();
        player.setPontuacao(player.getPontuacao()+5);
        playerDao.atualiza(player);

        List<Player>players=playerDao.buscaPlayerPorCampeonato(campeonato);

        listaResultados = findViewById(R.id.lista_resultados);
        ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        listaResultados.setAdapter(adapter);


    }
}
