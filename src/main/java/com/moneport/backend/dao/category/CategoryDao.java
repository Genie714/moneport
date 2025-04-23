package com.moneport.backend.dao.category;

import com.moneport.framework.dataObject.MapRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryDao {

    List<Map<String, Object>> listCategories(Map<String, Object> param);
    int insertCategory(Map<String, Object> param);
    int updateCategory(Map<String, Object> param);
    int deleteCategory(Map<String, Object> param);
}
