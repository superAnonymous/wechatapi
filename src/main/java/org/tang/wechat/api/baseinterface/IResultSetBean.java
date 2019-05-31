package org.tang.wechat.api.baseinterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultSetBean {
    void makeBean(ResultSet var1) throws SQLException;
}
