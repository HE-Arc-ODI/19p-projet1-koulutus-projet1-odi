package ch.hearc.odi.koulutus.business;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Course")
@XmlRootElement(name = "Course")
public class Course {

}
