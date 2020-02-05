#### Helm
[Tutorial](https://www.jianshu.com/p/200020e7a843)

[GitHub Releases](https://github.com/helm/helm/releases)

#### Tiller
[Tiller Docker Hub](https://hub.docker.com/r/fishead/gcr.io.kubernetes-helm.tiller/tags)

#### Tutorial
- Install Helm
    - tar -zxvf helm-v2.16.1-linux-amd64.tar.gz
    - mv linux-amd64/helm /usr/local/bin/
    - chmod +x /usr/local/bin/helm
    - helm version
- Install Tiller
    - helm init
    - helm init --upgrade
    - helm init -i gcr.io/kubernetes-helm/tiller:v2.16.1

