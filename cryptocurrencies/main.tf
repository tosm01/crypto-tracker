# 1. Specify the version of the AzureRM Provider to use
terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
      version = "=3.33.0"
    }
  }
}

# 2. Configure the AzureRM Provider
provider "azurerm" {
  features {}
}

# 3. Create a resource group
resource "azurerm_resource_group" "cryptotracker_resource_group" {
  name     = "cryptotracker"
  location = "Southeast Asia"
}

# 4a. Create a container group (Image from Docker Hub)
resource "azurerm_container_group" "cryptotracker_container_group_dh" {
  name                = "cryptocurrencyapi-dh"
  resource_group_name = azurerm_resource_group.cryptotracker_resource_group.name
  location            = azurerm_resource_group.cryptotracker_resource_group.location

  ip_address_type     = "Public"
  dns_name_label      = "cryptocurrencyapi-dh"
  os_type             = "Linux"

  container {
    name              = "cryptocurrencyapi-dh"
    image             = "taha12k/cryptocurrencyapi"
    cpu               = "1"
    memory            = "1"

    ports {
      port            = 8080
      protocol        = "TCP"
      }
    }
}

# 4b. Create a container group (Image from Azure Container Registry)
resource "azurerm_container_group" "cryptotracker_container_group_acr" {
  name                = "cryptocurrencyapi-acr"
  resource_group_name = azurerm_resource_group.cryptotracker_resource_group.name
  location            = azurerm_resource_group.cryptotracker_resource_group.location

  ip_address_type     = "Public"
  dns_name_label      = "cryptocurrencyapi-acr"
  os_type             = "Linux"

  container {
    name              = "cryptocurrencyapi-acr"
    image             = var.cryptotracker_container_group_acr_image
    cpu               = "1"
    memory            = "1"

    ports {
      port            = 8080
      protocol        = "TCP"
      }
      
    }
    
    image_registry_credential {
        server = var.cryptotracker_container_group_acr_server
        username = var.cryptotracker_container_group_acr_username
        password = var.cryptotracker_container_group_acr_password
    }
}