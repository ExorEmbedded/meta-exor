DESCRIPTION = "U-Boot - the Universal Boot Loader"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

inherit uboot-config deploy

S = "${WORKDIR}/git"

# Set these to exclusively build only first or second stage
SPL_ONLY ?= "0"
UBOOT_ONLY ?= "0"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

# Allow setting an additional version string that will be picked up by the
# u-boot build system and appended to the u-boot version.  If the .scmversion
# file already exists it will not be overwritten.
UBOOT_LOCALVERSION ?= ""

UBOOT_SUFFIX = "img"
UBOOT_IMAGE = "u-boot.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET = "all"

# Some versions of u-boot build an SPL (Second Program Loader) image that
# should be packaged along with the u-boot binary as well as placed in the
# deploy directory.  For those versions they can set the following variables
# to allow packaging the SPL.
SPL_SUFFIX ?= "bin"
SPL_BINARY ?= "xloader.${SPL_SUFFIX}"

do_compile () {
	if [ "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld$/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi

	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	unset LC_ALL

	if [ ! -e ${B}/.scmversion -a ! -e ${S}/.scmversion ]
	then
		echo ${UBOOT_LOCALVERSION} > ${B}/.scmversion
		echo ${UBOOT_LOCALVERSION} > ${S}/.scmversion
	fi

	oe_runmake ${UBOOT_MACHINE}
	oe_runmake ${UBOOT_MAKE_TARGET}
}

do_deploy () {
    install -d ${DEPLOYDIR}

    if [ "${UBOOT_ONLY}" = "1" ]; then
        install ${S}/${UBOOT_IMAGE} ${DEPLOYDIR}

        cd ${DEPLOYDIR}
        tar czvf ${MACHINE}-uboot-v${DISTRO_VERSION}.tar.gz ${UBOOT_IMAGE}
        rm ${UBOOT_IMAGE}

    elif [ "${SPL_ONLY}" = "1" ]; then
        install ${S}/${SPL_BINARY} ${DEPLOYDIR}

        cd ${DEPLOYDIR}
        tar czvf ${MACHINE}-xloader-v${DISTRO_VERSION}.tar.gz ${SPL_BINARY}
        rm ${SPL_BINARY}

    fi
}

addtask deploy before do_build after do_compile
