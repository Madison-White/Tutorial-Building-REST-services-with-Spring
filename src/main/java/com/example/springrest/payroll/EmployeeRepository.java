package com.example.springrest.payroll;

import org.springframework.data.jpa.repository.JpaRepository;

//Spring makes accessing data easy. By declaring this as an interface we automatically will be able to
//Create, Update, Delete, Select
interface EmployeeRepository extends JpaRepository<Employee, Long> {

}