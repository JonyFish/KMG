iptables -t nat -F OUTPUT
iptables -t nat -F PREROUTING
iptables -t mangle -F OUTPUT
iptables -t mangle -F PREROUTING
iptables -t mangle -P OUTPUT ACCEPT
iptables -t mangle -P PREROUTING ACCEPT
