package br.com.pch.livraria;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.pch.livraria.adapter.AndroidAdapter;
import br.com.pch.livraria.listener.OnItemClickListener;
import br.com.pch.livraria.model.Livro;
import br.com.pch.livraria.dao.LivroDAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // CONNECTION_TIMEOUT e READ_TIMEOUT são em milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private AndroidAdapter mAdapter;

    //private TextView tvLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tvLivros = (TextView) findViewById(R.id.tvLivros);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chama a tela de cadastro e aguarda um retorno que irá chamar o método onActivityResult
                startActivityForResult(new Intent(MainActivity.this, NovoLivroActivity.class),
                        NovoLivroActivity.CODE_NOVO_LIVRO);
            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        carregaLivros();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Cancelado",
                    Toast.LENGTH_LONG).show();
        } else {
            if(requestCode == NovoLivroActivity.CODE_NOVO_LIVRO) {
                carregaLivros();
            } else {
                if (requestCode == AlteraActivity.CODE_ALTERA_LIVRO){
                    carregaLivros();
                }
            }
        }

    }

    private void carregaLivros(){
       // tvLivros.setText("");
        LivroDAO livroDAO = new LivroDAO(this);
        StringBuilder sb = new StringBuilder();
        List<Livro> livros = livroDAO.getAll();

        setUpRecyclerView(livros);

        //for (Livro l : livros){
        //    sb = new StringBuilder(tvLivros.getText());
        //    sb.append("\n");
        //    sb.append(l.getTitulo());
        //    sb.append(l.getAutor());
        //    sb.append(" - ");
        //    sb.append(l.getCategoria().getNome());
        //    tvLivros.setText(sb.toString());
        //}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpRecyclerView(List<Livro> data) {

        RecyclerView rcLista = (RecyclerView) findViewById(R.id.rcLista);
        mAdapter = new AndroidAdapter(MainActivity.this, data);
        rcLista.setAdapter(mAdapter);
        rcLista.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Livro livro = mAdapter.getItem(position);
                Intent i = new Intent(MainActivity.this, AlteraActivity.class);
                i.putExtra(Livro.TAG_ID, livro.getId());
                i.putExtra(Livro.TAG_TITULO, livro.getTitulo());
                i.putExtra(Livro.TAG_AUTOR, livro.getAutor());
                String idCategoria = Integer.toString(livro.getCategoria().getId());
                i.putExtra(Livro.TAG_CATEGORIA_ID, idCategoria);

                startActivityForResult(i, AlteraActivity.CODE_ALTERA_LIVRO);
                //startActivity(i);

            }
        });
    }
}
