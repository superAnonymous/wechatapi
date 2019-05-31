package org.tang.wechat.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tang.wechat.api.baseinterface.IResultSetBean;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EntityBean implements IResultSetBean {
    public EntityBean() {
    }

    protected String notNullString(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return this.notNullString(s);
    }

    protected String notNullString(String str) {
        return str == null ? "" : str.trim();
    }

    protected BigDecimal notNullBigDecimal(BigDecimal value) {
        return value == null ? new BigDecimal(0) : value;
    }

    public String toJson() throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(this);
    }
}
