package wiley.lms.userprocessor.model.entity;

import java.util.stream.Stream;

public enum Role {
    SUPER_ADMIN("SA"),
    ADMIN("AD"),
    AUTHOR("AU"),
    INSTRUCTOR("AU"),
    STUDENT("AU"),
    SUBSCRIBER("AU");

    private String code;

    private Role(String code) {
        this.code = code;
    }

    public static Role of(String code) {
        return Stream.of(Role.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getCode() {
        return code;
    }
}
