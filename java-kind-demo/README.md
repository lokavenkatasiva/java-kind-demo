# java-kind-demo

Spring Boot app deployed to a KIND cluster via Jenkins.

## Structure

```
java-kind-demo
├── src
├── pom.xml
├── Dockerfile
├── Jenkinsfile
├── deployment.yaml
└── service.yaml
```

## Build & run locally

```bash
mvn clean package
java -jar target/app.jar
curl localhost:8080/hello
```

## Docker

```bash
docker build -t java-kind-demo:latest .
docker run -p 8080:8080 java-kind-demo:latest
```

## KIND

Create a cluster with port 30080 mapped to the host:

```bash
cat <<EOF | kind create cluster --config=-
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
  - role: control-plane
    extraPortMappings:
      - containerPort: 30080
        hostPort: 30080
EOF

kind load docker-image java-kind-demo:latest
kubectl apply -f deployment.yaml -f service.yaml
curl localhost:30080
```
