# oc-hello-service

## Setup CRC

Setup [crc](https://github.com/crc-org/crc/) local environment via below commands:

```bash
crc setup
crc start

eval $(crc podman-env)
```

After that, logon local openshift cluster and create new project:

```bash
oc login -u developer https://api.crc.testing:6443

oc new-project oc-hello-project
```

## Podman

To build image: `podman build -t oc-hello-service:0.1.0-SNAPSHOT .`

To push to local openshift container registry, we need:

1. makesure `oc` command logon local registry: `oc registry login --skip-check`
2. open docker desktop > settings > docker engine, add below to docker engine config:

```json
"insecure-registries": ["default-route-openshift-image-registry.apps-crc.testing"]
```

3. open new shell window, type `podman` command to logon local registry: `echo $(oc whoami -t) | podman login -u developer --password-stdin $(oc registry info) --tls-verify=false`
4. add tag to the image: `podman tag oc-hello-service:0.1.0-SNAPSHOT $(oc registry info)/$(oc project -q)/oc-hello-service:0.1.0-SNAPSHOT`
5. push the image to local registry: `podman push $(oc registry info)/$(oc project -q)/oc-hello-service:0.1.0-SNAPSHOT`

After push the image to local registry, run `oc get imagestream` to list the images in local registry.

## Openshift

Create yaml files. The `route.yaml` is openshift specfic, other yaml files are standard for k8s. Then, apply those files: `oc apply -f ./k8s`

After the application deployed, check the status of pods: `oc get pods`

To access the application. First, get the assigned hostname: `oc get routes`. Then, open browser to access, like: `http://oc-hello-route-oc-hello-project.apps-crc.testing/v1/app/greeting`.
