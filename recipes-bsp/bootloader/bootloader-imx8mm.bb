# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.

DESCRIPTION = "i.MX U-Boot suppporting i.MX reference boards."
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCBRANCH = "imx_v2018.03"
SRC_URI = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=http;branch=${SRCBRANCH} \
	file://0001-fix-pound-escaping-in-makefile.patch"

SRCREV = "5da4d379feaacbe316eb8ecc6a8a3d5d0fcfd759"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"
UBOOT_INITIAL_ENV = ""

do_deploy:append:mx8mm-generic-bsp() {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8m*-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    if [ -e "${DEPLOY_DIR_IMAGE}/${UBOOT_DTB_IMAGE}" ]
                    then
                        install -m 0777 ${DEPLOY_DIR_IMAGE}/${UBOOT_DTB_IMAGE} ${DEPLOYDIR}/${BOOT_TOOLS}/${UBOOT_DTB_NAME}
                    else
                        install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME} ${DEPLOYDIR}/${BOOT_TOOLS}
                    fi
                    install -m 0777 ${B}/${config}/tools/mkimage ${DEPLOYDIR}/${BOOT_TOOLS}/mkimage_uboot
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin
                fi
            done
            unset  j
        done
        unset  i
    fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(usom04)"
