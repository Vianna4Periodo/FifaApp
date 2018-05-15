package com.appfifa.gabriel.appfifa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.appfifa.gabriel.appfifa.dao.CampeonatoDao;
import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.dao.JogoDao;
import com.appfifa.gabriel.appfifa.dao.PlayerDao;
import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Jogo;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.List;

public class AdmJogosActivity extends AppCompatActivity {

    private ListView listaJogos;
    private Campeonato campeonato;
    private static boolean criado = false;
    private Jogo jogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_jogos);

        Intent intent = getIntent();
        campeonato = (Campeonato) intent.getSerializableExtra("campeonato");

        if(campeonato.getStatus().equals("Começar Jogos")){
            criarJogos(campeonato);
            campeonato.setStatus("Continuar Jogos");
            DAO dao = new DAO(this);
            CampeonatoDao cDao = new CampeonatoDao(dao);
            cDao.altera(campeonato);
            dao.close();
        }

        listaJogos = findViewById(R.id.lista_jogos);
        listaJogos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jogo jogo = (Jogo) listaJogos.getItemAtPosition(position);
                if(jogo.getStatus().equals("Em aberto")){
                    Intent intentVaiParaJogo =  new Intent(AdmJogosActivity.this, JogoActivity.class);
                    intentVaiParaJogo.putExtra("jogo", jogo);
                    startActivity(intentVaiParaJogo);
                }else {
                    Toast.makeText(AdmJogosActivity.this,"Jogo já finalizado", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaJogos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        Campeonato campeonato = (Campeonato) intent.getSerializableExtra("campeonato");
        DAO dao = new DAO(AdmJogosActivity.this);
        CampeonatoDao cDao = new CampeonatoDao(dao);
            switch (item.getItemId()){
                case R.id.menu_formulario_ok:
                    campeonato.setStatus("Ver resultados");
                    cDao.altera(campeonato);
            }

            dao.close();
            Toast.makeText(AdmJogosActivity.this, "Veja os resultados", Toast.LENGTH_LONG).show();
            finish();
            return super.onOptionsItemSelected(item);
    }



    private void carregaJogos() {
        Intent intent = getIntent();
        campeonato = (Campeonato) intent.getSerializableExtra("campeonato");
        DAO dao = new DAO(this);
        JogoDao jDao = new JogoDao(dao);
        List<Jogo> jogos = jDao.buscaJogoPorCampeonato(campeonato);
        dao.close();

        listaJogos = findViewById(R.id.lista_jogos);
        ArrayAdapter<Jogo> adapter = new ArrayAdapter<Jogo>(this, android.R.layout.simple_list_item_1, jogos);
        listaJogos.setAdapter(adapter);
    }

    private void criarJogos(Campeonato campeonato) {
        DAO dao = new DAO(AdmJogosActivity.this);
        PlayerDao pDao = new PlayerDao(dao);
        List<Player> players = pDao.buscaPlayerPorCampeonato(campeonato);

        for (int i=0; i < players.size(); i++){
            for (int j=0; j < players.size(); j++){
                Jogo jogo = new Jogo();
                if(i != j){
                    jogo.setCampeonato(campeonato);
                    jogo.setPlayer1(players.get(i));
                    jogo.setPlayer2(players.get(j));
                    jogo.setStatus("Em aberto");

                    JogoDao jDao = new JogoDao(dao);
                    jDao.insere(jogo);
                }
            }
        }
    }
}
