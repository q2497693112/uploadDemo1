package cn.cvs.mapper;

import cn.cvs.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryMapper {
    List<DataDictionary> flatformList();
    List<DataDictionary> statusList();
}
