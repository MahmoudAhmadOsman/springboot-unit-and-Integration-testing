/*---------
      => Author: Mahmoud Osman
      => Github Link: https://github.com/mahmoudahmadosman
--------- */

package com.minsura.repository;

import com.minsura.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    //use @BeforeEach method and refactor the entire code
    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("John")
                .lastName("Smith")
                .email("smith778@yahoo.com")
                .build();
    }

    // JUnit test for save employee method
    @DisplayName("JUnit test for save employee")
    @Test
    public void given_EmployeeObject_whenSave_thenReturnSavedEmployeeObject() {

        //given - precondition or set up

        //when - action or the behavior that is being tested
        Employee savedEmployee = employeeRepository.save(employee);

        //then- verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);


    }

    // JUnit test for getAll employees method

    @DisplayName("JUnit test for getAll employees")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {

        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john_doe900@yahoo.com")
                .build();
        //save the 2 employee objects
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behavior that is being tested
        //--findAll() method - the behavior that is being tested
        List<Employee> employeesList = employeeRepository.findAll();

        //then- verify the output
        assertThat(employeesList).isNotNull();
        assertThat(employeesList.size()).isEqualTo(2); // should return 2 object/employees
    }


    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")

    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        //given - precondition or set up
        employeeRepository.save(employee);

        //when - action or the behavior that is being tested
        Employee findEmployeeById = employeeRepository.findById(employee.getId()).get();

        //then- verify the output
        assertThat(findEmployeeById).isNotNull();
    }


    // JUnit test for get employee by email
    @DisplayName("JUnit test for get employee by email")

    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);
        //when - action or the behavior that is being tested
        Employee findEmployeeByEmail = employeeRepository.findByEmail(employee.getEmail()).get();

        //then- verify the output
        assertThat(findEmployeeByEmail).isNotNull();
    }


    // JUnit test for update employee
    @DisplayName("JUnit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition or set up

        employeeRepository.save(employee);

        //when - action or the behavior that is being tested
        //1. get employee from the database by it
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();

        //2. update employee info
        savedEmployee.setFirstName("Miski");
        savedEmployee.setLastName("Ali");
        savedEmployee.setEmail("miski_ali80@yahoo.com");

        Employee updatedEmployee = employeeRepository.save(savedEmployee); //save the employee again

        //then- verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("miski_ali80@yahoo.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Miski");
    }


    // JUnit test for delete employee
    @DisplayName("JUnit test for delete employee")

    @Test
    public void givenEmployeeObject_whenDelete_thenDeleteEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);

        //when - action or the behavior that is being tested
        //employeeRepository.delete(employee); -  works as well
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());


        //then- verify the output
        assertThat(employeeOptional).isEmpty();

    }


    // JUnit test for JPQL query with index
    @DisplayName("JUnit test for JPQL query with index")
    @Test
    public void givenFirstAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);
        String fistName = "John";
        String lastName = "Smith";

        //when - action or the behavior that is being tested
        Employee savedEmployee = employeeRepository.findByJPQL(fistName, lastName);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();

    }


    // JUnit test for JPQL query with named params
    @DisplayName("JUnit test for JPQL query with named params")
    @Test
    public void givenFirstAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);
        String fistName = "John";
        String lastName = "Smith";

        //when - action or the behavior that is being tested
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(fistName, lastName);

        //then- verify the output
        assertThat(savedEmployee).isNotNull();

    }


    // JUnit test for  native sql with index params
    @DisplayName("JUnit test for  native sql with index params")

    @Test
    public void givenFirstAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);

        //when - action or the behavior that is being tested

        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());


        //then- verify the output
        assertThat(savedEmployee).isNotNull();

    }


    // JUnit test for  native SQL with named params
    @DisplayName("JUnit test for  native sql with named params")
    @Test
    public void givenFirstAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

        //given - precondition or set up

        employeeRepository.save(employee);

        //when - action or the behavior that is being tested

        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        //then- verify the output
        assertThat(savedEmployee).isNotNull();


    }


}