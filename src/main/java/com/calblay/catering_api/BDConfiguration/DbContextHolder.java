package com.calblay.catering_api.BDConfiguration;

public class DbContextHolder {
    public enum DbType{
        READ,WRITE
    }

    private static final ThreadLocal<DbType> context = new ThreadLocal<>();
    public static void set (DbType dbType){
        context.set(dbType);
    }

    public static DbType get() {
        return context.get() == null ? DbType.WRITE : context.get();
    }

    public static void clear (){
        context.remove();
    }
}
