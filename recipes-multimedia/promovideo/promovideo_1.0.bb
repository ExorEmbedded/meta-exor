SUMMARY = "Exor JSmart promo video"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LIC;md5=befc92c2e2c75b146e7079ed888158da"

SRC_URI = "file://JSmart_Promotional.OGG.tar.bz2 \
		   file://promovideo.desktop \
		   file://promovideo.png \
		   file://promovideo.sh \
		   file://LIC \
"

DEPENDS = "gstreamer"
RDEPENDS_${PN} += "bash"
do_install_append() {
    install -d ${D}/${datadir}/applications
    install -m 0644 ${WORKDIR}/promovideo.desktop ${D}/${datadir}/applications
	install -d ${D}/${datadir}/exorvideo/
	install -m 0644 ${WORKDIR}/JSmart_Promotional.OGG ${D}/${datadir}/exorvideo/
    install -d ${D}/${datadir}/pixmaps
    install -m 0644 ${WORKDIR}/promovideo.png ${D}/${datadir}/pixmaps
	install -d ${D}/${bindir}/
	install -m 0755 ${WORKDIR}/promovideo.sh ${D}/${bindir}/
}

FILES_${PN} = "${datadir}/applications/* ${datadir}/exorvideo/* ${datadir}/pixmaps/* ${bindir}/*"
