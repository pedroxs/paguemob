package com.example.paguemob.web;

import com.example.paguemob.domain.Company;
import com.example.paguemob.service.CompanyService;
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
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> create(@RequestBody @Valid Company company, UriComponentsBuilder builder) {
        Company saved = companyService.create(company);

        return ResponseEntity
                .created(builder
                        .path("/companies/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .body(saved);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<Company>> list(@PageableDefault Pageable pageable,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "industry", required = false) String industry) {
        return ResponseEntity.ok(companyService.list(pageable, name, industry));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }
}
