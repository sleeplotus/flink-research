### Minikube
[Minicube Documents](https://kubernetes.io/docs/tasks/tools/install-minikube/)
#### Start up a local Kubernetes cluster
[Minicube Command Reference](https://github.com/kubernetes/minikube/issues/5860)
```
minikube start --vm-driver=none --registry-mirror=https://registry.docker-cn.com --image-mirror-country=cn --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers
```
