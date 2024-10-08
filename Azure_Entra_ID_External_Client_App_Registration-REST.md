
# Instructions for External Users to Register a Client Application Using `az rest` with Microsoft Graph

This document provides step-by-step instructions for external users or administrators to register a client application in their own Azure AD tenant using `az rest` with Microsoft Graph. This guide is based on making REST API calls directly using the built-in `az rest` command without additional extensions.

## Prerequisites
1. **Azure CLI**: Ensure Azure CLI is installed and updated to the latest version. You can install or update it from [Azure CLI Installation Guide](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli).
2. **Sign In**: Sign in to your Azure AD tenant using the following command:
    ```bash
    az login --allow-no-subscriptions
    ```
3. **Verify Your Azure CLI Version**:
   Make sure you are using Azure CLI version 2.37.0 or later. Check your version using:
    ```bash
    az version
    ```

## Step 1: Set the `{tenant-id}` as an Environment Variable

The `{tenant-id}` is the unique identifier of your Azure AD tenant. To find your tenant ID:

1. Run the following command to list your Azure AD tenants:
    ```bash
    az account tenant list --query "[].tenantId"
    ```

2. Copy the tenant ID that corresponds to your Azure AD tenant.

3. Set the `{tenant-id}` as an environment variable:
    ```bash
    export tenant_id="<your-tenant-id>"
    ```

This command sets the `tenant_id` variable for the current shell session. Use `$tenant_id` wherever `{tenant-id}` is required in the following commands.

## Step 2: Register the Client Application in the External Tenant

Create a new client application in the external Azure AD tenant that will act as a client to access the Resource Application.

```bash
# Create a new Client Application in the external Azure AD tenant using the tenant_id variable
az rest --method POST --uri "https://graph.microsoft.com/v1.0/applications"     --body '{
        "displayName": "External Client Application",
        "signInAudience": "AzureADMyOrg",
        "identifierUris": ["api://$tenant_id/external-client-app"],
        "api": {
            "requestedAccessTokenVersion": 2
        }
    }'
```

- `identifierUris`: Use the `$tenant_id` variable to specify the Application ID URI dynamically.
- `signInAudience`: Set to `AzureADMyOrg` if it should only be accessible by users within the external organization.

## Step 3: Configure API Permissions for the Client Application

The external client application must request API permissions (scopes) exposed by the Resource Application in the other tenant. You must have the **API scope IDs** and **Resource Application Object ID**.

```bash
# Get the Application ID of the newly created external client application
externalClientAppId=$(az rest --method GET --uri "https://graph.microsoft.com/v1.0/applications?\$filter=displayName eq 'External Client Application'" --query "value[0].appId" -o tsv)

# Define the Resource Application ID URI (provided by the Resource Application owner)
resourceApiIdUri="api://{your-resource-app-client-id}"  # Replace with the Application ID URI of the Resource App

# Define the Resource Application Object ID and scope IDs (provided by the Resource Application owner)
resourceApiId="{resource-app-object-id}"  # Replace with the Object ID of the Resource App
scope1Id="e1a9fc52-7cb1-4e8e-8eb7-5fc16d907c35"  # Replace with the Scope ID for `read.items`
scope2Id="fbc1204f-9b0a-431b-8309-c4c78553a66d"  # Replace with the Scope ID for `write.items`

# Configure API Permissions for the External Client Application
az rest --method POST --uri "https://graph.microsoft.com/v1.0/servicePrincipals/$externalClientAppId/appRoleAssignments"     --body '{
        "principalId": "'$externalClientAppId'",
        "resourceId": "'$resourceApiId'",
        "appRoleId": "'$scope1Id'"
    }'
```

## Step 4: Grant Admin Consent for the API Permissions

If the external tenant requires admin consent, the tenant admin must grant consent to the API permissions requested by the external client application.

```bash
# Grant admin consent for the API permissions requested by the external client application
az rest --method POST --uri "https://graph.microsoft.com/v1.0/servicePrincipals/$externalClientAppId/oauth2PermissionGrants"     --body '{
        "clientId": "'$externalClientAppId'",
        "consentType": "AllPrincipals",
        "principalId": null,
        "resourceId": "'$resourceApiId'",
        "scope": "read.items write.items"
    }'
```

## Step 5: Create a Service Principal for the External Client Application

Create a service principal for the external client application in the external Azure AD tenant.

```bash
# Create a service principal for the external client application
az rest --method POST --uri "https://graph.microsoft.com/v1.0/servicePrincipals"     --body '{
        "appId": "'$externalClientAppId'"
    }'
```

## Key Points to Remember

- **API Permissions (Scope IDs)**: External users must have the `scope IDs` of the Resource Application’s API permissions.
- **Resource Application’s Object ID and Application ID URI**: These must be provided by the Resource Application owner.
- **Consent Requirements**: Admin consent might be required in the external tenant.

## Summary

This document provides detailed instructions for external users or admins to register and configure a client application in their own tenant using the `az rest` command with Microsoft Graph. The external client application will request permissions to access the Resource Application’s API based on the scopes and roles defined in the Resource Application.
