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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.appfifa.gabriel.appfifa.dao.CampeonatoDao;
import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.dao.GolDao;
import com.appfifa.gabriel.appfifa.dao.JogoDao;
import com.appfifa.gabriel.appfifa.dao.PlayerDao;
import com.appfifa.gabriel.appfifa.model.Campeonato;
import com.appfifa.gabriel.appfifa.model.Gol;
import com.appfifa.gabriel.appfifa.model.Jogo;
import com.appfifa.gabriel.appfifa.model.Player;

import java.util.List;

public class JogoActivity extends AppCompatActivity {

    private Jogo jogo;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radioSelected;
    private RadioGroup radioGroup;
    private Spinner jogadores_spinner;
    private Button btn_salvar_gol;
    private ListView listaGols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        Intent intent = getIntent();
        jogo = (Jogo) intent.getSerializableExtra("jogo");

        carregaSpinner(null);
        carrgeaNomeDoRadio(jogo);

        btn_salvar_gol = findViewById(R.id.btn_add_gol);
        btn_salvar_gol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gol gol = new Gol();
                gol.setCampeonato(jogo.getCampeonato());
                gol.setJogo(jogo);

                DAO dao = new DAO(JogoActivity.this);
                PlayerDao pDao = new PlayerDao(dao);

                radioGroup = findViewById(R.id.radioGroup);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioSelected = findViewById(radioId);
                String playerToString = radioSelected.getText().toString();
                char idChar = playerToString.charAt(0);
                long id = idChar - '0';
                Player player = pDao.buscaPlayerPorId(jogo.getCampeonato(), id );
                gol.setPlayer(player);

                String itemSpinerJogador = (String) jogadores_spinner.getSelectedItem();
                gol.setJogador(itemSpinerJogador);

                GolDao golDao = new GolDao(dao);
                golDao.insere(gol);

                JogoDao jDao = new JogoDao(dao);
                if(player.getId().equals(jogo.getPlayer1().getId())){
                    jogo.setPlacarPlayer1(jogo.getPlacarPlayer1()+1);
                }else {
                    jogo.setPlacarPlayer2(jogo.getPlacarPlayer2()+1);
                }
                jDao.atualiza(jogo);
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
        jogo = (Jogo) intent.getSerializableExtra("jogo");
        DAO dao = new DAO(JogoActivity.this);
        JogoDao jDao = new JogoDao(dao);
        PlayerDao playerDao = new PlayerDao(dao);
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                if(jogo.getPlacarPlayer1() > jogo.getPlacarPlayer2()){
                    jogo.getPlayer1().setPontuacao(jogo.getPlayer1().getPontuacao()+3);
                    for (int i=0; i < (jogo.getPlacarPlayer1()-jogo.getPlacarPlayer2()); i++){
                        jogo.getPlayer1().setPontuacao(jogo.getPlayer1().getPontuacao()+1);
                    }
                    playerDao.atualiza(jogo.getPlayer1());
                }else if(jogo.getPlacarPlayer1() < jogo.getPlacarPlayer2()){
                    jogo.getPlayer2().setPontuacao(jogo.getPlayer2().getPontuacao()+3);
                    for (int i=0; i < (jogo.getPlacarPlayer2()-jogo.getPlacarPlayer1()); i++){
                        jogo.getPlayer2().setPontuacao(jogo.getPlayer2().getPontuacao()+1);
                    }
                    playerDao.atualiza(jogo.getPlayer2());
                }else if(jogo.getPlacarPlayer1() == jogo.getPlacarPlayer2()){
                    jogo.getPlayer1().setPontuacao(jogo.getPlayer1().getPontuacao()+1);
                    jogo.getPlayer2().setPontuacao(jogo.getPlayer2().getPontuacao()+1);
                    playerDao.atualiza(jogo.getPlayer1());
                    playerDao.atualiza(jogo.getPlayer2());
                }

                jogo.setStatus("Finalizado");
                jDao.atualiza(jogo);
        }

        dao.close();
        Toast.makeText(JogoActivity.this, "Jogo finalizado", Toast.LENGTH_SHORT).show();
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void carregaLista() {
        DAO dao = new DAO(this);
        GolDao gDao = new GolDao(dao);
        Intent intent = getIntent();
        Jogo jogo = (Jogo) intent.getSerializableExtra("jogo");
        List<Gol> gols = gDao.buscaPlayerPorJogo(jogo);
        dao.close();
        listaGols = findViewById(R.id.lista_gols);

        ArrayAdapter<Gol> adapter = new ArrayAdapter<Gol>(this, android.R.layout.simple_list_item_1, gols);
        listaGols.setAdapter(adapter);
    }

    public void carregaSpinner(View v) {
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioSelected = findViewById(radioId);
        String time = radioSelected.getText().toString();

        jogadores_spinner = findViewById(R.id.jogadores_spinner);

        if(time.contains("Corinthians")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoCorinthians, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("São Paulo")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoSaoPaulo, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Palmeiras")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoPalmeiras, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Grêmio")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoGremio, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Internacional")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoInternacional, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Atlético Paranaense")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoAtleticoParanaense, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Cruzeiro")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoCruzeiro, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Flamengo")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoFlamengo, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Fluminense")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoFluminense, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Atlético Mineiro")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoAtleticoMineiro, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
        if(time.contains("Santos")){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JogoActivity.this, R.array.escalacaoSantos, android.R.layout.simple_spinner_item);
            jogadores_spinner.setAdapter(adapter);
        }
    }

    private void carrgeaNomeDoRadio(Jogo jogo) {
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);

        radio1.setText(jogo.getPlayer1().toString());
        radio2.setText(jogo.getPlayer2().toString());
    }
}
