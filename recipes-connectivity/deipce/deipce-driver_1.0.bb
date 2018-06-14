DESCRIPTION = "Deipce driver"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

PR = "r0"

inherit module

S = "${WORKDIR}/drivers/"

SRC_URI = "file://deipce-patched.tar.bz2"

export KERNEL_SRC = "${STAGING_KERNEL_DIR}"

do_compile() {
    ${MAKE}
}

do_install() {
    # install kernel modules
    INSTALLDIR="${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/EXTERNAL/${PN}"
    install -d ${INSTALLDIR}

    install -m 0644 ${WORKDIR}/drivers/deipce/deipce.ko               ${INSTALLDIR}
    install -m 0644 ${WORKDIR}/drivers/flx_fsc/flx_fsc.ko             ${INSTALLDIR}
    install -m 0644 ${WORKDIR}/drivers/flx_pio/flx_pio.ko             ${INSTALLDIR}
    install -m 0644 ${WORKDIR}/drivers/flx_i2c_mdio/flx_i2c_mdio.ko   ${INSTALLDIR}
    install -m 0644 ${WORKDIR}/drivers/flx_eth_mdio/flx_eth_mdio.ko   ${INSTALLDIR}

}


