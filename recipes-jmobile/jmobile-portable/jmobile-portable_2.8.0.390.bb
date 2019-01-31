DESCRIPTION = "JMobile Portable"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ce29dbb849109f28c0a0358e8fedbc64"

JM_PKG_FILE="jmobile_${PV}_portable_devkit.tar.gz"

SRC_URI = "file://jmobile.desktop \
	${EXOR_FTP}/OpenHMI/${JM_PKG_FILE};unpack=0 \
"

SRC_URI[md5sum] = "adb4740114e2231e6042b339130a191c"
SRC_URI[sha256sum] = "e5e78d2477e313da141205ac2bfb9f1cd85a17ee55b15eee0babdf73f95976a2"

S = "${WORKDIR}/jmobile_portable"

do_unpack_license () {
   tar xzf ${WORKDIR}/${JM_PKG_FILE} -C ${WORKDIR} jmobile_portable/LICENSE.txt
}

do_install() {
   install -d ${D}${datadir}/applications
   install -m 0644 ${WORKDIR}/jmobile.desktop ${D}${datadir}/applications/
}

fakeroot do_fakeroot_install() {
   mkdir -p ${D}/opt
   tar xpzf ${WORKDIR}/${JM_PKG_FILE} -C ${D}/opt
}

python populate_packages_prepend() {
    import os
    portable_dir = d.getVar('D')
    pn = d.getVar('PN')
    dirs = [ x[0][ len(portable_dir) :] for x in os.walk(portable_dir + "/opt/jmobile_portable") ]
    dirs = dirs + [ "/usr", "/usr/share/", "/usr/share/applications/" ]
    d.setVar('FILES_' + pn, " ".join(dirs))
}

addtask unpack_license after do_unpack before do_populate_lic
addtask fakeroot_install after do_install before do_package
deltask package_qa

EXCLUDE_FROM_SHLIBS = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_DEFAULT_DEPS = "1"
SKIP_FILEDEPS = "1"
