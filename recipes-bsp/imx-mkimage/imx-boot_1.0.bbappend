
PROVIDES += "bootloader"

do_compile:usom04() {
    bbnote uSom04/i.Mx8MM boot binary build
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        bbnote "Copy ddr_firmware: ${ddr_firmware} from ${DEPLOY_DIR_IMAGE} -> ${BOOT_STAGING} "
        cp ${DEPLOY_DIR_IMAGE}/${ddr_firmware}               ${BOOT_STAGING}
    done
    cp ${DEPLOY_DIR_IMAGE}/signed_dp_imx8m.bin               ${BOOT_STAGING}
    cp ${DEPLOY_DIR_IMAGE}/signed_hdmi_imx8m.bin             ${BOOT_STAGING}
    cp ${DEPLOY_DIR_IMAGE}/u-boot-spl.bin-${MACHINE}-${UBOOT_CONFIG} \
                                                             ${BOOT_STAGING}/u-boot-spl.bin
    cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/${UBOOT_DTB_NAME}   ${BOOT_STAGING}
    cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/u-boot-nodtb.bin    ${BOOT_STAGING}/u-boot-nodtb.bin
    cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/mkimage_uboot       ${BOOT_STAGING}
    cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/${ATF_MACHINE_NAME} ${BOOT_STAGING}/bl31.bin
    cp ${DEPLOY_DIR_IMAGE}/${UBOOT_NAME}                     ${BOOT_STAGING}/u-boot.bin

    # mkimage for i.MX8
    if ${DEPLOY_OPTEE}; then
        cp ${DEPLOY_DIR_IMAGE}/tee.bin ${BOOT_STAGING}
    fi
    for target in ${IMXBOOT_TARGETS}; do
        bbnote "building ${SOC_TARGET} - ${target}"

        make SOC=${IMX_BOOT_SOC_TARGET} dtbs=${UBOOT_DTB_NAME} ${target}

        bbnote asd

        if [ -e "${BOOT_STAGING}/flash.bin" ]; then
            cp ${BOOT_STAGING}/flash.bin ${S}/${BOOT_CONFIG_MACHINE}-${target}
            cp ${BOOT_STAGING}/flash.bin ${S}/flash.bin
        fi
    done
}

do_deploy:append:usom04() {
    cd ${DEPLOYDIR}
    cp ${S}/flash.bin bootloader-full-fw.img

    tar czvf "${MACHINE}-bootloader-${DATETIME}.tar.gz" bootloader-full-fw.img
    ln -sf "${MACHINE}-bootloader-${DATETIME}.tar.gz" ${MACHINE}-bootloader.tar.gz
}

do_deploy[vardepsexclude] = "DATETIME"

UBOOT_NAME = "u-boot.bin-${UBOOT_CONFIG}"
