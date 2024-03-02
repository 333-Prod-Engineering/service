package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class AuthorEntity {
    @Id
    private String authorId;

    private String name;
    private String nationality;

    public AuthorEntity() {
    }

    public AuthorEntity(String name, String nationality) {
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

    @Override
    public String toString() {
        return String.format(
                "Author[authorId='%s', name='%s', nationality='%s']",
                authorId, name, nationality);
    }

}
