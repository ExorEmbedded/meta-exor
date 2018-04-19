do_install () {
    install -d ${D}/${datadir}/pixmaps
    install -d ${D}/${datadir}/applications
    install -m 0644 ${WORKDIR}/cinematicexperience.png ${D}/${datadir}/pixmaps
    install -m 0644 ${WORKDIR}/cinematicexperience.desktop ${D}/${datadir}/applications
    install -m 0644 ${WORKDIR}/qt5nmapcarousedemo.png ${D}/${datadir}/pixmaps
    install -m 0644 ${WORKDIR}/qt5nmapcarousedemo.desktop ${D}/${datadir}/applications
    install -m 0644 ${WORKDIR}/qtsmarthome.png ${D}/${datadir}/pixmaps
    install -m 0644 ${WORKDIR}/qtsmarthome.desktop ${D}/${datadir}/applications
}
