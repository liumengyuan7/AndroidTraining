package com.smallpigeon.dynamics.service;

import com.smallpigeon.dynamics.dao.DynamicsMapper;
import com.smallpigeon.entity.Dynamics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicsService {
    @Autowired
    private DynamicsMapper dynamicsMapper;
    public List<Dynamics> getAllDynamics(){
        return dynamicsMapper.getAllDynamics();
    }
    public void addDynamics(Dynamics dynamics){
        this.dynamicsMapper.addDynamics(dynamics);
    }
    public void deleteDynamics(int id){
        this.dynamicsMapper.deleteDynamics(id);
    }
    public Dynamics getDynamics(int id){
        return this.dynamicsMapper.getDynamics(id);
    }
    public void updateDynamics(Dynamics dynamics){
        this.dynamicsMapper.updateDynamics(dynamics);
    }
}
