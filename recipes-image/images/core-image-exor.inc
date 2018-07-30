QT5_BASE = " \
	qtbase \
	qtmultimedia \
	qtmultimedia-examples \
	qtimageformats \
	qtsvg \
	qtsvg-plugins \
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
"

QT5_WEB = " \
	qtwebengine \
	qtwebengine-examples \
	qtwebkit \
	qtwebkit-examples-examples \
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
"

USOM02_TSN_SW = " \
	rwcontrol \
	deipce-driver \
"

GSTREAMER = " \
	gstreamer1.0-plugins-base-playback \
	gstreamer1.0-meta-base \
	gstreamer1.0-meta-audio \
	gstreamer1.0-meta-video \
	gstreamer1.0-plugins-good-meta \
	gstreamer1.0-plugins-bad-meta \
	${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'commercial', 'gstreamer1.0-plugins-ugly-meta gstreamer1.0-libav', '', d)} \
"

GSTREAMER_append_usom03 = " \
	gstreamer1.0-plugins-imx \
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
	xf86-input-evdev \
	openvpn \
	openssh-sftp-server \
	e2fsprogs \
	gdbserver \
	kernel-modules \
	${GSTREAMER} \
	${QT5_BASE} \
	${QT5_WEB} \
	${TOOLS} \
	${NET_TOOLS} \
	${AUDIO} \
	${WIFI} \
"

IMAGE_INSTALL_append_usom02 += "\
	${USOM02_TSN_SW} \
"

IMAGE_FEATURES_remove = "ssh-server-dropbear"
IMAGE_FEATURES += "ssh-server-openssh tools-profile tools-debug"

inherit populate_sdk_qt5

# Required by qtcreator debugger
TOOLCHAIN_HOST_TASK += "nativesdk-python3-json"

TOOLCHAIN_OUTPUTNAME = "${SDKMACHINE}-exor-${MACHINE}-qt5-x11-sdk-v${DISTRO_VERSION}"
