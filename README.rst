android-animation-backport
===========================

Backport of the Android 4.0 property animation framework to Android 1.5+.

* All the classes in `android.animation` are present, except for `LayoutTransition` as it requires `View.OnLayoutChangeListener` which only exists starting from Android 4.0. As this is missing an integral part of View, this is non-trivial to port.

* The `java.util.*Property` classes were also backported.

* The backported classes have been moved to `backports.animation` (from `android.animation`) and `backports.property` (from `android.util`).

Usage
========

See http://developer.android.com/guide/developing/projects/projects-eclipse.html#ReferencingLibraryProject .

Authors
============

* Google

* Backported by Wladimir J. van der Laan

