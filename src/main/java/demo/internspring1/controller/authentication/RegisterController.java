package demo.internspring1.controller.authentication;

import com.google.gson.*;
import demo.internspring1.dto.request.ClientRegisterRequest;
import demo.internspring1.dto.request.LoginRequest;
import demo.internspring1.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/api/v1/auth/")
public class RegisterController {
    @Autowired
    private AccountService accountService;

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();

//    @GetMapping("/registration")
////    @ResponseBody
//    public String registrationPage(){
//        return "registrationPage";
//    }
    @PostMapping(value = "register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody ClientRegisterRequest clientRequest, Model model) {
        System.out.println("post register");
        System.out.println(clientRequest.getEmail());
        System.out.println(clientRequest.getName());
        String result= accountService.register(clientRequest);
        model.addAttribute("resultRegister", result);
        System.out.println("controller register");
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "verify-account")
    @ResponseBody
    public String verifyAccount(@RequestParam String email, @RequestParam String otp){
        String result= accountService.verifyAccount(email, otp);
        return result;
    }

    @PutMapping(value = "regenerate-otp")
    @ResponseBody
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return ResponseEntity.ok(accountService.regenerateOtp(email));
    }
}
