package com.example.matt_.listadecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.matt_.listadecompras.DAO.ProdutoDAO;
import com.example.matt_.listadecompras.Model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private CadastroHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        helper = new CadastroHelper(this);

        Intent intent = getIntent();
        Produto produto = (Produto) intent.getSerializableExtra("produto");

        if(produto != null){
            helper.preencheCadastro(produto);
        }

        Button botaoSalvar = (Button) findViewById(R.id.botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto produto = helper.getProduto();
                ProdutoDAO dao = new ProdutoDAO(CadastroProdutoActivity.this);

                if(produto.getId() != null){
                    dao.altera(produto);
                }else{
                    dao.insere(produto);
                }

                dao.close();
                Toast.makeText(CadastroProdutoActivity.this, "Produto " + produto.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}