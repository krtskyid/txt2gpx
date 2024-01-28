package denys.koretskyi.txt2gpx.model.gpx;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
public class Waypoint {

    @XmlAttribute
    private String lat;
    @XmlAttribute
    private String lon;

    @XmlElement
    private String ele;
    @XmlElement
    private String name;
    @XmlElement
    private String sym;
    @XmlElement
    private Extensions extensions;
}
