package com.example.flight_search.controller;
import com.example.flight_search.dto.AuthDto;
import com.example.flight_search.entity.User;
import com.example.flight_search.security.JwtTokenProvider;
import com.example.flight_search.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody User loginUser){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserName(),loginUser.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userService.getOneUserByUserName(loginUser.getUserName());
        AuthDto authDto = new AuthDto();
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        authDto.setMessage("Başarıyla giriş yapıldı.");
        authDto.setAccessToken(jwtToken);
        authDto.setUserId(user.getUser_id());
        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody User registerUser){
        AuthDto authDto = new AuthDto();
        if (userService.getOneUserByUserName(registerUser.getUserName()) != null){
            authDto.setMessage("Kullanıcı adı zaten kullanılıyor");
            return new ResponseEntity<>(authDto, HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setUserName(registerUser.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        newUser.setRole(registerUser.getRole());
        userService.createUser(newUser);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                registerUser.getUserName(),registerUser.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        authDto.setMessage("Kullanıcı başarıyla oluşturuldu.");
        authDto.setAccessToken("Bearer "+ jwtToken);
        authDto.setUserId(newUser.getUser_id());
        return new ResponseEntity<>(authDto, HttpStatus.CREATED);
    }

}
