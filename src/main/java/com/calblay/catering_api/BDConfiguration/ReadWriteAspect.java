package com.calblay.catering_api.BDConfiguration;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Aspect
@Component
@org.springframework.core.annotation.Order(1)
public class ReadWriteAspect {

    @Before("@annotation(transactional)")
    public void setDbType(Transactional transactional){
        if(transactional.readOnly()){
            DbContextHolder.set(DbContextHolder.DbType.READ);
        }else{
            DbContextHolder.set(DbContextHolder.DbType.WRITE);
        }
    }

    @After("@annotation(transactional)")
    public void clear (Transactional transactional){
        DbContextHolder.clear();
    }
}
