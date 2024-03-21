package com.lagunagym.LagunaGym.services;

import com.lagunagym.LagunaGym.models.Person;
import com.lagunagym.LagunaGym.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;
    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getById(Long id){
        Person p = new Person();
        Optional<Person> p2 = personRepository.findById(id);
        if (p2.isPresent()) {
            p = p2.get();
        } else {
            System.out.println("poxyu");
        }
        return p;
    }
    public void addPerson(Person p){
        personRepository.save(p);
    }
//    public Long getLast(){
//        List<Person> list = (List<Person>) personRepository.findAll();
//        Person lastPerson = null;
//        for (Person person : list) {
//            lastPerson = person;
//        }
//        if (lastPerson != null) {
//            return lastPerson.getId()+1;
//        } else {
//            return null; // Or throw an exception, depending on your requirement
//        }
//    }
    public Long getLast(){
        List<Person> list = (List<Person>) personRepository.findAll();
        Person lastPerson = null;
        for (Person person : list) {
            lastPerson = person;
        }
        Long result = (lastPerson != null ? lastPerson.getId()+1 : 0);
        return result;
    }
}
