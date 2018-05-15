package com.appfifa.gabriel.appfifa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.appfifa.gabriel.appfifa.dao.CampeonatoDao;
import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.dao.PlayerDao;
import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.List;

public class AddPlayerActivity extends AppCompatActivity {

    private EditText nomePlayer;
    private Spinner spinnerTimes;
    private ListView listaPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        listaPlayers = findViewById(R.id.lista_players);

        preencheSpinner();

        Button btnAddPlayer = findViewById(R.id.btn_add_player);
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Campeonato campeonato = (Campeonato) intent.getSerializableExtra("campeonato");

                Player player = new Player();
                player.setCampeonato(campeonato);

                nomePlayer = (EditText) findViewById(R.id.form_nome_player);
                player.setNome(nomePlayer.getText().toString());

                String itemSpinerTime = (String) spinnerTimes.getSelectedItem();
                player.setTime(itemSpinerTime);

                DAO dao = new DAO(AddPlayerActivity.this);
                PlayerDao pDao = new PlayerDao(dao);
                pDao.insere(player);
                dao.close();
                recreate();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
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
        DAO dao = new DAO(AddPlayerActivity.this);
        PlayerDao pDao = new PlayerDao(dao);
        List<Player> players = pDao.buscaPlayerPorCampeonato(campeonato);
        if(players.size() <= 2){
            Toast.makeText(AddPlayerActivity.this, "Adicione pelo menos  3 players", Toast.LENGTH_LONG).show();
            return super.onOptionsItemSelected(item);
        }else{
            switch (item.getItemId()){
                case R.id.menu_formulario_ok:
                    campeonato.setStatus("Começar Jogos");
                    CampeonatoDao cDao = new CampeonatoDao(dao);
                    cDao.altera(campeonato);
            }

            dao.close();
            Toast.makeText(AddPlayerActivity.this, "Agora que comecem os jogos", Toast.LENGTH_LONG).show();
            finish();
            return super.onOptionsItemSelected(item);
        }

    }

    public void preencheSpinner(){
        spinnerTimes = (Spinner)findViewById(R.id.times_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddPlayerActivity.this, R.array.times, android.R.layout.simple_spinner_item);
        spinnerTimes.setAdapter(adapter);
    }

    private void carregaLista(){
        DAO dao = new DAO(this);
        PlayerDao pDao = new PlayerDao(dao);
        Intent intent = getIntent();
        Campeonato campeonato = (Campeonato) intent.getSerializableExtra("campeonato");
        List<Player> players = pDao.buscaPlayerPorCampeonato(campeonato);
        dao.close();
        listaPlayers = findViewById(R.id.lista_players);

        ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        listaPlayers.setAdapter(adapter);
    }
}
