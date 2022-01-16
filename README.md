# Bank Account Service
## Daniel Machado Vasconcelos

### Basic requirements (that were implemented):
* Expose endpoints to create and close a bank account;
* Expose endpoints to deposit and withdraw founds;
* The service must make use of event source and CQRS;
* The service must persist aggregate state of all bank accounts;

### Extra requirements to be done:
* Add security layer to permit only authenticated users to operate;
* Add swagger documentation to the Rest API;
* Make sure all exchanged messages are encrypted, authenticated and has schema.

---
Prerequisites
-------------

* Java JDK 17
* Maven 
* Docker / Docker Compose

#### Resources
* Kafka
* Postgres
* Elasticsearch

### Tech Stack usage

**Kafka**

I used kafka as my main message broker, to communicate between other services components, that are interested in the events.

**Postgres**

This resource saves the aggregate state of my bank account. The service reduce the list of events to a single bank account database row.

**Elasticsearch**

This database saves the list of events in order, ensured by an optimistic lock.


## How to build?

Clone this repo into new project folder (e.g., `bank-account-service`).

```bash
git clone https://github.com/DanielMachadoVasconcelos/bank-account.git
cd bank-account-service
```

Start the external resources by running the docker-compose file. (It may take a while to start all resources)
```bash
docker-compose up -d 
```
---
**Start individually the spring boot applications (command and query)**

Start the service (command) that expose the REST API:
```bash
cd command
mvn spring-boot:run
```
Use the following commands to open, deposit, withdraw and close a bank account:

####To open a bank account
```bash
curl --location --request POST 'localhost:5000/api/v1/bank-accounts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "account_holder":"daniel.vasconcelos",
    "account_type": "SAVINGS",
    "opening_balance": 45.0
}'
```
####To deposit funds to bank account
```bash
curl --location --request POST 'localhost:5000/api/v1/bank-accounts/{bank-account-id}/deposits' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount": 12
}'
```
####To withdraw funds from a bank account
```bash
curl --location --request POST 'localhost:5000/api/v1/bank-accounts/{bank-account-id}/withdraws' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount": 7.45
}'
```

####To close a bank account
```bash
curl --location --request POST 'localhost:5000/api/v1/bank-accounts/6a21d2c6-a7e9-42ee-a584-86dcc331aafc/close' \
--header 'Content-Type: application/json' \
--data-raw '{
}'
```
---
Start the service (query) that will aggregate the bank account events:
```bash
cd query
mvn spring-boot:run
```

###Postgres
* Access the local url (localhost:5050) in your favorite browser to verify the Postgres Database

**Use the following credentials:**

| username      | password |
|---------------|--------|
| admin@admin.com | admin | 


###Kibana
* Access the local url (localhost:5601) in your favorite browser to verify the Elasticsearch Database

**No credentials are needed:**

* Find the option 'Dev Tools' on the left menu
* Type the following command in the console:

```
 GET event-store/_search
```