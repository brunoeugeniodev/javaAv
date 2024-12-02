package DAO;

import Objetos.Bes_Cliente;
import Util.Bes_Conexao;
import Util.Bes_ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Bes_ClienteDAO 
{
    Connection conn;
    Bes_ManipulaData md;

    /*O método construtor é primeiro método a
    ser executado em uma classe. Esse método
    tem o mesmo nome da classe*/
    public Bes_ClienteDAO() 
    {
        /*Na variável "conn" é armazenada a 
        conexão estabelecida pelo método 
        "conectar()" da classe "Conexão".*/
        conn = new Bes_Conexao().conectar();
        md = new Bes_ManipulaData();
    }
    
    public Bes_Cliente salvar(Bes_Cliente c)
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
            ("INSERT INTO cliente(nome, data_nascimento, cpf) values(?,?,?)", 
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getNome());            
            stmt.setDate(2, md.string2Date(c.getData_nascimento()));
            stmt.setString(3, c.getCpf()); 
            stmt.execute();// Executa o SQL no banco
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                c.setIdcliente(rs.getInt("idcliente"));
            }
            else{
                c.setIdcliente(-1);
            }
        }
        catch(SQLException ex)
        {  
            ex.printStackTrace();
        }
        return c;
    }
    
    public void editar(Bes_Cliente c){
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("UPDATE cliente SET nome = ?, data_nascimento = ? , cpf = ?"
                    + "WHERE idcliente = ?");
            stmt.setString(1, c.getNome());            
            stmt.setDate(2, md.string2Date(c.getData_nascimento()));
            stmt.setString(3, c.getCpf());
            stmt.setInt(4, c.getIdcliente());
            stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
    }
    
    public int excluir(Bes_Cliente c)
    {
        int verif = 0;
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("DELETE FROM cliente WHERE idcliente = ?");
            stmt.setInt(1, c.getIdcliente());
            verif = stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
        return verif;
    }
    
    /*O método "getPessoas" retorna um valor do
    tipo "List<Pessoa>"*/
    public List<Bes_Cliente> getBes_Cliente()
    {
        /*É criada uma variável "lstP" do tipo "List" que pode 
        armazenar vários objetos do tipo "Pessoa".*/
        List<Bes_Cliente> lstP = new ArrayList<>();
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
        ("SELECT * FROM cliente");
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
                lstP.add(getBes_Cliente(rs));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public Bes_Cliente getBes_Cliente(int idcliente){
        Bes_Cliente c = new Bes_Cliente();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM cliente WHERE idcliente = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ppStmt.setInt(1, idcliente);
            rs = ppStmt.executeQuery();
            rs.first();
            c = getBes_Cliente(rs);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return c;
    }
    
    public List<Bes_Cliente> getBes_Cliente(String nome){
        List<Bes_Cliente> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM cliente WHERE nome ILIKE ?");
            ppStmt.setString(1, nome+ "%");
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
    
    public List<Bes_Cliente> getBes_Cliente(String nome, String dataInicio, 
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
    
    public Bes_Cliente getBes_Cliente(ResultSet rs) throws SQLException
    {
        Bes_Cliente c = new Bes_Cliente();
        
        c.setIdcliente(rs.getInt("idcliente"));
        c.setNome(rs.getString("nome"));
        c.setData_nascimento(md.date2String(rs.getString("data_nascimento")));
        c.setCpf(rs.getString("cpf"));
        
        return c;
    }
}
