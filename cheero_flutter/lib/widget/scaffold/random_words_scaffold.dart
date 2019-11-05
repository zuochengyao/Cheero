import 'package:flutter/material.dart';

class RandomWordsScaffold extends Scaffold {
  final AppBar rwsAppBar;
  final Widget rwsBody;

  const RandomWordsScaffold({this.rwsAppBar, this.rwsBody});

  @override
  PreferredSizeWidget get appBar => this.rwsAppBar;

  @override
  Widget get body => this.rwsBody;

  @override
  Widget get bottomNavigationBar {
    return new BottomAppBar(
        shape: const CircularNotchedRectangle(),
        child: Container(height: 50.0));
  }

  @override
  Widget get floatingActionButton {
    return new FloatingActionButton(
      tooltip: 'Add',
      child: new Icon(Icons.add),
      onPressed: null,
    );
  }

  @override
  // TODO: implement floatingActionButtonLocation
  FloatingActionButtonLocation get floatingActionButtonLocation =>
      FloatingActionButtonLocation.centerDocked;
}
