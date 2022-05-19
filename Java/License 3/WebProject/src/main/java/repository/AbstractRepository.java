package repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class AbstractRepository {
    
    protected String buildQueryWith(String queryType, List<QueryParameter> parameters) {
        
        StringBuilder queryParams = new StringBuilder(queryType);
        
        for (int i = 0; i < parameters.size(); i++) {
            queryParams.append( i == 0 ? " WHERE " : " AND ")
                .append(parameters.get(i).getField() )
                .append(" ")
                .append(parameters.get(i).getType() )
                .append(" ?")
            ;
        }
        
        return queryParams.toString();
    }
    
    protected void setQueryParameters(PreparedStatement stmt, List<QueryParameter> parameters) throws SQLException {
        this.setQueryParameters(stmt, parameters, 1);
    }
    protected void setQueryParameters(PreparedStatement stmt, List<QueryParameter> parameters, int n) throws SQLException {
        
        for (int i = 0; i < parameters.size(); i++, n++) {
            String field = parameters.get(i).getField();
            Object value = parameters.get(i).getValue();
            
            if (value instanceof String)
                stmt.setString(n, (String) value);
            else if (value instanceof Float)
                stmt.setFloat(n, (Float) value);
            else if (value instanceof Integer)
                stmt.setInt(n, (Integer) value);
            else if (value instanceof Date)
                stmt.setDate(n, (Date) value);
        }
    }
    
}
