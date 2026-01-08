# URL Shortener Service with Basic Analytics

### Specs
Please consider the following bullets:
- Clarify requirements together with technical specifications
- Dockerize the application.
- Integrate GitHub Actions to perform necessary linting and testing.
- Additional: Provide configurations for running on the Kubernetes cluster.
- Provide instructions to run and deploy to the README.md

### Prerequisites
- Docker
- Docker Compose
- Postman
### Deploy and run locally

- In the directory which contains docker-compose.yml file, run:
```
docker compose up -d
```
- Please wait for the environment to initialize (this can take several minutes). Once the containers are running, 
you can access the Keycloak in your browser: http://host.docker.internal:1111/ then log in to the admin console with the default credentials:
**_admin_** / **_admin_**, enable client `account` with Standard flow for Authentication 
& don't forget to generate a new client secret, then use the following curl to obtain access token:
```cURL
curl --location 'http://host.docker.internal:1111/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic YWNjb3VudDp4Mklwa1BmaktOd0RRdHlxTU54RU9tUERUVW9LVmkwcg==' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=admin' \
--data-urlencode 'password=admin'
```
- Once you have a proper token, you can use it to shorten a long URL, you will also see the short URL in the response section of Postman
```cURL
curl --location 'http://host.docker.internal:8080/links' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [token]' \
--data '{
    "originalUrl": "https://www.google.com/search?gs_ssp=eJzj4tTP1TcwMU02T1JgNGB0YPBiS8_PT89JBQBASQXT&q=google&rlz=1C1CHBF_enVN1151VN1151&oq=g&gs_lcrp=EgZjaHJvbWUqEwgCEC4YgwEYxwEYsQMY0QMYgAQyBggAEEUYPDIGCAEQRRg8MhMIAhAuGIMBGMcBGLEDGNEDGIAEMggIAxBFGCcYOzIOCAQQRRgnGDsYgAQYigUyBggFEEUYPDIGCAYQRRg8MgYIBxBFGDzSAQg5MTczajBqN6gCALACAA&sourceid=chrome&ie=UTF-8"
}'
```
- The short URL can be accessed in your browser, for instance: http://host.docker.internal:8080/c/FM2lmL0R 
- Please use below curl to view some basic analytics
```cURL
curl --location 'http://host.docker.internal:8080/links' \
--header 'Authorization: Bearer [token]'
```

### Kubernetes Deployment
[Helm](https://helm.sh/) is recommended to use to deploy and run the application on a Kubernetes cluster. 
You should concretely see a set of manifest files in directory charts/shortener-service to work with Helm for the deployment.



