package com.smallpigeon.record.dao;
import com.smallpigeon.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper {
    public List<Record> getAllRecords();
    public void addRecord(Record record);
    public void deleteRecord(@Param("id") int id);
    public Record getRecord(@Param("id") int id);
    public void updateRecord(Record record);
}
