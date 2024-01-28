package denys.koretskyi.txt2gpx.model.gpx;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.Setter;

import java.util.List;

@Setter
public class Extensions {

    @XmlElement(name = "gpxx:WaypointExtension")
    private List<WaypointExtension> waypointExtensions;
}
