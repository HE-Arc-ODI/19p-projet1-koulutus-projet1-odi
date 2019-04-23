package ch.hearc.odi.koulutus.business;

import ch.hearc.odi.koulutus.exception.ProgramException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.ws.rs.ProcessingException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Program")
@XmlRootElement(name = "Program")
public class Program implements Serializable {

    private Integer id;
    private String name;
    private String richDescription;
    private String field;
    private Integer price;
    private List<Course> courses;

    public Program() {
        courses = new ArrayList<>();
    }

    public Program(String name, String richDescription, String field, Integer price){
        this();
        this.name = name;
        this.richDescription = richDescription;
        this.field = field;
        this.price = price;
    }
    public Program(Integer id, String name, String richDescription, String field, Integer price) {
        this(name, richDescription,field,price);
        this.id = id;
    }

    public Program(Program p, Course c) {
        id = p.getId();
        name = p.getName();
        richDescription = p.getRichDescription();
        field = p.getField();
        price = p.getPrice();
        courses = new ArrayList<>();
        courses.add(c);
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    public String getRichDescription() {
        return richDescription;
    }

    public void setRichDescription(String richDescription) {
        this.richDescription = richDescription;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @XmlElement
    @Transient
    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Integer getIndex(Integer id) throws ProgramException {
        for (int i = 0; i < courses.size(); i++){
            Course c = courses.get(i);
            if (c.getId() == id){
                return i;
            }
        }
        throw new ProgramException("Index not found");
    }
    public void removeCourse(Integer id) throws ProgramException {
        this.courses.remove(getIndex(id));
    }
}
