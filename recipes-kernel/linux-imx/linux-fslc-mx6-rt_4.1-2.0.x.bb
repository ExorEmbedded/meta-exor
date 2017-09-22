# Copyright (C) 2012, 2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "Linux real-time kernel based on linux-imx"
DESCRIPTION = "Linux kernel that is based on Freescale's linux-imx, with added real-time capabilities."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
inherit kernel fsl-kernel-localversion
# Put a local version until we have a true SRCREV to point to
LOCALVERSION = "-4.1-2.0.x-imx"
SCMVERSION = "y"
SRCBRANCH = "4.1-2.0.x-imx-RT"
#SRCREV = "0542b88febdb0332faaaf519f5243d24890ba708"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/ExorEmbedded/linux-us03.git;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"
require recipes-kernel/linux/linux-dtb.inc
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
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel.tar.gz
   else
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" "${KERNEL_DEVICETREE}"
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel.tar.gz
   fi

   rm -rf  ${KERNEL_IMAGETYPE}*
   rm -rf *.dtb
}

do_deploy[vardepsexclude] = "DATETIME"

DEPENDS += "lzop-native bc-native"

COMPATIBLE_MACHINE = "(nsom01)"

