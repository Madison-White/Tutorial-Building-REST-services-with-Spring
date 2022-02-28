package com.example.springrest.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

  @Override
  public EntityModel<Order> toModel(Order order) {

    // Unconditional links to single-item resource and aggregate root

    EntityModel<Order> orderModel = EntityModel.of(order,
        linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
        linkTo(methodOn(OrderController.class).all()).withRel("orders"));

    // Conditional links based on state of the order
    
	//Instead of clients parsing the payload, give them links to signal valid actions. 
	//Decouple state-based actions from the payload of data. 
	//In other words, when CANCEL and COMPLETE are valid actions, dynamically add them to the list of links. 
	//Clients only need show users the corresponding buttons when the links exist.

    if (order.getStatus() == Status.IN_PROGRESS) {
      orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
      orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
    }

    return orderModel;
  }
}