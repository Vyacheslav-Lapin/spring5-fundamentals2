package lab.model;

import lombok.Data;

@Data
public class SimpleContact implements Contact {
    private String phoneNumber;
    private String email;
}
