# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2019 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# Use in-tree defconfig
do_preconfigure[noexec] = "1"
KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} O=${B} ${KERNEL_MACHINE}_defconfig"

inherit kernel 

S = "${WORKDIR}/git"

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "4.14-1.0.0-imx"
LOCALVERSION = "-imx"
SRC_URI = "git://github.com/ExorEmbedded/linux-us03.git;protocol=http;branch=${SRCBRANCH}"
SRCREV = "ed148c4fb1003c11c8a9923e656fd33c3be8be19"

LINUX_VERSION = "4.14"

PROVIDES += "linux kernel"

do_deploy () {
   [ -d "${DEPLOYDIR}/kernel-deploy" ] && rm -r "${DEPLOYDIR}/kernel-deploy"

   install -d "${DEPLOYDIR}/kernel-deploy"
   install -m 0644 "${B}/arch/arm64/boot/${KERNEL_IMAGETYPE}" "${DEPLOYDIR}/kernel-deploy"

   for DTB in ${KERNEL_DEVICETREE}; do
      install -m 0644 "${B}/arch/arm64/boot/dts/${DTB}" "${DEPLOYDIR}/kernel-deploy"
   done

   cd "${DEPLOYDIR}"

   tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" -C "${DEPLOYDIR}/kernel-deploy" .
   ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel.tar.gz

   rm -r "${DEPLOYDIR}/kernel-deploy"

   # Avoid parsing from deploy appends
   return 0
}

do_deploy[vardepsexclude] = "DATETIME"
COMPATIBLE_MACHINE = "(usom04)"
