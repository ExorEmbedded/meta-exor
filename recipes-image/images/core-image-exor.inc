
IMAGE_INSTALL += "\
	iproute2 \
	canutils \
	nano \
	openssh-sftp-server \
	e2fsprogs \
	gdbserver \
	gtk+3 \
	psplash \
	packagegroup-core-base-utils \
"

IMAGE_FEATURES:remove = "ssh-server-dropbear"
IMAGE_FEATURES += "ssh-server-openssh"

TOOLCHAIN_OUTPUTNAME = "${SDKMACHINE}-exor-${MACHINE}-wayland-sdk-v${DISTRO_VERSION}"
