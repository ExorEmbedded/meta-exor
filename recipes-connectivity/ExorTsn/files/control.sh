#!/bin/sh

set -e

[ -z "$bindir" ] && bindir=/home/root/bin
[ -z "$cfgdir" ] && cfgdir=/home/root
[ -z "$moddir" ] && moddir=/home/root/drivers

[ -z "$IP" ] && IP=/home/root/tools/iproute2/ip/ip
[ -z "$VCONFIG" ] && VCONFIG=$bindir/vconfig
[ -z "$IPROUTE2" ] && IPROUTE2=$bindir/ip
[ -z "$IPERF" ] && IPERF=$bindir/iperf
[ -z "$PTPD" ] && PTPD=$bindir/ptp4l
[ -z "$CHRT" ] && CHRT=$bindir/chrt
[ -z "$REGRW" ] && REGRW=$bindir/regrw

[ -z "$IFACE" ] && IFACE=SE01

[ -z "$VID_H" ] && VID_H=77
[ -z "$VPRIO_H" ] && VPRIO_H=7
[ -z "$VIFACE_H" ] && VIFACE_H=${IFACE}.${VID_H}

[ -z "$VID_L" ] && VID_L=11
[ -z "$VPRIO_L" ] && VPRIO_L=1
[ -z "$VIFACE_L" ] && VIFACE_L=${IFACE}.${VID_L}


MASTER_VIP_H=10.77.77.100
MASTER_VIP_L=10.11.11.100
MASTER_IP=192.168.1.90
SLAVE_VIP_H=10.77.77.101
SLAVE_VIP_L=10.11.11.101
SLAVE_IP=192.168.1.91
VNETMASK=255.255.255.0
NETMASK=255.255.255.0

# kit 0, master PTP e client iperf
if [ "$1" = "master" ]; then
    VIP_H=$MASTER_VIP_H
    VIP_L=$MASTER_VIP_L
    IP=$MASTER_IP
    IPERF_ARGS="-c $SLAVE_VIP_L -r -d -t 3600000 -b 20M"
    PTPD_ARGS="-f $cfgdir/ptp-master.cfg -i $IFACE"

# kit 1, slave PTP e server iperf
elif [ "$1" = "slave" ]; then
    VIP_H=$SLAVE_VIP_H
    VIP_L=$SLAVE_VIP_L
    IP=$SLAVE_IP
    IPERF_ARGS=-s
    PTPD_ARGS="-f $cfgdir/ptp-slave.cfg -i $IFACE"
fi

tmpdir=/tmp/exortest
mkdir -p $tmpdir

#### IPERF -- genera traffico di disturbo

IPERF_PIDFILE=$tmpdir/iperf.pid

start_iperf()
{
    $IPERF $IPERF_ARGS >/dev/null &
    echo $! > $IPERF_PIDFILE
}

stop_iperf()
{
    kill -9 $(cat $IPERF_PIDFILE)
}

#### PTP -- genera clock condiviso tra i due nodi

PTP_PIDFILE=$tmpdir/ptp.pid

start_ptp()
{
    $PTPD $PTPD_ARGS >/dev/null &
    echo $! > $PTP_PIDFILE
}

stop_ptp()
{
    kill $(cat $PTP_PIDFILE)
}

#### VLAN -- configura due interfacce di rete per comunicazione a diverse priorità

start_single_vlan()
{
    vid=$1
    vprio=$2
    vip=$3
    viface=$4
    
    #$VCONFIG add $IFACE $vid >/dev/null
    #for skb_prio in 0 1 2 3 4 5 6 7
    #do
	#	$VCONFIG set_egress_map $viface $skb_prio $vprio >/dev/null
	#	$VCONFIG set_ingress_map $viface $vprio $skb_prio >/dev/null
    #done
    #ifconfig $viface $vip netmask $VNETMASK up
	
	$IPROUTE2 link add link $IFACE name $viface type vlan id $vid ingress-qos-map 0:$vprio 1:$vprio 2:$vprio 3:$vprio 4:$vprio 5:$vprio 6:$vprio 7:$vprio egress-qos-map $vprio:0 $vprio:1 $vprio:2 $vprio:3 $vprio:4 $vprio:5 $vprio:6 $vprio:7
	$IPROUTE2 addr add $vip/24 dev $viface
	$IPROUTE2 link set dev $viface up
}

stop_single_vlan()
{
    viface=$1
    
    #ifconfig $viface down
    #$VCONFIG rem $viface >/dev/null
	$IPROUTE2 link set dev $viface down
	$IPROUTE2 link delete $viface
}

start_vlan()
{
    start_single_vlan $VID_H $VPRIO_H $VIP_H $VIFACE_H
    start_single_vlan $VID_L $VPRIO_L $VIP_L $VIFACE_L
    # linux v4.1 and deipce don't play well regarding VLAN and SWITCHDEV
    #$REGRW s w 16 7 v # vlan 11, each register is 16bit long
    #$REGRW s w 9a 7 v # vlan 77
	$REGRW -Sv -w 16 7
	$REGRW -Sv -w 9a 7
}

stop_vlan()
{
    stop_single_vlan $VIFACE_H
    stop_single_vlan $VIFACE_L
}

#### configuration of deipce driver

start_deipce()
{
    insmod $moddir/flx_eth_mdio/flx_eth_mdio.ko
    insmod $moddir/deipce/deipce.ko
    ifconfig $IFACE $IP netmask $NETMASK
}

stop_deipce()
{
    rmmod deipce
    rmmod flx_eth_mdio
}

reset_phy()
{
    _if=$1
    ifconfig $_if down
    sleep 1
    ifconfig $_if up
}

reset_all_phys()
{
    reset_phy CE01
    reset_phy CE02
}

#### help and usage

help()
{
    echo "uso: $0 nodo [comando] on|off"
    echo
    echo "Su entrambi i nodi va lanciato all'avvio/spegnimento"
    echo "  $0 master|slave on|off"
    echo 
    echo "Sul nodo master si puo' abilitare il traffico di disturbo con"
    echo "  $0 master disturbo on|off"
    echo
    echo "L'IP dello slave si puo' vedere con"
    echo "  $0 master tsn on|off"
    echo
    echo "Per resettare i phy esterni (solo a configurazione attiva)"
    echo "  $0 resetphys"
    exit 1
}

case "$1" in
    master)
	case "$2" in
	    on)
		start_deipce
		start_vlan
		start_ptp
		;;
	    off)
		stop_ptp
		stop_vlan
		stop_deipce
		;;
	    tsn)
		case "$3" in
		    on)
			echo "inviare pkt a $SLAVE_VIP_H"
			;;
		    off)
			echo "inviare pkt a $SLAVE_VIP_L"
			;;
		    *)
			help
			;;
		esac
		;;
	    disturbo)
		case "$3" in
		    on)
			start_iperf
			;;
		    off)
			stop_iperf
			;;
		    *)
			help
			;;
		esac
		;;
	    *)
		help
		;;
	esac
	;;
    slave)
	case "$2" in
	    on)
		start_deipce
		start_vlan
		start_iperf
		start_ptp
		;;
	    off)
		stop_ptp
		stop_iperf
		stop_vlan
		stop_deipce
		;;
	    *)
		help
		;;
	esac
	;;
    resetphys)
	reset_all_phys
	;;
    *)
	help
	;;
esac

exit 0
