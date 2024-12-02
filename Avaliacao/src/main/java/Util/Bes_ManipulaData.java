package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Bes_ManipulaData 
{
    public Date string2Date(String data)
    {
        DateTimeFormatter formato = 
                DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        Date dataSaida = Date.valueOf(LocalDate.parse(data, formato));
                
        return dataSaida;
    }
    
    public String date2String(String data)
    {
        try
        {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").
                    parse(data);
            data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        }
        catch(ParseException e)
        {
            System.out.println(e);
        }
        return data;
    }
}