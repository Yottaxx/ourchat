package bit.group.ourchat.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "exampleEntity")
public class exampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role="user";


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<exampleEntity> getMoments() {
        return moments;
    }

    public void setMoments(List<exampleEntity> moments) {
        this.moments = moments;
    }


    @OneToMany(mappedBy = "exampleEntity",cascade= CascadeType.ALL,fetch = FetchType.LAZY)//People是关系的维护端，当删除 people，会级联删除
    // address
    private List<exampleEntity> moments;//地址

    public exampleEntity(Integer id, String name, String email, String password, List<exampleEntity> moments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.moments = moments;
    }

    public exampleEntity()
    {

    }
}
