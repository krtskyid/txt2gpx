package denys.koretskyi.txt2gpx.model.gpx;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Setter;

@Setter
public class WaypointExtension {

    @XmlAttribute(name = "xmlns:gpxx")
    private String xmlnsGpxx = "http://www.garmin.com/xmlschemas/GpxExtensions/v3";

    @XmlElement(name = "gpxx:DisplayMode")
    private String displayMode;
}
