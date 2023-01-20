package org.launchcode.productpal.models;

import org.launchcode.productpal.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> listCategory(){
        return (List<Category>) categoryRepository.findAll();

    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category get(Integer id) throws UserNotFoundException {
        Optional<Category> result = categoryRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not found user by ID");
    }

    public Category getByName(String name) throws UserNotFoundException {
        ArrayList<Category> result = categoryRepository.findByName(name);
        if(result.size()>0){
            return result.get(0);
        }
        throw new UserNotFoundException("Could not found user by NAME");
    }

    public void deleteCategory(Integer id) throws UserNotFoundException {

        Long count=categoryRepository.countById(id);
        if(count == null|| count == 0){
            throw new UserNotFoundException("Could not found user by ID");
        }
        categoryRepository.deleteById(id);
    }
}