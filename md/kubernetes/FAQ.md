### Minikube
#### Q1
- [Question](https://github.com/kubernetes/minikube/issues/4172)
```
failed to run Kubelet: failed to create kubelet: misconfiguration: kubelet cgroup driver: "cgroupfs" is different from docker cgroup driver: "systemd"
```
- Solution
```
--extra-config="kubelet.cgroup-driver=systemd"
```
#### Q2
- [Question](https://github.com/kubernetes/minikube/issues/6061)
```
setting "apiserver.authorization-mode=RBAC" : writing Crisocket information: timed out waiting for the condition
```
- Solution
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