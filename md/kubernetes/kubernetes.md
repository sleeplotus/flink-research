### Kubernetes
#### Kubernetes Port
[kubernetes中port、target port、node port的对比分析，以及kube-proxy代理](https://blog.csdn.net/xinghun_4/article/details/50492041)
[Kubernetes中的nodePort，targetPort，port的区别和意义(转)](https://www.cnblogs.com/devilwind/articles/8881201.html)
[Kubernetes中的nodePort，targetPort，port的区别和意义](https://blog.csdn.net/u013760355/article/details/70162242)
```
apiVersion: v1
kind: Service
metadata:
  labels:
    name: app1
  name: app1
  namespace: default
spec:
  type: NodePort
  ports:
  - port: 8080
    targetPort: 8080
    nodePort: 30062
  selector:
    name: app1
```

