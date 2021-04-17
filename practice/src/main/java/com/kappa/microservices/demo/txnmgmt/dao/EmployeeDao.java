package com.kappa.microservices.demo.txnmgmt.dao;

import com.kappa.microservices.demo.txnmgmt.model.Employee;

public interface EmployeeDao {

    void insertEmployee(Employee cus);

    void deleteEmployeeById(String empid);
}
