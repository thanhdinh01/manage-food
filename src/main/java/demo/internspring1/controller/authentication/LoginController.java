package demo.internspring1.controller.authentication;

import com.google.gson.*;
import demo.internspring1.dto.request.LoginRequest;
import demo.internspring1.dto.response.LoginResponse;
import demo.internspring1.jwt.JwtTokenProvider;
import demo.internspring1.service.impl.CustomAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/api/v1/auth/")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();

//    @ModelAttribute("loginform")
//    public LoginRequest loginRequest1() {
//        return new LoginRequest();
//    }

//    @GetMapping()
//    public String login(){
//        return "loginPage";
//    }

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<LoginResponse> signInAcc(@RequestBody LoginRequest loginRequest) {
        System.out.println("sigin post");
        System.out.println(loginRequest.getUsername());
//        LoginRequest loginRequest1= gson.fromJson(loginRequest, LoginRequest.class);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customAccountDetails);

        System.out.println("jwt " + jwt);
        System.out.println(customAccountDetails.getUsername());
        System.out.println("login success");
        return ResponseEntity.ok(new LoginResponse(jwt,customAccountDetails.getUsername(),customAccountDetails.getAuthorities()));
    }
}
