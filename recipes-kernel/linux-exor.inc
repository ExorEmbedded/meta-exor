# Use in-tree defconfig
do_preconfigure[noexec] = "1"
KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} O=${B} ${KERNEL_MACHINE}_defconfig"

inherit kernel

PV = "${LINUX_VERSION}+git${SRCPV}"
S = "${WORKDIR}/git"
B = "${S}"

KERNEL_IMAGETYPE = "zImage"
KERNEL_EXTRA_ARGS = "dtbs"

KERNEL_OUTPUT = "arch/arm/boot"
DTB_OUTPUT = "${KERNEL_OUTPUT}/dts"

PROVIDES += "linux kernel"

do_deploy_append() {

   install -d "${DEPLOYDIR}"
   install -m 0644 "${B}/${KERNEL_OUTPUT}/${KERNEL_IMAGETYPE}" "${DEPLOYDIR}"
   for DTB in ${KERNEL_DEVICETREE}; do
      install -m 0644 "${B}/${DTB_OUTPUT}/${DTB}" "${DEPLOYDIR}"
   done

   cd "${DEPLOYDIR}"

   if [ -n "${DTB_TARGET}" ] ; then
                mv ${KERNEL_DEVICETREE} ${DTB_TARGET}
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" ${DTB_TARGET}
   else
                tar czvf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" "${KERNEL_IMAGETYPE}" ${KERNEL_DEVICETREE}
   fi

   ln -sf "${MACHINE}-kernel-${KERNEL_VERSION}-${DATETIME}.tar.gz" ${MACHINE}-kernel-v${DISTRO_VERSION}.tar.gz

   rm -rf  ${KERNEL_IMAGETYPE}*
   rm -rf *.dtb
}

do_deploy[vardepsexclude] = "DATETIME"

RDEPENDS_${KERNEL_PACKAGE_NAME}-base = ""
