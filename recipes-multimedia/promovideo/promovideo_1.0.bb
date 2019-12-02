SUMMARY = "Exor JSmart promo video"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LIC;md5=befc92c2e2c75b146e7079ed888158da"

SRC_URI = "file://JSmart_320x240_25fps_audio.ogg.tgz \
	file://JSmart_720x480_25fps_h264_noaudio.avi.tgz \
	file://organfinale.wav \
	file://promovideo.desktop.ogg \
	file://promovideo.desktop.h264 \
	file://kitcamera.desktop \
	file://promovideo.png \
	file://kitcamera.png \
	file://kitcamera.sh \
	file://LIC \
"

DEPENDS = "gstreamer1.0"
RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}/${datadir}/applications
	install -m 0644 ${WORKDIR}/promovideo.desktop.ogg ${D}/${datadir}/applications/promovideo.desktop
	install -d ${D}/${datadir}/exorvideo/
	install -m 0644 ${WORKDIR}/JSmart_320x240_25fps_audio.ogg ${D}/${datadir}/exorvideo/
	install -d ${D}/${datadir}/testaudio/
	install -m 0644 ${WORKDIR}/organfinale.wav ${D}/${datadir}/testaudio/
	install -d ${D}/${datadir}/pixmaps
	install -m 0644 ${WORKDIR}/promovideo.png ${D}/${datadir}/pixmaps
}

do_install_usom03() {
	install -d ${D}/${datadir}/applications
	install -m 0644 ${WORKDIR}/promovideo.desktop.h264 ${D}/${datadir}/applications/promovideo.desktop
	install -d ${D}/${datadir}/exorvideo/
	install -m 0644 ${WORKDIR}/JSmart_720x480_25fps_h264_noaudio.avi ${D}/${datadir}/exorvideo/
	install -d ${D}/${datadir}/pixmaps
	install -m 0644 ${WORKDIR}/promovideo.png ${D}/${datadir}/pixmaps
}


do_install_append_nsom02() {
	install -d ${D}/${bindir}/
	install -m 0644 ${WORKDIR}/kitcamera.sh ${D}/${bindir}/kitcamera.sh
	install -d ${D}/${datadir}/applications
	install -m 0644 ${WORKDIR}/kitcamera.desktop ${D}/${datadir}/applications/kitcamera.desktop
	install -d ${D}/${datadir}/pixmaps
	install -m 0644 ${WORKDIR}/kitcamera.png ${D}/${datadir}/pixmaps
}

FILES_${PN} = "${datadir}/applications/* ${datadir}/exorvideo/* ${datadir}/testaudio/* ${datadir}/pixmaps/* ${bindir}/"
