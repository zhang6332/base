package com.ilsmp.base.icon;

import javax.swing.*;

import com.intellij.openapi.util.IconLoader;

public class CrudIcons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, CrudIcons.class);
    }

    public static final Icon LOGO = load("/icons/logo.svg");
    public static final Icon SPRING_BOOT = load("/icons/springboot.svg");

    public static final Icon MYSQL_CONN = load("/icons/mysql.svg");
    public static final Icon PGSQL_CONN = load("/icons/postgresql.png");
    public static final Icon ORACLE_CONN = load("/icons/oracle.png");

    public static final Icon DB = load("/icons/dbms.svg");
    public static final Icon SCHEMA = load("/icons/schema.svg");
    public static final Icon TABLE = load("/icons/table.svg");

    public static final Icon ADD = load("/icons/add.png");
    public static final Icon REMOVE = load("/icons/remove.png");
    public static final Icon EDIT = load("/icons/edit.png");

    public static final Icon LOADING = load("/icons/loading.svg");
}
