# Copyright (C) 2017 Exor int 
SUMMARY = "Linux real-time kernel based on linux-imx"
DESCRIPTION = "Linux kernel that is based on Freescale's linux-imx, with added real-time capabilities."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
inherit kernel fsl-kernel-localversion
# Put a local version until we have a true SRCREV to point to
LOCALVERSION = "-4.1-2.0.x-imx"
SCMVERSION = "y"
SRCBRANCH = "4.1-2.0.x-imx-RT"
SRCREV = "4a49d7884bb09488318f49c78f584f030cb2c487"

SRC_URI = "git://github.com/ExorEmbedded/linux-us03.git;branch=${SRCBRANCH}"
SRC_URI_append_ns01-ek435 += "file://0001-Redpine-wifi-driver-OSD-1.3-RC9.patch "
S = "${WORKDIR}/git"
#require recipes-kernel/linux/linux-dtb.inc
require ../linux-exor.inc

PROVIDES += "linux kernel"

do_deploy () {
   install -d "${DEPLOYDIR}"
   install -m 0644 "${B}/arch/arm/boot/zImage" "${DEPLOYDIR}"
   for DTB in ${KERNEL_DEVICETREE}; do
      install -m 0644 "${B}/arch/arm/boot/dts/${DTB}" "${DEPLOYDIR}"
   done

   cd "${DEPLOYDIR}"

   if [ -n "${DTB_TARGET}" ] ; then
                mv ${KERNEL_DEVICETREE} ${DTB_TARGET}
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" ${DTB_TARGET}
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel-v${DISTRO_VERSION}.tar.gz
   else
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" "${KERNEL_DEVICETREE}"
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel-v${DISTRO_VERSION}.tar.gz
   fi

   rm -rf  ${KERNEL_IMAGETYPE}*
   rm -rf *.dtb
}

do_deploy[vardepsexclude] = "DATETIME"

DEPENDS += "lzop-native bc-native"

COMPATIBLE_MACHINE = "(nsom01)"

