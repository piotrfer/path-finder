package source;

public class Place {
    private String id;
    private String name;
    private String description;

    public Place(String id_miejscca, String name, String description) {
        id = id_miejscca;
        this.name = name;
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Place == false) {
            return false;
        }
        Place no = (Place) o;
        if (no.getId() == this.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
