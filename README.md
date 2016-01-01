# graphviz-webapp
A simple Java Web application deployed as a Docker container and run as part of a [Kubernetes cluster](https://github.com/omerio/kubernetes-graphviz).

## Getting Started

Checkout the project

```
git clone https://github.com/omerio/graphviz-webapp 
cd graphviz-webapp
```

To build a docker image:

```
docker build -t graphviz-webapp .
```

To run the container:

```
docker run -d -p 8080:8080 graphviz-webapp
```

To deploy the image to the Docker hub:

```
# tag the image
docker tag <IMAGE-ID> omerio/graphviz-webapp:latest
# login to the docker hub
docker login --username=blah --password=blah --email=blah@gmail.com
# push the image
docker push omerio/graphviz-webapp
```

Optionally, if you are using Google Container Engine, to deploy the image to the Google Container Registry:

```
# tag the image
docker tag graphviz-webapp gcr.io/<YOUR-PROJECT_ID>/graphviz-webapp
# push the image
gcloud docker push gcr.io/<YOUR-PROJECT_ID>/graphviz-webapp
```

