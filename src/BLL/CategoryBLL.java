package BLL;

import java.util.ArrayList;

import DAL.CategoryDAL;
import DTO.Categories;

public class CategoryBLL {
    private CategoryDAL categoryDAL;
    public CategoryBLL() {
        this.categoryDAL = new CategoryDAL();
    }

    public ArrayList<Categories> getAllCategories() {
        return this.categoryDAL.getAllCategoryList();
    }
}
