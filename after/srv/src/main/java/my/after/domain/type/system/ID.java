package my.after.domain.type.system;

import java.util.UUID;

public class ID {

    private String value;

    public ID(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ID generate(){
        return new ID(UUID.randomUUID().toString());
    }
}
