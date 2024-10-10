# Client App registration for Customers from **external Azure AD tenants**
Instructions for customers from external Azure AD tenants to register their own client applications.

---
## Step 1: Register a New Application
External customers need to register a new application in their Azure AD tenant.

### Login to your subscription
```bash
az login
```

### Define variables
```bash
export CLIENT_APP_NAME="Client Application"
export CLIENT_APP_DISPLAY_NAME="Client Application"
export REDIRECT_URI="https://yourapp.example.com/auth/callback" # External customer's redirect URI
```

### Create the client application registration
```bash
CLIENT_APP_RESPONSE=$(az rest --method POST --uri https://graph.microsoft.com/v1.0/applications --headers "Content-Type=application/json" --body "{
  \"displayName\": \"$CLIENT_APP_DISPLAY_NAME\",
  \"signInAudience\": \"AzureADMultipleOrgs\",
  \"web\": {
    \"redirectUris\": [\"$REDIRECT_URI\"]
  }
}")
```

## Step 2: Configure API Permissions to Access Your API

### Get the Client App ID
```bash
export CLIENT_APP_ID=$(echo $CLIENT_APP_RESPONSE | jq -r '.id')
```

### Update the client application to request access to your API scopes
```bash
az rest --method PATCH --uri "https://graph.microsoft.com/v1.0/applications/$CLIENT_APP_ID" --headers "Content-Type=application/json" --body "{
  \"requiredResourceAccess\": [
    {
      \"resourceAppId\": \"$SPRING_APP_APP_ID\", # Your application's App ID
      \"resourceAccess\": [
        {
          \"id\": \"$SCOPE_ID\", # Scope ID for 'access_as_user' from your application
          \"type\": \"Scope\"
        }
      ]
    }
  ]
}"
```

### Explanation:
- **`resourceAppId`:** Your application's App ID, which identifies your API.
- **`resourceAccess`:** Specifies the scopes the client application is requesting access to.


## Step 3: Grant Consent
    External customers may need to grant consent for the application to access your API.
    - User Consent:
    - If allowed by their tenant's policies and the scope is user-consentable, users can consent during authentication.
    - Admin Consent:
    - If required, an admin in their tenant must grant consent.

### External Customer Actions
    - Admins can grant consent via:
        - The Azure Portal under **Enterprise applications**.
        - An admin consent URL provided by the client application.
    - Users may be prompted to consent when they first authenticate.

---

- Tenant Restrictions (Optional):
    - If you want to limit access to specific tenants, validate the `tid` (tenant ID) claim in the token.

---

## Example: External Customer's Process Summary

1. Register Client Application in Their Azure AD Tenant:
    - Use the Azure Portal or `az rest` commands to create an application.

2. Configure API Permissions:
    - Add API permissions by specifying your API's App ID and scope ID.

3. Grant Consent:
    - Users or admins grant consent as required.

4. Configure Their Application:
    - Set up their application to authenticate users and obtain tokens for your API.
    - Use OAuth2 flows, such as authorization code flow.

5. Access Your API:
    - Use the obtained tokens to make authorized requests to your Spring Boot API.

---

## Changes to the Client Application Registration

Since external customers will create their own client applications, you don't need to change your existing client application registration. However, you can:

- Provide a Template or Manifest:
    - Offer a JSON manifest of your client application registration that external customers can use as a starting point.

- Share Configuration Details:
    - Inform external customers of any necessary settings, such as redirect URIs, required scopes, and authentication flows.

---

## Security Considerations

- Token Validation:
    - Ensure your Spring Boot application validates tokens correctly, including signature, issuer, audience, and expiration.

- Access Control:
    - Implement application-level access controls to protect sensitive resources.

- Data Isolation:

    - Ensure that data belonging to one tenant is not accessible by another tenant.
