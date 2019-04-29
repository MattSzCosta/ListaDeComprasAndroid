package com.example.matt_.listadecompras.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.matt_.listadecompras.CadastroHelper;
import com.example.matt_.listadecompras.MainActivity;
import com.example.matt_.listadecompras.Model.ItensLista;
import com.example.matt_.listadecompras.Model.Lista;
import com.example.matt_.listadecompras.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {
    private CadastroHelper helper;

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

    public Long insere(Produto produto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getProduto(produto);

        Long idProduct = db.insert("Produtos", null, dados);
        Log.e("ID PRODUTO", idProduct.toString());
        return idProduct;
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
        return id;
    }

    public void insertProductAtList(ItensLista itensLista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getItemLista(itensLista);

        db.insert("ItensLista", null, dados);
    }

    public List<Produto> getListItems (Long idlist) {
        Log.e("ID LISTA", idlist.toString());
        //String sql = "SELECT * from ItensLista WHERE idLista =" +idlist+";";
        String sql = "SELECT * from ItensLista";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<ItensLista> listaIdProdutos = new ArrayList<>();
        while(c.moveToNext()){
            ItensLista itens = new ItensLista();
            itens.setIdProduto(c.getLong(c.getColumnIndex("idProduto")));
            listaIdProdutos.add(itens);
        }
        Log.e("ID PRODUTOS DA LISTA:", listaIdProdutos.toString());
        c.close();
        List<Produto> listaProdutos = new ArrayList<>();
        for (ItensLista item: listaIdProdutos){
            String sqlProduto = "SELECT * from Produtos WHERE id =" +item.getIdProduto()+";";
            SQLiteDatabase db2 = getReadableDatabase();
            Cursor c2 = db2.rawQuery(sqlProduto, null);

            while(c2.moveToNext()){
                Produto produto = new Produto();
                produto.setId(c.getLong(c.getColumnIndex("id")));
                produto.setNome(c.getString(c.getColumnIndex("nome")));
                produto.setMarca(c.getString(c.getColumnIndex("marca")));
                produto.setTipoProduto(c.getString(c.getColumnIndex("tipoProduto")));
                produto.setQuantidade(c.getLong(c.getColumnIndex("quantidade")));
                produto.setPreco(c.getDouble(c.getColumnIndex("preco")));
                listaProdutos.add(produto);
            }
            c2.close();
        }
        Log.e("PRODUTOS DA LISTA:", listaProdutos.toString());

        return listaProdutos;
    }

    public List<Lista> getLista(){
        String sql = "SELECT * from Lista";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Lista> lista = new ArrayList<>();
        while(c.moveToNext()){
            Lista item = new Lista();
            item.setId(c.getLong(c.getColumnIndex("id")));
            item.setNome(c.getString(c.getColumnIndex("nomeLista")));
            lista.add(item);
        }
        c.close();
        return lista;
    }

}
