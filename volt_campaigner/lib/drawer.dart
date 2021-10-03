import 'package:volt_campaigner/map/poster_map.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

import 'map/poster/add_poster.dart';
import 'map/poster/poster_tags.dart';
import 'package:latlong2/latlong.dart';

enum DrawerSelection { POSTER, FLYER }

class DrawerView extends StatefulWidget {
  const DrawerView({Key? key}) : super(key: key);

  @override
  _DrawerViewState createState() => _DrawerViewState();
}

class _DrawerViewState extends State<DrawerView> {
  DrawerSelection drawerSelection = DrawerSelection.POSTER;
  List<PosterTag> posterTypes = [
    new PosterTag(id: "b45ebffb-a559-49dc-87e0-b3b760069d50", name: "Test"),
    new PosterTag(id: "b812c563-4eab-4d77-badd-e1375a597178", name: "Test2"),
    new PosterTag(id: "aea33423-b50f-4505-9179-c3ecc0fa836c", name: "sadasdasdasdasdassadsaa"),
    new PosterTag(id: "7f0e2e16-9ba5-4ed0-b001-2a346d2e68a5", name: "Test2"),
    new PosterTag(id: "a758c8d0-55a5-4432-94f9-e31db4cc76a0", name: "asdasdasdasdasdasdasdsa"),
    new PosterTag(id: "6", name: "Test2"),
    new PosterTag(id: "7", name: "adasdsadasdsadsadsadsad"),
    new PosterTag(id: "8", name: "Test2"),
    new PosterTag(id: "9", name: "asdasdasdasdasdasasassad"),
    new PosterTag(id: "10", name: "Test2")
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: new AppBar(title: Text(AppLocalizations.of(context)!.appTitle)),
      body: _getBody(),
      drawer: Drawer(
        child: ListView(children: [
          DrawerHeader(
            decoration: BoxDecoration(
              color: Colors.blue,
            ),
            child: Text('Drawer Header'),
          ),
          ListTile(
            title: Text(AppLocalizations.of(context)!.poster),
            onTap: () {
              setState(() {
                drawerSelection = DrawerSelection.POSTER;
              });
              Navigator.pop(context);
            },
          ),
          ListTile(
            title: Text(AppLocalizations.of(context)!.flyer),
            onTap: () {
              setState(() {
                drawerSelection = DrawerSelection.FLYER;
              });
              Navigator.pop(context);
            },
          ),
        ]),
      ),
      floatingActionButton: _getFloatingActionButton(),
    );
  }

  Widget _getBody() {
    switch (drawerSelection) {
      case DrawerSelection.POSTER:
        return PosterMapView();
      case DrawerSelection.FLYER:
        return Container();
    }
  }

  Widget _getFloatingActionButton() {
    Widget poster = (FloatingActionButton(
      child: Icon(Icons.add),
      tooltip: AppLocalizations.of(context)!.addPoster,
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => AddPoster(
                    posterTypes: posterTypes,
                    motiveTypes: posterTypes,
                    targetGroupTypes: posterTypes,
                    environmentTypes: posterTypes,
                    otherTypes: posterTypes,
                    location: LatLng(50, 30),
                  )),
        );
      },
    ));

    switch (drawerSelection) {
      case DrawerSelection.POSTER:
        return poster;
      case DrawerSelection.FLYER:
        return Container();
    }
  }
}
