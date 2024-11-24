package com.interview.employeeSystem.services;

import com.interview.employeeSystem.Dto.response.EmployeeDetailsResponse;
import com.interview.employeeSystem.exception.EmployeeSystemException;
import com.interview.employeeSystem.repository.EmployeeDetailsRepository;
import com.interview.employeeSystem.repository.RoleRepository;
import com.interview.employeeSystem.repository.entity.EmployeeDetails;
import com.interview.employeeSystem.repository.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeDetailsService {

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final RestTemplate restTemplate;

    private final RoleRepository roleRepository;

    public EmployeeDetailsService(EmployeeDetailsRepository employeeDetailsRepository, RestTemplate restTemplate, RoleRepository roleRepository) {
        this.employeeDetailsRepository = employeeDetailsRepository;
        this.restTemplate = restTemplate;
        this.roleRepository = roleRepository;
    }

    public EmployeeDetailsResponse getEmployeeDetails(int employeeId) throws ExecutionException, InterruptedException {
        EmployeeDetailsResponse employeeDetailsResponse=new EmployeeDetailsResponse();
        CompletableFuture<EmployeeDetails> dbCall = CompletableFuture.supplyAsync(
                () -> {
                    Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(employeeId);
                    return employeeDetails.orElse(null);
                });

        CompletableFuture<Integer> thirdPartyApiCall=CompletableFuture.supplyAsync(() ->{
            Map<String,Integer> queryParam = new HashMap<>();
            UriComponents builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/getEmployeeSalary")
                    .queryParam("employee_id",employeeId).build();
            try{
                ResponseEntity<Object> response= restTemplate.getForEntity(builder.toUriString(),Object.class);
                return Integer.parseInt(response.getBody().toString());
            }
            catch(Exception ex){
                return -1;
            }
        });
        EmployeeDetails employeeDetails= dbCall.join();
        int salary = thirdPartyApiCall.join();
        if(salary==-1 || employeeDetails==null){
            throw new EmployeeSystemException("Employee details not found", HttpStatus.BAD_REQUEST);
        }
        employeeDetailsResponse.setEmployeeName(employeeDetails.getEmployeeName());
        employeeDetailsResponse.setDepartment(employeeDetails.getDepartment());
        employeeDetailsResponse.setDesignation(employeeDetails.getDesignation());
        employeeDetailsResponse.setId(employeeDetails.getId());
        employeeDetailsResponse.setSalary(salary);
        return employeeDetailsResponse;
    }

    public void checkRoles(int employeeId, int requestEmployeeId) {
        String role=null;
        Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(requestEmployeeId);
        if(employeeDetails.isPresent()){
          Optional<Role> optionalRole=roleRepository.findById(employeeDetails.get().getRole_id());
          if(optionalRole.isPresent()){
              role = optionalRole.get().getRole_Name();
          }
        }

        if(!(employeeId==requestEmployeeId || "Admin".equalsIgnoreCase(role))){
            throw new EmployeeSystemException("You are not allowed to check information of other employee details",HttpStatus.UNAUTHORIZED);
        }
    }
}
