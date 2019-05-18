iptables -t nat -F OUTPUT
iptables -t nat -F PREROUTING
iptables -t mangle -F OUTPUT
iptables -t mangle -F PREROUTING
iptables -t mangle -P OUTPUT ACCEPT
iptables -t mangle -P PREROUTING ACCEPT

#-------------JJ防跳ForKMG-----------------#
DIR=/data/data/android.kmg/files
source $DIR/*.conf

for A in nat mangle;do
for B in lo tun0 wlan0;do
iptables -t $A -A OUTPUT -o $B -j ACCEPT;done;done
for A in lo tun0 wlan0;do
iptables -t mangle -A PREROUTING -i $A -j ACCEPT;done
for A in PREROUTING OUTPUT;do
iptables -t mangle -P $A DROP
iptables -t mangle -A $A -p udp -j ACCEPT
iptables -t mangle -A $A -p tcp -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT;done
iptables -t nat -A OUTPUT -m owner --uid-owner $uid$user -p tcp -j ACCEPT
iptables -t nat -A OUTPUT -s 192.168/16 -j ACCEPT
iptables -t nat -A PREROUTING -s 192.168/16 -d 192.168/16 -j ACCEPT
iptables -t nat -A PREROUTING -s 192.168/16 -p udp --dport 53 -j REDIRECT --to $dns_listen_port
iptables -t nat -A PREROUTING -s 192.168/16 -p tcp -j REDIRECT --to $listen_port
iptables -t nat -A PREROUTING -s 192.168/16 -p udp ! --dport 53 -j DNAT --to 127.0.0.1
iptables -t nat -A OUTPUT -p udp --dport 53 -j REDIRECT --to $dns_listen_port
iptables -t nat -A OUTPUT -p tcp -j REDIRECT --to $listen_port
iptables -t nat -A OUTPUT -p udp ! --dport 53 -j DNAT --to 127.0.0.1
