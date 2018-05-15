package com.appfifa.gabriel.appfifa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.appfifa.gabriel.appfifa.dao.CampeonatoDao;
import com.appfifa.gabriel.appfifa.dao.DAO;
import com.appfifa.gabriel.appfifa.model.Campeonato;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaCampeonatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaCampeonatos = (ListView) findViewById(R.id.lista_campeonatos);

        Button btnFlutuante;
        btnFlutuante = (Button) findViewById(R.id.btn_novo_campeonato);


        listaCampeonatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campeonato campeonato = (Campeonato) listaCampeonatos.getItemAtPosition(position);
                if(campeonato.getStatus().equals("Add jogadores")){
                    Intent intentVaiParaPlayers =  new Intent(MainActivity.this, AddPlayerActivity.class);
                    intentVaiParaPlayers.putExtra("campeonato", campeonato);
                    startActivity(intentVaiParaPlayers);
                }else if(campeonato.getStatus().equals("Começar Jogos") || campeonato.getStatus().equals("Continuar Jogos")){
                    Intent intentVaiParaJogos =  new Intent(MainActivity.this, AdmJogosActivity.class);
                    intentVaiParaJogos.putExtra("campeonato", campeonato);
                    startActivity(intentVaiParaJogos);
                }else if(campeonato.getStatus().equals("Ver resultados")){
                    Intent intentVaiParaResultado =  new Intent(MainActivity.this, ResultadoActivity.class);
                    intentVaiParaResultado.putExtra("campeonato", campeonato);
                    startActivity(intentVaiParaResultado);
                }
            }
        });

        btnFlutuante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiParaFormularioNovoCampeonato = new Intent(MainActivity.this, FormularioCampeonatoActivity.class);
                startActivity(intentVaiParaFormularioNovoCampeonato);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista(){
        DAO dao = new DAO(this);
        CampeonatoDao cDao = new CampeonatoDao(dao);
        List<Campeonato> campeonatos = cDao.buscaCampeonatos();
        dao.close();

        ArrayAdapter<Campeonato> adapter = new ArrayAdapter<Campeonato>(this, android.R.layout.simple_list_item_1, campeonatos);
        listaCampeonatos.setAdapter(adapter);
    }
}
