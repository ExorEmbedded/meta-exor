QT5_BASE = " \
	qtbase \
	qtmultimedia \
	qtmultimedia-examples \
	qtimageformats \
	qtsvg \
	qtsvg-plugins \
	qt3d \
	qtquickcontrols \
	qtquickcontrols \
	qtquickcontrols-qmlplugins \
	qtquickcontrols2 \
	qtquickcontrols2-qmlplugins \
	qtgraphicaleffects \
	qtserialbus \
"

QT5_WEB = " \
	qtwebengine \
	qtwebengine-examples \
	qtwebkit \
"

QT5_DEMO = " \
	qtsmarthome \
	cinematicexperience \
	qt5nmapcarousedemo \
	qt5-demo-extrafiles \
"

TOOLS = " \
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
	procps \
	hwlatdetect \
	gnome-system-monitor \
	rt-tests \
"

NET_TOOLS = " \
	iperf3 \
	vlan \
	tcpdump \
	iftop \
	iproute2 \
	linuxptp \
	ethtool \
"

USOM02_TSN_SW = " \
	rwcontrol \
	deipce-driver \
"

AUDIO = "\
	alsa-tools \
	alsa-utils \
	pulseaudio \ 
"

IMAGE_INSTALL += "\
	oprofile \
	iproute2 \
	canutils \
	vim \
	openhmi-portable \
	xf86-input-evdev \
	openvpn \
	openssh-sftp-server \
	e2fsprogs \
	gdbserver \
	kernel-modules \
	${QT5_BASE} \
	${QT5_WEB} \
	${TOOLS} \
	${NET_TOOLS} \
	${AUDIO} \
"

IMAGE_FEATURES_remove = "ssh-server-dropbear"
IMAGE_FEATURES += "ssh-server-openssh tools-profile tools-debug"

inherit populate_sdk_qt5

# Required by qtcreator debugger
TOOLCHAIN_HOST_TASK += "nativesdk-python3-json"

TOOLCHAIN_OUTPUTNAME = "${SDKMACHINE}-exor-${MACHINE}-qt5-x11-sdk-v${DISTRO_VERSION}"
