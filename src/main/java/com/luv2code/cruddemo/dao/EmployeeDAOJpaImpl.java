package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{
    //define field for entityManager(Spring Boot automatically crates the bean for it, so it's ready for injection)
    private EntityManager entityManager;

    //set up constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Employee> findAll() {
        //create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);
        //execute query and get result list
        List<Employee> employees = theQuery.getResultList();
        //return results
        return employees;
    }

    @Override
    public Employee findById(int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    //we don't use @Transactional here in the DAO layer because it will be handled at the Service layer; same to deleteById(int id)
    public Employee save(Employee employee) {
        //save employee
        return entityManager.merge(employee); //if(id == 0) then insert/save else update
    }

    @Override
    public void deleteById(int id) {
        //find employee by id
        Employee employee = entityManager.find(Employee.class, id);
        //remove it
        entityManager.remove(employee);
    }
}
