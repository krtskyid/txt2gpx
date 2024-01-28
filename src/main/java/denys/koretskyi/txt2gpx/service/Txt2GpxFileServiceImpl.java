package denys.koretskyi.txt2gpx.service;

import denys.koretskyi.txt2gpx.model.gpx.Extensions;
import denys.koretskyi.txt2gpx.model.gpx.Gpx;
import denys.koretskyi.txt2gpx.model.gpx.Waypoint;
import denys.koretskyi.txt2gpx.model.gpx.WaypointExtension;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static denys.koretskyi.txt2gpx.util.Constants.DISPLAY_MODE_SYMBOL_AND_NAME;
import static denys.koretskyi.txt2gpx.util.Constants.SYM_WAYPOINT;
import static denys.koretskyi.txt2gpx.util.Constants.WINDOWS_1251;

@Service
public class Txt2GpxFileServiceImpl implements Txt2GpxFileService {

    @Override
    public List<String> splitByLines(String input) {
        return input.lines().toList();
    }

    @Override
    public Gpx txtToGpx(List<String> txtLines) {
        List<Waypoint> waypoints = txtLines.stream()
                .map(line -> line.split(","))
                .map(splitLine -> Arrays.stream(splitLine).map(String::trim).toArray(String[]::new))
                .map(splitLine -> {
                    Extensions extensions = createExtensions();
                    return createWaypoint(splitLine, extensions);
                })
                .toList();
        Gpx gpx = new Gpx();
        gpx.setWaypoints(waypoints);
        return gpx;
    }

    @Override
    @SneakyThrows
    public byte[] convertTo1251Charset(byte[] bytes) {
        return new String(bytes, WINDOWS_1251).getBytes();
    }

    private Waypoint createWaypoint(String[] fields, Extensions extensions) {
        Waypoint waypoint = new Waypoint();
        waypoint.setName(fields[0]);
        waypoint.setLat(fields[1]);
        waypoint.setLon(fields[2]);
        waypoint.setEle(fields[3]);
        waypoint.setSym(SYM_WAYPOINT);
        waypoint.setExtensions(extensions);
        return waypoint;
    }

    private Extensions createExtensions() {
        Extensions extensions = new Extensions();
        WaypointExtension waypointExtension = new WaypointExtension();
        waypointExtension.setDisplayMode(DISPLAY_MODE_SYMBOL_AND_NAME);
        extensions.setWaypointExtensions(List.of(waypointExtension));
        return extensions;
    }
}
