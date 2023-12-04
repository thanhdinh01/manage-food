package demo.internspring1.dto.request;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Set<Integer> decentralizationSet;
}
