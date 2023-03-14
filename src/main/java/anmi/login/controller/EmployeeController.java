package anmi.login.controller;

import anmi.login.dto.EmployeeDTO;
import anmi.login.dto.EmployeeFormDTO;
import anmi.login.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class EmployeeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService service;

    //cadastra novos funcionários
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody @Valid EmployeeFormDTO body) {
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        EmployeeDTO employee = this.service.save(body);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    //lista os empregados com filtro por nome.
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestParam(name = "nome", required = false) String fullName) {
        List<EmployeeDTO> employee = this.service.getEmployees(fullName);
        return ResponseEntity.ok(employee);
    }

    //procura o empregado pelo seu cpf
    @PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
    @GetMapping("/{cpf}")
    public ResponseEntity<EmployeeDTO> searchEmployee(@PathVariable String cpf){
        EmployeeDTO employee = this.service.searchEmployee(cpf);
        return ResponseEntity.ok(employee);
    }

    //atualiza os dados de um empregado
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{cpf}")
    @Transactional
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String cpf, @RequestBody @Valid EmployeeFormDTO body) {
        EmployeeDTO employee = this.service.updateEmployee(cpf, body);
        return ResponseEntity.ok(employee);
    }

    //deleta um empregado
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<?> deleteEmployee(@PathVariable String cpf) {
        this.service.deleteEmployee(cpf);
        return ResponseEntity.noContent().build();
    }
}
