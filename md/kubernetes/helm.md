#### Helm
[Tutorial](https://www.jianshu.com/p/200020e7a843)

[GitHub Releases](https://github.com/helm/helm/releases)

#### Tiller
[Tiller Docker Hub](https://hub.docker.com/r/fishead/gcr.io.kubernetes-helm.tiller/tags)

#### Tutorial
##### Install Helm
- 解压helm-v2.9.1-linux-amd64.tar.gz
```
tar -zxvf helm-v2.9.1-linux-amd64.tar.gz
```
- 移动到/usr/local/bin目录下
```
mv linux-amd64/helm /usr/local/bin/
```
- 添加执行权限
```
chmod +x /usr/local/bin/helm
```
- 测试使用可以使用(获取客户端的版本号) 
```
helm version
```
##### Install Tiller
- 准备镜像，从阿里云上拉取Tiller镜像到本地，然后修改Tag
```
docker pull registry.cn-qingdao.aliyuncs.com/kubernetes_xingej/tiller:v2.9.0

docker tag 2a1d7ef9d530 gcr.io/kubernetes-helm/tiller:v2.9.0
```
- 为tiller创建serviceaccount和clusterrolebinding：tiller的服务端是一个deployment，在kube-system namespace下，会去连接kube-api创建应用和删除，所以需要给他权限.
```
kubectl create serviceaccount --namespace kube-system tiller  
kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
```
- 初始化Tiller
    - 方法1
    ```
    helm init
    helm init --service-account tiller
    helm init --upgrade
    ```
    - 方法2
    ```
    helm init -i gcr.io/kubernetes-helm/tiller:v2.9.0
    helm init --service-account tiller -i gcr.io/kubernetes-helm/tiller:v2.9.0
    ```
- 为应用程序设置serviceaccount
```
kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'
```