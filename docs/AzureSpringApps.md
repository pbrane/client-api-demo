
# Azure Spring Apps Deployment Guide

This README provides step-by-step instructions for building, testing, and deploying a Spring Boot application using Azure Spring Apps.

## Table of Contents

- [Build and Test the Project](#build-and-test-the-project)
- [Azure Cloud Setup](#azure-cloud-setup)
  - [Create Azure Account](#create-azure-account)
  - [Create a Resource Group](#create-a-resource-group)
  - [Install the Azure CLI](#install-the-azure-cli)
  - [Login with the CLI](#login-with-the-cli)
  - [Set the Subscription](#set-the-subscription)
- [Spring App Deployment](#spring-app-deployment)
  - [Helper Script](#helper-script)
  - [Create an Azure Spring Apps Instance](#create-an-azure-spring-apps-instance)
  - [Create an App within the Spring Apps Instance](#create-an-app-within-the-spring-apps-instance)
  - [Deploy the JAR File to Azure Spring Apps](#deploy-the-jar-file-to-azure-spring-apps)

## Build and Test the Project

You'll need Git and a Java 21 JDK installed. This was done from a terminal on a Mac. The instructions are the same for Linux. Windows might differ.

### Git the Code

```bash
cd ~
mkdir deploy
cd deploy
git clone https://github.com/pbrane/client-api-demo.git
```

### Build it

```bash
cd client-api-demo
./mvnw clean package
```

### Test it

```bash
./mvnw spring-boot:run

curl -X 'GET' \
  'https://localhost:8080/api/v1/tacCases/CN004' \
  -H 'accept: */*'
```

## Azure Cloud Setup

### Create Azure Account

Create an Azure Account and Subscription Tier using the browser. For this example, refer to the following:

- [Azure Pricing Options](https://azure.microsoft.com/en-us/pricing/purchase-options/azure-account)

### Create a Resource Group

Create an Azure Resource Group to contain the Azure Spring Apps services. This example uses the Resource Group: **"MolexProject"**.

- [Step-by-Step Guide: Creating an Azure Resource Group](https://techcommunity.microsoft.com/t5/startups-at-microsoft/step-by-step-guide-creating-an-azure-resource-group-on-azure/ba-p/3792368)

### Install the Azure CLI

Install the Azure CLI by following the instructions provided here:

- [Azure CLI Installation Guide](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli)

### Login with the CLI

```bash
az login
```

### Set the Subscription

From the documentation:

```bash
az account set --subscription <your-subscription-id>
```

Practical Example:

```bash
az account set --subscription 87ed1b31-e580-4be3-be4a-6c0379f41995
```

## Spring App Deployment

### Helper Script

The provisioning of a Spring App may fail on Azure if these providers are not registered. Resource Providers are REST-based operations for specific Azure services.

```bash
for provider in Microsoft.AppPlatform Microsoft.Network Microsoft.Storage Microsoft.AlertsManagement Microsoft.Insights Microsoft.KeyVault Microsoft.OperationalInsights; do
    az provider register --namespace $provider
done
```

### Create an Azure Spring Apps Instance (Using the Standard Pricing Tier)

From the documentation:

```bash
az spring create --resource-group MolexProject --name <spring-app-service-name> --location <region>
```

Practical Example:

```bash
az spring create \
  --resource-group MolexProject \
  --name rest-api-gateway \
  --subscription 87ed1b31-e580-4be3-be4a-6c0379f41995 \
  --sku Standard \
  --location westus \
  --verbose
```

### Create an App within the Spring Apps Instance

From the documentation:

```bash
az spring app create --name <app-name> --resource-group MolexProject --service <spring-app-service-name>
```

Practical Example:

```bash
az spring app create \
  --name client-api-demo-app \
  --service rest-api-gateway \
  --jvm-options='-Xms1024m -Xmx2048m' \
  --cpu 2 \
  --memory 3Gi \
  --runtime-version Java_21 \
  --subscription 87ed1b31-e580-4be3-be4a-6c0379f41995 \
  --resource-group MolexProject \
  --location westus \
  --verbose
```

### Deploy the JAR File to Azure Spring Apps

```bash
az spring app deploy \
  --artifact-path target/client-api-demo-0.0.1-SNAPSHOT.jar \
  --name client-api-demo-app \
  --service rest-api-gateway \
  --jvm-options='-Xms1024m -Xmx2048m' \
  --runtime-version Java_21 \
  --subscription 87ed1b31-e580-4be3-be4a-6c0379f41995 \
  --resource-group MolexProject \
  --location westus \
  --output table \
  --verbose
```

