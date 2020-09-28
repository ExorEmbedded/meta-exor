# Copyright 2013-2016 (C) Freescale Semiconductor
# Copyright 2017-2019 (C) NXP
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

# Use in-tree defconfig
do_preconfigure[noexec] = "1"
KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} O=${B} ${KERNEL_MACHINE}_defconfig"

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "4.14-1.0.0-imx"
LOCALVERSION = "-imx"
SRCREV = "ffa3dc639426d64a2a28101d64c5f9dbba55d1b0"
SRC_URI = "git://github.com/ExorEmbedded/linux-us03.git;branch=${SRCBRANCH} \
           file://0001-Moved-GPU-Vivante-driver-from-6.2.4.p2.163672-to-6.2.patch"

DEFAULT_PREFERENCE = "1"

PROVIDES += "linux kernel"

do_deploy () {
   install -d "${DEPLOYDIR}"
   install -m 0644 "${B}/arch/arm/boot/zImage" "${DEPLOYDIR}"
   for DTB in ${KERNEL_DEVICETREE}; do
      install -m 0644 "${B}/arch/arm/boot/dts/${DTB}" "${DEPLOYDIR}"
      install -m 0644 "${B}/arch/arm/boot/dts/${DTB}" "${D}/boot/"
   done

   cd "${DEPLOYDIR}"

   if [ -n "${DTB_TARGET}" ] ; then
                mv ${KERNEL_DEVICETREE} ${DTB_TARGET}
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" ${DTB_TARGET}
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel-v${DISTRO_VERSION}.tar.gz
   else
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" ${KERNEL_DEVICETREE}
                ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel-v${DISTRO_VERSION}.tar.gz
   fi

   rm -rf  ${KERNEL_IMAGETYPE}*
   rm -rf *.dtb
}

do_deploy[vardepsexclude] = "DATETIME"
COMPATIBLE_MACHINE = "(nsom01|usom03)"

