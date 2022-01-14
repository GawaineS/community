package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("construct AlphaService");
    }

    @PostConstruct
    public void initialize() {
        System.out.println("Initialize AlphaService");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }
}
