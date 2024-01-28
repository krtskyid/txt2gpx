package denys.koretskyi.txt2gpx.service;

import denys.koretskyi.txt2gpx.model.gpx.Gpx;

import java.util.List;

public interface Txt2GpxFileService {

    List<String> splitByLines(String input);

    Gpx txtToGpx(List<String> txtLines);

    byte[] convertTo1251Charset(byte[] bytes);
}
