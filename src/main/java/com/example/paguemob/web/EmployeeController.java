package com.example.paguemob.web;

import com.example.paguemob.domain.Employee;
import com.example.paguemob.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee, UriComponentsBuilder builder) {
        Employee created = employeeService.create(employee);

        return ResponseEntity
                .created(builder
                        .path("/employees/{id}")
                        .buildAndExpand(created.getId())
                        .toUri())
                .body(created);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Employee>> list(@PageableDefault Pageable pageable,
                                               @RequestParam(value = "companyName", required = false) String companyName,
                                               @RequestParam(value = "jobTitle", required = false) String jobTitle) {
        return ResponseEntity.ok(employeeService.list(pageable, companyName, jobTitle));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }
}
