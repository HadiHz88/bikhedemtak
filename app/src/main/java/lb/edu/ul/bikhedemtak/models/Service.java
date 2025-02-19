package lb.edu.ul.bikhedemtak.models;

public class Service {
    private int id;
    private String serviceName;

    public Service(int id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getId() {
        return id;
    }
}
