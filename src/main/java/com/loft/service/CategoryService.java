package com.loft.service;

import com.loft.model.Category;


import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    List<Category> getParents();

    Category getById(Integer id);


}
