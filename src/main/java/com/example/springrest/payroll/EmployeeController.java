package com.example.springrest.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//the data returned by each method will be written straight into the response body instead of rendering a template.
@RestController
class EmployeeController {

  private final EmployeeRepository repository;
  private final EmployeeModelAssembler assembler;

  EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  //We have routes for each operation (@GetMapping, @PostMapping, @PutMapping and @DeleteMapping, 
  //corresponding to HTTP GET, POST, PUT, and DELETE calls)

  // Aggregate root
  // tag::get-aggregate-root[]
  
  //ConnectionModel another Spring HATEOAS container; it’s aimed at encapsulating collections of resources—instead of a single resource entity, like EntityModel<> from earlier. CollectionModel<>, too, lets you include links.
  @GetMapping("/employees")
  CollectionModel<EntityModel<Employee>> all() {

	//We fetch all records but with Java 8 transform it to a list of EntityModel<?> Objects
    List<EntityModel<Employee>> employees = repository.findAll().stream()
        .map(assembler::toModel) //This is Java 8 method references
        .collect(Collectors.toList());

    return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  //ResponseEntity is used to create an HTTP 201 Created status message
  @PostMapping("/employees")
  ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

    EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

//  return the model-based version of the saved object.
    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //adds in the link to itself (i.e   "_links": {"self": {"href": "http://localhost:8080/employees/3"}, ... }     )
        .body(entityModel);
  }

  // Single item
  
  //EntityModel<?> a generic container from Spring HATEOAS that includes not only the data but a collection of links.
  @GetMapping("/employees/{id}")
  EntityModel<Employee> one(@PathVariable Long id) {

    Employee employee = repository.findById(id) //
        .orElseThrow(() -> new EmployeeNotFoundException(id));
    
    return assembler.toModel(employee);
  }
  
  @PutMapping("/employees/{id}")
  ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    Employee updatedEmployee = repository.findById(id) //
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repository.save(employee);
        }) //
        .orElseGet(() -> {
          newEmployee.setId(id);
          return repository.save(newEmployee);
        });

    EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //The location header. A hypermedia powered client could opt to "surf" to this new resource and proceed to interact with it.
        .body(entityModel);
  }
  
  @DeleteMapping("/employees/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}

/*
What is the point of adding all these links? It makes it possible to evolve REST services over time. 
Existing links can be maintained while new links can be added in the future. Newer clients may take advantage of the new links, 
while legacy clients can sustain themselves on the old links. This is especially helpful if services get relocated and moved around. 
As long as the link structure is maintained, clients can STILL find and interact with things.
*/




