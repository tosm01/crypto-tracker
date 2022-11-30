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

# 4. Create a container group
resource "azurerm_container_group" "cryptotracker_container_group" {
  name                = "cryptocurrencyapi"
  resource_group_name = azurerm_resource_group.cryptotracker_resource_group.name
  location            = azurerm_resource_group.cryptotracker_resource_group.location

  ip_address_type     = "Public"
  dns_name_label      = "cryptocurrencyapi"
  os_type             = "Linux"

  container {
    name              = "cryptocurrencyapicontainer"
    image             = "taha12k/cryptocurrencyapi"
    cpu               = "1"
    memory            = "1"

    ports {
      port            = 8080
      protocol        = "TCP"
      }
    }
}