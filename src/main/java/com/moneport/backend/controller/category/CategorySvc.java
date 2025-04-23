package com.moneport.backend.controller.category;

import com.moneport.backend.dao.category.CategoryDao;
import com.moneport.framework.dataObject.MapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategorySvc {

    private final CategoryDao categoryDao;

    public List<Map<String, Object>> listCategories(MapRequest param) {
        return categoryDao.listCategories(param);
    }

    public void insertCategory(MapRequest param) {
        categoryDao.insertCategory(param);
    }

    public void updateCategory(MapRequest param) {
        int result = categoryDao.updateCategory(param);
        if (result == 0) throw new IllegalArgumentException("수정할 카테고리가 없습니다.");
    }

    public void deleteCategory(MapRequest param) {
        int result = categoryDao.deleteCategory(param);
        if (result == 0) throw new IllegalArgumentException("삭제할 카테고리가 없습니다.");
    }
}