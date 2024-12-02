package DAO;

import Objetos.Bes_Produto;
import Util.Bes_Conexao;
import Util.Bes_ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Bes_ProdutoDAO 
{
    Connection conn;
    Bes_ManipulaData md;

    /*O método construtor é primeiro método a
    ser executado em uma classe. Esse método
    tem o mesmo nome da classe*/
    public Bes_ProdutoDAO() 
    {
        /*Na variável "conn" é armazenada a 
        conexão estabelecida pelo método 
        "conectar()" da classe "Conexão".*/
        conn = new Bes_Conexao().conectar();
        md = new Bes_ManipulaData();
    }
    
    public Bes_Produto salvar(Bes_Produto p)
    {
        try
        {
            /*Estabelece um espaço para 
            "preparar" o SQL que será executado
            no banco. Cada simbolo "?" será 
            substituido por valores contidos
            em variáveis de uma classe java.
            Através dos métodos "set" da classe
            PreparedStatement são atribuidos os
            valores para os espaços referentes
            aos simbolos "?"*/
            PreparedStatement stmt = conn.prepareStatement
            ("INSERT INTO prodtuo(nome, marca, modelo, valor) values(?,?,?,?)", 
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNome()); 
            stmt.setString(2, p.getMarca());  
            stmt.setString(3, p.getModelo());  
            stmt.setDouble(4, p.getValor()); 
            stmt.execute();// Executa o SQL no banco
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                p.setIdproduto(rs.getInt("idproduto"));
            }
            else{
                p.setIdproduto(-1);
            }
        }
        catch(SQLException ex)
        {  
            ex.printStackTrace();
        }
        return p;
    }
    
    public void editar(Bes_Produto p){
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("UPDATE produto SET nome = ?, marca= ? , modelo = ?, valor = ?"
                    + "WHERE idpessoa = ?");
            stmt.setString(1, p.getNome());  
            stmt.setString(2, p.getMarca());
            stmt.setString(3, p.getModelo());
            stmt.setDouble(4, p.getValor()); 
            stmt.setInt(5, p.getIdproduto());
            stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
    }
    
    public int excluir(Bes_Produto p)
    {
        int verif = 0;
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("DELETE FROM produto WHERE idproduto = ?");
            stmt.setInt(1, p.getIdproduto());
            verif = stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
        return verif;
    }
    
    /*O método "getPessoas" retorna um valor do
    tipo "List<Pessoa>"*/
    public List<Bes_Produto> getBes_Produto()
    {
        /*É criada uma variável "lstP" do tipo "List" que pode 
        armazenar vários objetos do tipo "Pessoa".*/
        List<Bes_Produto> lstP = new ArrayList<>();
        /*A variável "rs" do tipo "ResultSet"
        armazena o retorno das consultas realizadas no banco dados. 
        Essa variável se adapta a qualquer retorno do banco de dados.*/
        ResultSet rs;
        /*Através do "try-catch" é possível tratar exceções no banco 
        dados. Caso a instrução SQL esteja incorreta ou as 
        informações de acesso ao banco estejam erradas, 
        será retornada uma exceção informando o erro.*/
        try
        {
            
            PreparedStatement ppStmt = conn.prepareStatement
        ("SELECT * FROM produto");
            /*Através do "executeQuery" a instrução SQL de consulta 
            é executada e os valores são retornados para aplicação 
            em um formato de "ResultSet"*/
            rs = ppStmt.executeQuery();
            /*No comando "while" o "ResultSet" é percorrido 
            enquanto existir uma próxima informação não lida em seu 
            interior.*/
            while(rs.next())
            {
                /*Na linha abaixo, através do método "getPessoa" os 
                dados de pessoa são extraidos do "ResultSet" e 
                atribuídos para uma variável do tipo pessoa, 
                constituindo, dessa forma, uma pessoa "x". 
                Cada pessoa extraida pelo método "getPessoa" é 
                adicionada em uma lista de pessoas, no caso, 
                na lista "lstP".*/
                lstP.add(getBes_Produto(rs));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public Bes_Produto getBes_Produto(int idproduto){
        Bes_Produto p = new Bes_Produto();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM prouto WHERE idproduto = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ppStmt.setInt(1, idproduto);
            rs = ppStmt.executeQuery();
            rs.first();
            p = getBes_Produto(rs);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }
    
    public List<Bes_Produto> getBes_Produto(String nome){
        List<Bes_Produto> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM produto WHERE nome ILIKE ?");
            ppStmt.setString(1, nome+ "%");
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getBes_Produto(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
   /* public List<Bes_Produto> getBes_Cliente(String nome, String dataInicio, 
            String dataFim){
        List<Bes_Cliente> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM cliente WHERE nome ILIKE ? AND "
                        + "data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1, nome+ "%");
            ppStmt.setDate(2, md.string2Date(dataInicio));
            ppStmt.setDate(3, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getBes_Cliente(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }    
    public List<Bes_Cliente> getBes_Cliente(String dataInicio, String dataFim){
        List<Bes_Cliente> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM cliente WHERE data_nascimento "
                        + "BETWEEN ? AND ?");
            ppStmt.setDate(1, md.string2Date(dataInicio));
            ppStmt.setDate(2, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getBes_Cliente(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
*/
    
    public Bes_Produto getBes_Produto(ResultSet rs) throws SQLException
    {
        Bes_Produto p = new Bes_Produto();
        
        p.setIdproduto(rs.getInt("idproduto"));
        p.setNome(rs.getString("nome"));
        p.setMarca(rs.getString("marca"));
        p.setModelo(rs.getString("modelo"));
        p.setValor(rs.getDouble("valor")); 
        
        
        return p;
    }
}
