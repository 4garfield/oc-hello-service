# oc-hello-service

This document will guide how to build docker images and deploy to kubernetes.

## Docker

To build local docker image: `docker build -t sample/oc-hello-service .`

Run the docker image: `docker run -p 9000:9000 sample/oc-hello-service`, then we can access the expose port 9000 for testing.

## Kubernetes

First, create the public repository in [dockerhub](https://hub.docker.com/).

Generate the yaml files which K8s can use for run and manage our application.

```bash
mkdir k8s

kubectl create deployment oc-hello-service --image 4garfield/oc-hello-service:snapshot -o yaml --dry-run=client > k8s/deployment.yaml

kubectl create service clusterip oc-hello-service --tcp 80:9000 -o yaml --dry-run=client > k8s/service.yaml
```

Then, apply those files to k8s: `kubectl apply -f ./k8s`.

In K8s, the service isn't exposed outside of the cluster network by default. We can forward the HTTP to the service with `kubectl` command: `kubectl port-forward svc/oc-hello-service 9000:80`.

## Private Registry

If using private image registry, then can follow below guide.

Build and push docker image to private registry.

```bash
docker login -u username <private-reg>

docker build -t <private-reg>/<username>/oc-hello-service:snapshot .

docker push <private-reg>/<username>/oc-hello-service:snapshot
```

Create secret in k8s:

```bash
kubectl create secret docker-registry private-secret \
--docker-server=<private-reg> \
--docker-username=<username> \
--docker-password=<password> \
--docker-email=<email>
```

Create `k8s/pod.yaml` file to manage the pod to use private registry.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: oc-hello-service
spec:
  containers:
    - name: oc-hello-service-container
      image: <username>/oc-hello-service:snapshot
  imagePullSecrets:
    - name: private-secret
```

Finally, apply the yaml file: `kubectl apply -f k8s/pod.yaml`
