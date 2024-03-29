package com.learnSB.learnSpringBoot;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

  private final CustomerRepository customerRepository;

  public Main(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  record NewCustomerRequest(
      String name,
      String email,
      Integer age) {
  }

  record UpdateCustomerRequest(
      String name,
      String email,
      Integer age) {
  }

  @PostMapping
  public void addCustomer(@RequestBody NewCustomerRequest request) {
    Customer customer = new Customer();
    customer.setName(request.name());
    customer.setEmail(request.email());
    customer.setAge(request.age());
    customerRepository.save(customer);

  }

  @DeleteMapping("{customerId}")
  public void deleteCustomer(@PathVariable("customerId") Integer id) {
    customerRepository.deleteById(id);
  }

  @PutMapping("{customerId}")
  public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody UpdateCustomerRequest request) {
    Optional<Customer> customerOptional = customerRepository.findById(id);
    Customer customer = new Customer();
    if (customerOptional.isPresent()) {
      customer = customerOptional.get();
      customer.setName(request.name());
      customer.setEmail(request.email());
      customer.setAge(request.age());
      customerRepository.save(customer);
    }
  }

  @GetMapping
  public List<Customer> getCustomers() {
    return customerRepository.findAll();
  }

  // @GetMapping("/greet")
  // public GreetResponse greet() {
  // GreetResponse response = new GreetResponse(
  // "Hello",
  // List.of("Java", "React", "Javascript"),
  // new Person("Parth", 26, 100));
  // return response;
  // }

  // record Person(String name, int age, double savings) {
  // }

  // record GreetResponse(
  // String greet,
  // List<String> favProgrammingLanguages,
  // Person person) {
  // }

}
