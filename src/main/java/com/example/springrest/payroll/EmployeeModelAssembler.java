package com.example.springrest.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * based on converting a non-model object (Employee) into a model-based object (EntityModel<Employee>).
 * 
 * the assembler will be automatically created when the app starts thanks to @Component
 */
@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

  @Override
  public EntityModel<Employee> toModel(Employee employee) {

    return EntityModel.of(employee,
        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),  //asks Spring HATEOAS to build a link to the EmployeeController's one() method, and flag it as a self link.
        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")); //asks Spring HATEOAS to build a link to the aggregate root, all(), and call it "employees".
    
  }
}
