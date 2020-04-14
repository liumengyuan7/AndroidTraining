package com.record.dao;

import com.entity.Record;

/**
 * @author syf
 * @ClassName RecordMapper
 * @description
 * @date 2020/04/10 15:45
 */

public interface RecordMapper {

    public int insertRecord(Record record);

    public int getUserTodayKm(String userId);

}
