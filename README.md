# Telegram Bot for converting files from .txt to .gpx

Simple bot-converter from .txt file with comma-separated geodetic data to .gpx file

## Tech stack

Java 17  
Maven  
Spring Boot  
Apache Camel  
Containerized for Cloud Run / GKE / GCE

## CI & CD
### Cloud Build pipelines

* Service is being built from the GitHub repository by
  GCP Cloud Build after running one of Cloud Build triggers. 
  There are multiple triggers for Cloud Run and GCE VM f1-micro (free tier) deployment
* Built images are being pushed
  to according Cloud Registry
  repository  
  Images older than 1 day are automatically deleted from the Registry repo by the cleanup policy
* After pushing to Registry, the image is deployed
  either as Cloud Run service or as GCE f1-micro instance

## Telegram bot link

https://t.me/txt2gpx_bot

Input .txt file example:

```txt
Т31,48.56021885,39.15971512,64.954,
Т30,48.56024909,39.16018710,65.582,
1391,48.54691486,39.19655908,141.418,труба жел.400
```

Input .txt file fields explanation:

| Ordering |  Description  | Optional |
|:--------:|:-------------:|:--------:|
|    1     | Waypoint name |    -     |
|    2     |   Latitude    |    -     |
|    3     |   Longitude   |    -     |
|    4     |   Elevation   |    -     |
|    5     |  Description  |    +     |

Output .gpx file example:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<gpx xsi:schemaLocation="http://www.garmin.com/xmlschemas/GpxExtensions/v3 http://www.garmin.com/xmlschemas/GpxExtensions/v3/GpxExtensionsv3.xsd http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd"
     xmlns="http://www.topografix.com/GPX/1/1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <wpt lat="48.56021885" lon="39.15971512">
        <ele>64.954</ele>
        <name>Т31</name>
        <sym>Waypoint</sym>
        <extensions>
            <gpxx:WaypointExtension xmlns:gpxx="http://www.garmin.com/xmlschemas/GpxExtensions/v3">
                <gpxx:DisplayMode>SymbolAndName</gpxx:DisplayMode>
            </gpxx:WaypointExtension>
        </extensions>
    </wpt>
    <wpt lat="48.56024909" lon="39.16018710">
        <ele>65.582</ele>
        <name>Т30</name>
        <sym>Waypoint</sym>
        <extensions>
            <gpxx:WaypointExtension xmlns:gpxx="http://www.garmin.com/xmlschemas/GpxExtensions/v3">
                <gpxx:DisplayMode>SymbolAndName</gpxx:DisplayMode>
            </gpxx:WaypointExtension>
        </extensions>
    </wpt>
    <wpt lat="48.54691486" lon="39.19655908">
        <ele>141.418</ele>
        <name>1391</name>
        <sym>Waypoint</sym>
        <extensions>
            <gpxx:WaypointExtension xmlns:gpxx="http://www.garmin.com/xmlschemas/GpxExtensions/v3">
                <gpxx:DisplayMode>SymbolAndName</gpxx:DisplayMode>
            </gpxx:WaypointExtension>
        </extensions>
    </wpt>
</gpx>
```
