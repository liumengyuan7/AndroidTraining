package com.smallpigeon.dynamics.dao;

import com.smallpigeon.entity.Dynamics;
import com.smallpigeon.entity.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicsMapper {
    public List<Dynamics> getAllDynamics();
    public void addDynamics(Dynamics dynamics);
    public void deleteDynamics(@Param("id") int id);
    public Dynamics getDynamics(@Param("id") int id);
    public void updateDynamics(Dynamics dynamics);
}
