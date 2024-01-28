package denys.koretskyi.txt2gpx.model.gpx;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.List;

@XmlRootElement
@Setter
public class Gpx {

    @XmlAttribute(name="xsi:schemaLocation")
    private String xsiSchemaLocation = "http://www.garmin.com/xmlschemas/GpxExtensions/v3 http://www.garmin.com/xmlschemas/GpxExtensions/v3/GpxExtensionsv3.xsd http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd";
    @XmlAttribute(name="xmlns")
    private String xmlns = "http://www.topografix.com/GPX/1/1";
    @XmlAttribute(name="xmlns:xsi")
    private String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";

    @XmlElement(name = "wpt")
    private List<Waypoint> waypoints;
}
