package DAO;

import Objetos.Bes_Cliente;
import Objetos.Bes_Venda;
import Util.Bes_Conexao;
import Util.Bes_ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Bes_VendaDAO 
{
    Connection conn;
    Bes_ManipulaData md;
    Bes_ClienteDAO cDAO;
    
    public Bes_VendaDAO() 
    {
        conn = new Bes_Conexao().conectar();
        md = new Bes_ManipulaData();
        cDAO = new Bes_ClienteDAO();
    }

    public Bes_Venda salvar(Bes_Venda venda)
    {
        try
        {
            PreparedStatement stmt = conn.prepareStatement
            ("INSERT INTO venda(data_venda, total_venda, idcliente) VALUES (?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, md.string2Date(venda.getData_venda()));
            stmt.setDouble(2, venda.getTotal_venda());
            stmt.setInt(3, venda.getCliente().getIdcliente());
            stmt.execute();// Executa o SQL no banco
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                venda.setIdvenda(rs.getInt("idvenda"));
            }
            else{
                venda.setIdvenda(-1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
        return venda;
    }
    
    public void editar(Bes_Venda venda){
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("UPDATE venda SET data_venda=?, total_venda=?, idcliente=? WHERE idvenda = ?");
            stmt.setDate(1, md.string2Date(venda.getData_venda()));
            stmt.setDouble(2, venda.getTotal_venda());
            stmt.setInt(3, venda.getCliente().getIdcliente());
            stmt.setInt(4, venda.getIdvenda());
            stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
    }
    
    public int excluir(Bes_Venda venda)
    {
        int verif = 0;
        try{            
            PreparedStatement stmt = conn.prepareStatement
            ("DELETE FROM venda WHERE idvenda = ?");
            stmt.setInt(1, venda.getIdvenda());
            verif = stmt.executeUpdate();
        }
        catch(SQLException ex){  
            ex.printStackTrace();
        }
        return verif;
    }
    
    public List<Bes_Venda> getBes_Venda()
    {
        List<Bes_Venda> lstP = new ArrayList<>();
        ResultSet rs;        
        try
        {            
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM venda");            
            rs = ppStmt.executeQuery();
            while(rs.next())
            {
                lstP.add(getBes_Venda(rs));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public List<Bes_Venda> getBes_Venda(String data_venda){
        List<Bes_Venda> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM venda WHERE data_venda ILIKE ?");
            ppStmt.setString(1, data_venda+ "%");
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getBes_Venda(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
    
    public Bes_Venda getBes_Venda(int idvenda){
        Bes_Venda venda = new Bes_Venda();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM venda WHERE idvenda = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ppStmt.setInt(1, idvenda);
            rs = ppStmt.executeQuery();
            rs.first();
            venda = getBes_Venda(rs);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return venda;
    }
        
    /*public List<Bes_Venda> getBes_Venda(String nome, String dataInicio, 
            String dataFim){
        List<Pet> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM venda WHERE data_venda ILIKE ? AND "
                        + "data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1, nome+ "%");
            ppStmt.setDate(2, md.string2Date(dataInicio));
            ppStmt.setDate(3, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPet(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }    
    public List<Pet> getPets(String dataInicio, String dataFim){
        List<Pet> lstP = new ArrayList<>();
        ResultSet rs;
        try{            
            PreparedStatement ppStmt = conn.prepareStatement
                ("SELECT * FROM pet WHERE data_nascimento "
                        + "BETWEEN ? AND ?");
            ppStmt.setDate(1, md.string2Date(dataInicio));
            ppStmt.setDate(2, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPet(rs));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return lstP;
    }
*/
    
    public Bes_Venda getBes_Venda(ResultSet rs) throws SQLException
    {
        Bes_Venda venda = new Bes_Venda();
        
        venda.setIdvenda(rs.getInt("idvenda"));
        venda.setData_venda(md.date2String(rs.getString("data_venda")));
        venda.setTotal_venda(rs.getDouble("total_venda"));
        venda.setCliente(cDAO.getBes_Cliente(rs.getInt("idcliente")));
     
        return venda;
    }
}
