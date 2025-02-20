package lb.edu.ul.bikhedemtak.models;

public class Promo {
    private String code;
    private String description;

    public Promo(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
