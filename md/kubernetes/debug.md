### Minikube
#### [Q1](https://stackoverflow.com/questions/50161690/minikube-does-not-start-kubectl-connection-to-server-was-refused)
```
For posterity, the solution to this problem was to delete the

.minikube
directory in the user's home directory, and then try again. Often fixes strange minikube problems.
```