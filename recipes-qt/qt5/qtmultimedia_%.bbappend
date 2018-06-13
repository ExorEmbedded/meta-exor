FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://add-autoplay-to-qtvideoplayer.patch"

PACKAGECONFIG += "gstreamer pulseaudio alsa"

do_compile_append () {
    oe_runmake sub-examples
}

do_install_append() {
    oe_runmake sub-examples-install_subtargets INSTALL_ROOT=${D}
}
