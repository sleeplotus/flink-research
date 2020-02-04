### Minikube
#### Minikube Alibaba Resource
[Minikube With NONE (VM)Driver](https://zhuanlan.zhihu.com/p/47185808)

[Minikube - Kubernetes本地实验环境](https://yq.aliyun.com/articles/221687)

[Alibaba Minikube Github](https://github.com/AliyunContainerService/minikube)

[15分钟在笔记本上搭建 Kubernetes + Istio开发环境](https://yq.aliyun.com/articles/672675?spm=a2c4e.11153940.0.0.7dd54cec9MRAfu)

[Reference001](https://blog.csdn.net/qq_26819733/article/details/83591891)
#### Minikube Official Resource
##### Minikube Turotial
[Minicube Documents](https://kubernetes.io/docs/tasks/tools/install-minikube/)

[Turotial](https://blog.csdn.net/qq_26819733/article/details/83591891)
##### Start up a local Kubernetes cluster
[Minicube Command Reference](https://github.com/kubernetes/minikube/issues/5860)
- Start Up Minikube
```
minikube start --vm-driver=none --bootstrapper=kubeadm --extra-config="kubelet.cgroup-driver=systemd" --extra-config="apiserver.authorization-mode=Node,RBAC" --iso-url=https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/iso/minikube-v1.6.2.iso --registry-mirror=https://registry.docker-cn.com --image-mirror-country=cn --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers

minikube status

kubectl create clusterrolebinding add-on-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:default
```
- Start Up Dashboard
```
minikube dashboard &

kubectl proxy --address='0.0.0.0' --disable-filter=true

curl http://your_api_server_ip:8001/api/v1/namespaces/kube-system/services/http:kubernetes-dashboard:/proxy/

minikube addons list
```
- Run a particular image on the cluster
```kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount=kube-system:tiller
kubectl run hello --image=docker.io/mongo
```
- Take a replication controller, service, deployment or pod and expose it as a new Kubernetes Service
```
kubectl expose deployment hello --port=80 --target-port=8000
```
