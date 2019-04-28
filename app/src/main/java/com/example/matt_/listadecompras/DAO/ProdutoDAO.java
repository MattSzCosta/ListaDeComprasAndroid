package com.example.matt_.listadecompras.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.matt_.listadecompras.MainActivity;
import com.example.matt_.listadecompras.Model.ItensLista;
import com.example.matt_.listadecompras.Model.Lista;
import com.example.matt_.listadecompras.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {

    public ProdutoDAO(Context context) {
        super(context, "ListaCompra", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Produtos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, marca TEXT NOT NULL, tipoProduto TEXT, " +
                "quantidade INTEGER NOT NULL, preco DOUBLE NOT NULL)";
        String sql2 = "CREATE TABLE Lista (id INTEGER PRIMARY KEY AUTOINCREMENT, nomeLista TEXT NOT NULL)";
        String sql3 = "CREATE TABLE ItensLista (id INTEGER PRIMARY KEY AUTOINCREMENT, idLista INTEGER NOT NULL, idProduto INTEGER NOT NULL)";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Produtos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getProduto(produto);

        db.insert("Produtos", null, dados);
    }

    @NonNull
    private ContentValues getProduto(Produto produto){
        ContentValues dados = new ContentValues();
        dados.put("nome", produto.getNome());
        dados.put("marca", produto.getMarca());
        dados.put("tipoProduto", produto.getTipoProduto());
        dados.put("quantidade", produto.getQuantidade());
        dados.put("preco", produto.getPreco());
        return dados;
    }

    @NonNull
    private ContentValues getLista(Lista lista){
        ContentValues dados = new ContentValues();
        dados.put("id", lista.getId());
        dados.put("nomeLista", lista.getNome());
        return dados;
    }
    @NonNull
    private ContentValues getItemLista(ItensLista itensLista) {
        ContentValues dados = new ContentValues();
        dados.put("idLista", itensLista.getIdLista());
        dados.put("idProduto", itensLista.getIdProduto());
        return dados;
    }

    public List<Produto> getProdutos() {
        String sql = "SELECT * from Produtos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Produto> produtos = new ArrayList<>();
        while(c.moveToNext()){
            Produto produto = new Produto();
            produto.setId(c.getLong(c.getColumnIndex("id")));
            produto.setNome(c.getString(c.getColumnIndex("nome")));
            produto.setMarca(c.getString(c.getColumnIndex("marca")));
            produto.setTipoProduto(c.getString(c.getColumnIndex("tipoProduto")));
            produto.setQuantidade(c.getLong(c.getColumnIndex("quantidade")));
            produto.setPreco(c.getDouble(c.getColumnIndex("preco")));
            produtos.add(produto);
        }
        c.close();
        return produtos;
    }

    public void deleta(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {produto.getId().toString()};
        db.delete("Produtos", "id = ?", params);
    }

    public void altera(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getProduto(produto);

        String[] params = {produto.getId().toString()};
        db.update("Produtos", dados, "id = ?", params);
    }

    public long newLista(String nomeDaLista) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nomeLista",nomeDaLista);
        Long id = db.insert("Lista", null, dados);
        Log.e("RETORNO: ", id.toString());
        return id;
    }

    public void insertProductAtList(ItensLista itensLista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getItemLista(itensLista);

        db.insert("ItensLista", null, dados);
    }

    public void getListItems (Integer idlist) {
        String sql = "SELECT * from Itens Lista WHERE id =" +idlist+";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
    }

}
