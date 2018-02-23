QT5_BASE = "\
	qtbase \
	qtmultimedia \
	qtimageformats \
"

QT5_GRAPHICS = "\
	qt3d \
	qtcanvas3d \
	qtquickcontrols \
	qtquick1 \
	qtquick1-plugins \
	qtquick1-qmlplugins \
	qtquickcontrols \
	qtquickcontrols-qmlplugins \
	qtquickcontrols2 \
	qtquickcontrols2-qmlplugins \
	qtgraphicaleffects \
	qtsmarthome \
	cinematicexperience \
	qt5nmapcarousedemo \
"

TOOLS = "\
	bash \
	htop \
	lsof \
	nano \
	nmon \
	powertop \
	screen \
	socat \
	sysstat \
	evtest \
	devmem2 \
	i2c-tools \
	gdb \
	procps \
 	strace \
 "

NET_TOOLS = "\
    iperf3 \
	vlan \
	tcpdump \
	iftop \
	iproute2 \
"

GSTREAMER = "\
	gstreamer1.0-meta-base \
	gstreamer1.0-meta-video \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-bad \
"
AUDIO = "\
	alsa-tools \
    alsa-utils \
	pulseaudio \ 
"

#Redpine Wifi firmware
WIFI = "redpine-ods"

IMAGE_INSTALL += "\
	oprofile \
	iproute2 \
	canutils \
	vim \
	jmobile-portable \
	openvpn \
	openssh-sftp-server \
	e2fsprogs \
	gdbserver \
	kernel-modules \
	${GSTREAMER} \
	${QT5_BASE} \
	${TOOLS} \
	${NET_TOOLS} \
    ${AUDIO} \
	${WIFI} \
"

IMAGE_FEATURES_remove = "ssh-server-dropbear"
IMAGE_FEATURES += "ssh-server-openssh"

# Uncomment to include chromium web browser on the board. The build
# requires at least 8GB of free RAM memory otherwise the linking phase may fail
#CORE_IMAGE_EXTRA_INSTALL += "chromium"

ROOTFS_POSTPROCESS_COMMAND += "jmInstall;"

jmInstall() {
   JMobilePkg="${IMAGE_ROOTFS}/home/root/jmobile_portable.tar.gz"
   [ -f ${JMobilePkg} ] && tar xzf ${JMobilePkg} -C ${IMAGE_ROOTFS}/home/root; rm ${JMobilePkg}
}


inherit populate_sdk_qt5

# Required by qtcreator debugger
TOOLCHAIN_HOST_TASK += "nativesdk-python3-json"

TOOLCHAIN_OUTPUTNAME = "${SDKMACHINE}-exor-${MACHINE}-qt5-x11-sdk-${DISTRO_VERSION}"
