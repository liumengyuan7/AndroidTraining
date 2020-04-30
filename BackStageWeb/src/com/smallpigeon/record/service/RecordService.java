package com.smallpigeon.record.service;

import com.smallpigeon.entity.Record;
import com.smallpigeon.record.dao.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className RecordService
 * @auther 吴东枚
 * @description 记录service
 * @date 2020/04/27 13:19
 */
@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;
    public List<Record> getAllRecords(){
        return  this.recordMapper.getAllRecords();
    }
    public void addRecord(Record record){
        this.recordMapper.addRecord(record);
    }
    public void deleteRecord(int id){
        this.recordMapper.deleteRecord(id);
    }
    public Record getRecord(int id){
        return this.recordMapper.getRecord(id);
    }
    public void updateRecord(Record record){
        this.recordMapper.updateRecord(record);
    }
}
