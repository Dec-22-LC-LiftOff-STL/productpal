package org.launchcode.productpal.models;

import org.launchcode.productpal.models.data.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DescriptionService {
    @Autowired
    private DescriptionRepository descriptionRepository;
    public List<Description> listDescription(){
        return (List<Description>) descriptionRepository.findAll();

    }

    public void save(Description description) {
        descriptionRepository.save(description);
    }

    public Description get(Integer id) throws UserNotFoundException {
        Optional<Description> result = descriptionRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not found user by ID");
    }

    public Description getByName(String name) throws UserNotFoundException {
        List<Description> result = descriptionRepository.findByName(name);
        if(result.size()>0){
            return result.get(0);
        }
        throw new UserNotFoundException("Could not found user by NAME");
    }

    public void deleteDescription(Integer id) throws UserNotFoundException {

        Long count=descriptionRepository.countById(id);
        if(count == null|| count == 0){
            throw new UserNotFoundException("Could not found user by ID");
        }
        descriptionRepository.deleteById(id);
    }
}