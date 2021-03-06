package br.com.pch.livraria;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.pch.livraria.dao.CategoriaDAO;
import br.com.pch.livraria.dao.LivroDAO;
import br.com.pch.livraria.model.Categoria;
import br.com.pch.livraria.model.Livro;

public class AlteraActivity extends AppCompatActivity {

    public final static int CODE_ALTERA_LIVRO = 1003;
    private TextInputLayout tilTitulo;
    private TextInputLayout tilAutor;
    private Spinner spCategoria;
    private EditText etTitulo;
    private EditText etAutor;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera);

        tilTitulo = (TextInputLayout) findViewById(R.id.tilTitulo);
        etTitulo = (EditText) findViewById(R.id.etTitulo);
        tilAutor = (TextInputLayout) findViewById(R.id.tilAutor);
        etAutor = (EditText) findViewById(R.id.etAutor);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);

        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        categorias = categoriaDAO.getAll();

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getApplicationContext(),
                R.layout.categoria_spinner_item, categorias);
        adapter.setDropDownViewResource(R.layout.categoria_spinner_item);
        spCategoria.setAdapter(adapter);

        if(getIntent() != null) {
            etTitulo.setText(getIntent().getStringExtra(Livro.TAG_TITULO));
            etAutor.setText(getIntent().getStringExtra(Livro.TAG_AUTOR));
            String idCategoria = getIntent().getStringExtra(Livro.TAG_CATEGORIA_ID);

            int posicaoArray = 0;

            for(int i=0; (i <= categorias.size()-1) ; i++){

                if(categorias.get(i).getId() == Integer.parseInt(idCategoria)){

                    posicaoArray = i;
                    break;
                }else{
                    posicaoArray = 0;
                }
            }
            spCategoria.setSelection(posicaoArray);
        }

    }


    public void salvar(View v) {
        LivroDAO livroDAO = new LivroDAO(this);
        Livro livro = new Livro();
        livro.setTitulo(tilTitulo.getEditText().getText().toString());
        livro.setAutor(tilAutor.getEditText().getText().toString());
        livro.setCategoria((Categoria) spCategoria.getSelectedItem());
        livroDAO.update(livro);

        retornaParaTelaAnterior();
    }

    public void excluir(View v) {
        LivroDAO livroDAO = new LivroDAO(this);
        Livro livro = livroDAO.getBy(1);
        livro.setTitulo(tilTitulo.getEditText().getText().toString());
        livro.setAutor(tilAutor.getEditText().getText().toString());
        livro.setCategoria((Categoria) spCategoria.getSelectedItem());
        livroDAO.update(livro);

        retornaParaTelaAnterior();
    }

    //retorna para tela de lista
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_ALTERA_LIVRO, intentMessage);
        finish();
    }

}
