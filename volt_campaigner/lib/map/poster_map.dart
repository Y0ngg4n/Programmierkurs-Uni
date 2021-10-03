import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_map_marker_cluster/flutter_map_marker_cluster.dart';
import 'package:latlong2/latlong.dart';
import 'package:flutter_map_location_marker/flutter_map_location_marker.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';


class PosterMapView extends StatefulWidget {
  const PosterMapView({Key? key}) : super(key: key);

  @override
  _PosterMapViewState createState() => _PosterMapViewState();
}

class _PosterMapViewState extends State<PosterMapView> {
  // Set default location to Volt Headquarter
  LatLng currentLocation = new LatLng(50.82558, 4.35469);
  List<Marker> markers = [];

  @override
  void initState() {
    super.initState();
    markers.add(Marker(
      anchorPos: AnchorPos.align(AnchorAlign.center),
      height: 30,
      width: 30,
      point: currentLocation,
      builder: (ctx) => Icon(Icons.pin),
    ));
  }

  Widget build(BuildContext context) {
    return FlutterMap(
        children: [LocationMarkerLayerWidget()],
        options: MapOptions(
          center: currentLocation,
          zoom: 13.0,
          plugins: [
            MarkerClusterPlugin(),
          ],
        ),
        layers: [
          TileLayerOptions(
            urlTemplate: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            subdomains: ['a', 'b', 'c'],
            attributionBuilder: (_) {
              return Text("© OpenStreetMap contributors");
            },
          ),
          MarkerLayerOptions(
            markers: [
              Marker(
                width: 80.0,
                height: 80.0,
                point: currentLocation,
                builder: (ctx) => Container(
                  child: FlutterLogo(),
                ),
              ),
            ],
          ),
          MarkerClusterLayerOptions(
            maxClusterRadius: 120,
            size: Size(40, 40),
            fitBoundsOptions: FitBoundsOptions(
              padding: EdgeInsets.all(50),
            ),
            markers: markers,
            polygonOptions: PolygonOptions(
                borderColor: Colors.blueAccent,
                color: Colors.black12,
                borderStrokeWidth: 3),
            builder: (context, markers) {
              return FloatingActionButton(
                child: Text(markers.length.toString()),
                onPressed: null,
              );
            },
          ),
        ],
    );
  }
}
