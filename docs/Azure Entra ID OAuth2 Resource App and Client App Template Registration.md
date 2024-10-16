# Azure Entra ID Spring Boot App Registration for OAuth2 Support
To set up Azure Entra ID (formerly Azure Active Directory) for OAuth2 authorization so that client applications can authenticate and access REST APIs served by your Spring Boot application, you can use the `az rest` command to interact directly with the Microsoft Graph API. Below are the step-by-step `az rest` commands to achieve this configuration.

To set up Azure Entra ID (formerly Azure Active Directory) for OAuth2 authorization so that client applications can authenticate and access REST APIs served by your Spring Boot application, you can use the `az rest` command to interact directly with the Microsoft Graph API. Below are the step-by-step `az rest` commands to achieve this configuration.

**Prerequisites:**

- Azure CLI installed and logged in.
- Ensure you have the necessary permissions to create and modify app registrations in Azure Entra ID.

---

## **Step 1: Register the Spring Boot Application in Azure Entra ID**

Create an application registration for your Spring Boot application.

### Define variables
```bash
export RESOURCE_APP_ID_URI="api://tac-rma-case-api-proxy-demo-$(uuidgen)"
export RESOURCE_APP_DISPLAY_NAME="TAC/RMA Case API Proxy"
export RESOURCE_APP_SIGN_IN_AUDIENCE="AzureADMultipleOrgs"
```

### Create the application registration
```bash
RESOURCE_APP_RESPONSE=$(az rest --method post --uri https://graph.microsoft.com/v1.0/applications --headers "Content-Type=application/json" --body "{ 
  \"displayName\": \"$RESOURCE_APP_DISPLAY_NAME\",
  \"signInAudience\": \"$RESOURCE_APP_SIGN_IN_AUDIENCE\",
  \"identifierUris\": [\"$RESOURCE_APP_ID_URI\"],
  \"api\": {
    \"requestedAccessTokenVersion\": 2
  }
}")
```

### Extract the Resource application's ID and object ID
```bash
export RESOURCE_APP_ID=$(echo $RESOURCE_APP_RESPONSE | jq -r '.id')
export RESOURCE_APP_OBJ_ID=$(echo $RESOURCE_APP_RESPONSE | jq -r '.appId')
```

---

## **Step 2: Expose an API and Define Scopes for the Spring Boot Application**

Define the scopes (permissions) that client applications can request.

### Generate a unique GUID for the scope ID
```bash
export RESOURCE_APP_SCOPE_ID=$(uuidgen)
```

### Update the resource application to expose the API scope
```bash
az rest --method patch --uri https://graph.microsoft.com/v1.0/applications/$RESOURCE_APP_ID --headers "Content-Type=application/json" --body "{
  \"api\": {
    \"oauth2PermissionScopes\": [
      {
        \"adminConsentDescription\": \"Allows the app to access the TAC/RMA Case API on behalf of the signed-in user.\",
        \"adminConsentDisplayName\": \"Access TAC/RMA Case API\",
        \"id\": \"$RESOURCE_APP_SCOPE_ID\",
        \"isEnabled\": true,
        \"type\": \"User\",
        \"userConsentDescription\": \"Allows you to access the TAC/RMA Case API.\",
        \"userConsentDisplayName\": \"Access TAC/RMA Case API\",
        \"value\": \"access_as_user\"
      }
    ]
  }
}"
```

---

## **Step 3: Register the Client Application in Azure Entra ID**

Create an application registration for the client application that will access the Spring Boot API.

### Define variables
```bash
export CLIENT_APP_DISPLAY_NAME="TAC/RMA Client Application"
export CLIENT_APP_REDIRECT_URI="https://localhost:8080/auth/callback"
```

### Create the client application registration
```bash
CLIENT_APP_RESPONSE=$(az rest --method post --uri https://graph.microsoft.com/v1.0/applications --headers "Content-Type=application/json" --body "{
  \"displayName\": \"$CLIENT_APP_DISPLAY_NAME\",
  \"signInAudience\": \"AzureADMyOrg\",
  \"web\": {
    \"redirectUris\": [\"$CLIENT_APP_REDIRECT_URI\"]
  }
}")
```

### Extract the application ID and object ID
```bash
export CLIENT_APP_ID=$(echo $CLIENT_APP_RESPONSE | jq -r '.id')
export CLIENT_APP_OBJ_ID=$(echo $CLIENT_APP_RESPONSE | jq -r '.appId')
```

---

## **Step 4: Configure the Client Application to Access the Spring Boot API**

Update the client application to request access to the Spring Boot API scopes.

```bash
az rest --method patch --uri https://graph.microsoft.com/v1.0/applications/$CLIENT_APP_ID --headers "Content-Type=application/json" --body "{
  \"requiredResourceAccess\": [
    {
      \"resourceAppId\": \"$RESOURCE_APP_OBJ_ID\",
      \"resourceAccess\": [
        {
          \"id\": \"$RESOURCE_APP_SCOPE_ID\",
          \"type\": \"Scope\"
        }
      ]
    }
  ]
}"
```

---

## **Step 5: Grant Admin Consent for the Client Application (Optional)**

If the scope requires admin consent, grant it using the following commands.

### Create Service Principals (Sometimes this is necessary???)

#### Create service principal for the Resource App
```bash
RESOURCE_APP_SP_RESPONSE=$(az rest --method POST --uri https://graph.microsoft.com/v1.0/servicePrincipals --headers "Content-Type=application/json" --body "{
  \"appId\": \"$RESOURCE_APP_OBJ_ID\"
}")
```
#### Capture the Resource Application's Service Principal ID
```bash
export RESOURCE_APP_SP_OBJ_ID=$(echo "$RESOURCE_APP_SP_RESPONSE" | jq -r '.id')
```

#### Create service principal for the Client application
```bash
CLIENT_APP_SP_RESPONSE=$(az rest --method POST --uri https://graph.microsoft.com/v1.0/servicePrincipals --headers "Content-Type=application/json" --body "{
  \"appId\": \"$CLIENT_APP_OBJ_ID\"
}")
```
#### Capture the Client Application Service Principal ID
```bash
export CLIENT_APP_SP_OBJ_ID=$(echo "$CLIENT_APP_SP_RESPONSE" | jq -r '.id')
```

### Grant admin consent to the Resource App's API scope
```bash
az rest --method post --uri https://graph.microsoft.com/v1.0/oauth2PermissionGrants --headers "Content-Type=application/json" --body "{
  \"clientId\": \"$CLIENT_APP_SP_OBJ_ID\",
  \"consentType\": \"AllPrincipals\",
  \"principalId\": null,
  \"resourceId\": \"$RESOURCE_APP_SP_OBJ_ID\",
  \"scope\": \"access_as_user\"
}"
```

---

### **Step 6: Configure Your Spring Boot Application**

Ensure your Spring Boot application is configured to validate tokens issued by Azure Entra ID. Typically, this involves setting up the appropriate security filters and validating JWT tokens against Azure Entra ID's issuer and audience.

---

### **Notes:**

- **UUID Generation:** Ensure `uuidgen` and `jq` are installed on your system for UUID generation and JSON parsing.
- **Permissions:** The account used to execute these commands must have the necessary permissions in Azure Entra ID.
- **Sign-In Audience:** The `signInAudience` is set to `AzureADMyOrg` for single-tenant applications. Adjust if needed.
- **Redirect URIs:** Update the `redirectUris` to match those used by your client application.

---

By executing these commands, you set up Azure Entra ID to provide OAuth2 authorization, enabling client applications to authenticate and securely access your Spring Boot application's REST APIs.