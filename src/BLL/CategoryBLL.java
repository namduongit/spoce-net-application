package BLL;

import java.util.ArrayList;

import DAL.CategoryDAL;
import DTO.Categories;

public class CategoryBLL {
    private CategoryDAL categoryDAL;
    public CategoryBLL() {
        this.categoryDAL = new CategoryDAL();
    }

    public String getNameCategoryById(String id) {
        return this.categoryDAL.getNameCategoryById(id);
    }

    public ArrayList<Categories> getAllCategories() {
        return this.categoryDAL.getAllCategoryList();
    }

    public boolean createNewCategory(String name) {
        return this.categoryDAL.createNewCategory(name);
    }
}
