### Minikube
#### Q1
- Question
```
failed to run Kubelet: failed to create kubelet: misconfiguration: kubelet cgroup driver: "cgroupfs" is different from docker cgroup driver: "systemd"
```
- [Solution](https://github.com/kubernetes/minikube/issues/4172)
```
--extra-config="kubelet.cgroup-driver=systemd"
```
- [Solution](https://www.cnblogs.com/hongdada/p/9771857.html)

#### Q2
- Question
```
setting "apiserver.authorization-mode=RBAC" : writing Crisocket information: timed out waiting for the condition
```
- [Solution](https://github.com/kubernetes/minikube/issues/6061)
```
--extra-config="apiserver.authorization-mode=RBAC"
```
#### Q3
- [Question](https://github.com/kubernetes/minikube/issues/4150)
```
Error loading config file "/var/lib/minikube/kubeconfig": open /var/lib/minikube/kubeconfig: permission denied
```
- [Solution](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/#k8s-install-1)
```
# Set SELinux in permissive mode (effectively disabling it)
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config
```
#### Q5
- Question
```
minikube dashbaordX http://127.0.0.1:37619/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/ is not accessible: Temporary Error: unexpected response code: 503
```
- [Solution](https://gist.github.com/F21/08bfc2e3592bed1e931ec40b8d2ab6f5)
```
I was able to get it to work with this. The --bootstrapper option seems to be required when you are running it with --vm-driver=none

minikube start --vm-driver=none --bootstrapper=kubeadm --extra-config=apiserver.authorization-mode=RBAC
```
- [Solution](https://dockerquestions.com/2019/04/22/minikube-running-in-docker-mode-returns-503-when-launching-the-dashboard/)
- [Solution](https://zhuanlan.zhihu.com/p/47185808)

#### Q6
- Question
```
how to access local kubernetes minikube dashboard remotely
```
- [Solution](https://stackoverflow.com/questions/47173463/how-to-access-local-kubernetes-minikube-dashboard-remotely)
```
1. Start a proxy using @Jeff's script, as default it will open a proxy on '0.0.0.0:8001'.

kubectl proxy --address='0.0.0.0' --disable-filter=true

2. Visit the dashboard via the link below:

curl http://your_api_server_ip:8001//api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy
```

#### Q7
- Question
```
socat not found
```
- [Solution](https://github.com/helm/helm/issues/1371）
```
yum install -y socat
```