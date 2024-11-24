package com.interview.employeeSystem.Controller;

import com.interview.employeeSystem.Dto.response.EmployeeDetailsResponse;
import com.interview.employeeSystem.exception.EmployeeSystemException;
import com.interview.employeeSystem.services.EmployeeDetailsService;
import com.interview.employeeSystem.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class EmployeeDetailsController {

    private final EmployeeDetailsService employeeDetailsService;
    private final TokenService tokenService;

    public EmployeeDetailsController(EmployeeDetailsService employeeDetailsService, TokenService tokenService) {
        this.employeeDetailsService = employeeDetailsService;
        this.tokenService = tokenService;
    }

    // 1. Auth token table -> token id, employee id, create , end
    // 2. Role table which has roleid and role name
    // 3. Generate token controller who generate token and store in db with expiry time and create time and request id
    // 4. in main controller take it in header and verify expiry time from db, role name of request id from auth token and verify you are authorize to view employee details

    @GetMapping("getEmployeeDetails")
    public ResponseEntity<Object> getEmployeeDetails(@RequestHeader("token") String token, @RequestParam("employee_id") int employeeId) throws ExecutionException, InterruptedException {
        try {
            int requestEmployeeId = tokenService.validateToken(token);
            employeeDetailsService.checkRoles(employeeId, requestEmployeeId);
            EmployeeDetailsResponse employeeDetailsResponse = employeeDetailsService.getEmployeeDetails(employeeId);
            return ResponseEntity.ok(employeeDetailsResponse);
        } catch (EmployeeSystemException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }

    }
}
