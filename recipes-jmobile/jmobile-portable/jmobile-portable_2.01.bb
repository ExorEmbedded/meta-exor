DESCRIPTION = "JMobile Portable"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ce29dbb849109f28c0a0358e8fedbc64"

PR = "2"
PV = "233"

JM_URI          = "${EXOR_FTP}/usom01/JMobile/jmobile2.01-${PV}-${PR}_portable_us01kit_cds3.tar.gz"
JM_URI_us02-kit = "${EXOR_FTP}/usom02/JMobile/jmobile2.01-${PV}-${PR}_portable_alterakit_cds3.tar.gz"
JM_URI_us03-kit = "${EXOR_FTP}/usom03/JMobile/jmobile2.01-${PV}-${PR}_portable_us03kit_cds3.tar.gz"
JM_URI_nsom01   = "${EXOR_FTP}/usom03/JMobile/jmobile2.01-${PV}-${PR}_portable_us03kit_cds3.tar.gz"

SRC_URI = "file://jmobile.desktop"

S = "${WORKDIR}/portable"

do_fetch () {
	wget ${JM_URI} -O ${WORKDIR}/jmobile_portable.tar.gz
}

do_unpack_license () {
	tar xzf ${WORKDIR}/jmobile_portable.tar.gz -C ${WORKDIR} portable/LICENSE.txt
}

do_install() {
	install -d ${D}${datadir}/applications
	install -m 0644 ${WORKDIR}/jmobile.desktop ${D}${datadir}/applications/
	install -d ${D}/home/root
	install -m 0644 ${WORKDIR}/jmobile_portable.tar.gz ${D}/home/root/
}

FILES_${PN} = "${datadir}/applications/ /home/root/jmobile_portable.tar.gz"

addtask unpack_license after do_unpack before do_populate_lic
