# currency-microservices-V2

## URLS

#### Currency Exchange Service
- http://localhost:8000/currency-exchange/from/USD/to/BLR

#### Currency Conversion Service
  http://localhost:8100/currency-conversion/from/USD/to/BLR/quantity/10
  http://localhost:8100/currency-conversion-feign/from/USD/to/BLR/quantity/10
  
#### Eureka
- http://localhost:8761/

#### Zipkin
- http://localhost:9411/

#### API GATEWAY
- http://localhost:8765/currency-exchange/from/USD/to/BLR
- http://localhost:8765/currency-conversion/from/USD/to/BLR/quantity/10
- http://localhost:8765/currency-conversion-feign/from/USD/to/BLR/quantity/10
- http://localhost:8765/currency-conversion-new/from/USD/to/BLR/quantity/10


Docker zipkin
docker run -p 9411:9411 openzipkin/zipkin:2.23
Zipkin URL
http://localhost:9411/zipkin/

### Docker Images
To create docker images, you need to run mvn spring-boot:build-image

docker run -d -p8761:8761 neninho/naming-server:0.0.1-SNAPSHOT
docker run -d -p8765:8765 neninho/api-gateway:0.0.1-SNAPSHOT
docker run -d -p8000:8000 neninho/currency-exchange-service:0.0.1-SNAPSHOT
docker run -d -p8100:8100 neninho/currency-conversion-service:0.0.1-SNAPSHOT


### kubernates commands


#### kubectl create will do the deployment, replicaset and pod  
kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE
#### kubectl will publish the service which allow us to access the application
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
#### kubectl will create 3 replicas and set the desired number of instances to 3
kubectl scale deployment hello-world-rest-api --replicas=3
#### set the image name 'hello-world-rest-api', then specify the docker image for the container 'hello-world-rest-api=DUMMY_IMAGE:TEST'
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST
#### delete pod
kubectl delete pod hello-world-rest-api-67c79fd44f-n6c7l
#### shows all running services
kubectl get services
#### get all the components running as part of the master node
kubectl get componentstatuses

#### deploy docker image
kubectl create deployment currency-exchange --image=neninho/currency-exchange-service:0.0.11-SNAPSHOT
kubectl create deployment currency-conversion --image=neninho/currency-conversion-service:0.0.11-SNAPSHOT
#### exposing port for the container
kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000
kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100
#### check if the deployment is ready
kubectl get services

#### ope the project directory
cd /home/andrei/IdeaProjects/currency-microservices-V2/currency-exchange-service
#### get all the deployment infor in Yaml format
kubectl get deployment currency-exchange -o yaml
#### save the deployment config in project directory
kubectl get deployment currency-exchange -o yaml >> deployment.yaml
#### save the service config in project directory
kubectl get service currency-exchange -o yaml >> service.yaml
#### you can change some configurations on yaml files, to check the differences use
kubectl diff -f deployment.yaml
#### to deploy the new configurations
kubectl apply -f deployment.yaml
#### check if the replicas are created as per deployment.yaml
kubectl get pods
#### delete all pods/services/deployments and replicaset related with the app
kubectl delete all -l app=currency-exchange
kubectl delete all -l app=currency-conversion
#### recreating the entire currency exchange from the deployment.yaml file 
kubectl apply -f deployment.yaml
#### after recreate check if the pods are ok
kubectl get pods
#### check the service as well
kubectl get services
#### Create configuration to be used as environment variables
kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
#### get the list of configuration map  
kubectl get configmap
#### get configuration data for currency conversion
kubectl get configmap currency-conversion
#### view configuration in yaml format
kubectl get configmap currency-conversion -o yaml
#### exporting configuration to yaml file
kubectl get configmap currency-conversion -o yaml >> configmap.yaml
#### get previous deployments revisions history
kubectl rollout history deployment currency-conversion
kubectl rollout history deployment currency-exchange
#### revert the deployment to a previous revisions
kubectl rollout undo deployment currency-exchange --to-revision=1
#### configure autoscale based on cpu usage percentage allowing the max of 3 instances
kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=70
#### check the horizontal pod autoscaler configuration
kubectl get horizontalpodautoscaler
#### check the cpu and memory utilization from pods
kubectl top pod
#### check the cpu and memory utilization from specific nodes
kubectl top nodes
#### delete the horizontal pod autoscaler configuration
kubectl delete hpa currency-exchange