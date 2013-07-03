package com.nrkpj.commetial.hrm.core.db;

import java.sql.SQLException;

import org.h2.server.pg.PgServer;
import org.h2.tools.Server;

public class DB {
    public static void main(String[] args) throws SQLException {
        Server server = new Server(new PgServer());
        System.out.println(server.getURL());
        server.run();
    }
}
