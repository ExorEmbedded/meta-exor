FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://xinput_calibrator.desktop \
	file://30xinput_calibrate.sh_disable \
"

do_install_append() {
    rm ${D}${sysconfdir}/xdg/autostart/xinput_calibrator.desktop

    # Replace /usr/share/applications/xinput_calibrator.desktop
    install -m 755 ${WORKDIR}/xinput_calibrator.desktop ${D}/${datadir}/applications/
    install -m 755 ${WORKDIR}/30xinput_calibrate.sh_disable  ${D}/${sysconfdir}/X11/Xsession.d/30xinput_calibrate.sh
}

do_install_append_usom01() {
    install -m 755 ${WORKDIR}/30xinput_calibrate.sh  ${D}/${sysconfdir}/X11/Xsession.d/30xinput_calibrate.sh
}
