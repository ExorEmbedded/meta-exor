IMAGE_FSTYPES = "tar.gz"
IMAGE_NAME_SUFFIX = ""

MKUBIFS_ARGS = "-F -m 2048 -e 126976 -c 4095"
UBINIZE_ARGS = "-m 2048 -p 128KiB -s 512 -O 2048"
UBI_VOLNAME = "rootfs"

MACHINE_EXTRA_RRECOMMENDS += "kernel-devicetree kernel-modules"

MACHINE_FEATURES = "serial rtc"
