package com.example.mobilear.controller;

import com.example.mobilear.Response.EHttpStatus;
import com.example.mobilear.Response.Response;
import com.example.mobilear.entity.Account;
import com.example.mobilear.entity.RegisterRequest;
import com.example.mobilear.entity.UserDetails;
import com.example.mobilear.jwt.JwtTokenProvider;
import com.example.mobilear.service.AuthService;
import com.example.mobilear.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/login")
    public Response<?> login(@RequestParam String username, @RequestParam String password) throws ExecutionException, InterruptedException {
        Account account = new Account(username, password);
        EHttpStatus status = authService.checkLogin(account);
        if (status.getMessage().equals("Success")) {
            String token = jwtTokenProvider.generateToken(account);
            return new Response<>(status, token);
        } else {
            return new Response<>(status);
        }

    }

    @GetMapping("/checkAccount")
    public Response<?> checkAccount(@RequestParam String username) throws ExecutionException, InterruptedException {
        boolean exists = authService.checkAccount(username);
        if (exists) {
            return new Response<>(EHttpStatus.USERNAME_ALREADY_EXISTS, "Username already exists");
        } else {
            return new Response<>(EHttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public Response<?> register(@RequestBody RegisterRequest registerRequest) throws ExecutionException, InterruptedException {
        Account account = registerRequest.getAccount();
        UserDetails userDetails = registerRequest.getUserDetails();

        boolean saveAccount = authService.saveAccount(account);
        boolean saveUserDetails = userDetailService.saveUserDetails(account.getUsername(), userDetails);

        if(saveAccount && saveUserDetails) {
            return new Response<>(EHttpStatus.OK);
        } else {
            return new Response<>(EHttpStatus.INTERNAL_SERVER_ERROR);
        }

    }





    @GetMapping("/getAllAccounts")
    public ResponseEntity<?> getAllAccounts() throws ExecutionException, InterruptedException {
        List<Account> accountList = authService.getAllAccounts();
        if (accountList != null) {
            return ResponseEntity.ok(accountList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }


    @PostMapping("/updateAccount")
    public String updateAccount(Account account) throws ExecutionException, InterruptedException {
        return authService.updateAccount(account);
    }

    @DeleteMapping("/deleteAccount")
    public String deleteAccount(@RequestParam String username) throws ExecutionException, InterruptedException {
        return authService.deleteAccount(username);
    }
}
