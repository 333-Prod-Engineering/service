package ro.unibuc.hello.dto;

public class Author {

    private String name;
    private String nationality;

    public Author() {
    }

    public Author(String name, String nationality) {

        this.name = name;
        this.nationality = nationality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

}
