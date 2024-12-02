
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bes_Conexao 
{
     /* Abaixo as variáveis com as informações
    de acesso ao banco de dados são declaradas.
    A palavra reservada "final" define que a
    variável não poderá ser alterada em outra
    parte do programa. 
    "private" define que a váriavel será visível apenas na 
    classe a qual pertence.*/
    final private String driver="org.postgresql.Driver";
    final private String url="jdbc:postgresql://localhost:5433/"
            + "avaliacao";
    final private String usuario = "user_aulas";
    final private String senha = "123456";
    
    /*Através desse método será possível fazer
    a conexão com banco de dados.*/
    public Connection conectar()
    {
        /*variável "conn" do tipo "Connection", sendo que "Connection" 
        é uma classe com métodos para conexão com banco de dados.*/
        Connection conn = null;
        
        /*Os comandos "try" e "catch" são utilizados para tratar 
        possíveis exceções.
        No caso da utilização de aplicações java com banco de dados 
        há acesso externo a aplicação, logo o caminho de acesso externo 
        pode ser incorreto, o driver de acesso pode não existir e os 
        comandos SQL podem estar errados.*/
        try
        {  
            /*Faz a leitura das classes do driver em tempo de execução.*/
            Class.forName(driver);
            /*Através das classes do driver é criada uma conexão 
            com banco. 
            Para estabelecer a conexão é preciso definir usuário, 
            senha e caminho onde o banco se encontra (url).*/
            conn = DriverManager.getConnection(url,usuario,senha);
        }
        /*Exceção caso a classe do driver não seja encontrada.*/
        catch(ClassNotFoundException ex)
        {  
            ex.printStackTrace();
        }
        /*Exceção caso o caminho de acesso, usuário ou senha do banco 
        estejam incorretos.*/
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        /*Retorna a variável "conn" que contém a conexão com o banco de dados.*/
        return conn;
    }
}
