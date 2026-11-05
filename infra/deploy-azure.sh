#!/bin/bash
# Deploy do SOLIN na Azure: PostgreSQL gerenciado + App Service (Java 17).
# Uso: ./infra/deploy-azure.sh   (precisa do "az login" feito antes)
# A senha do banco fica em infra/.env (fora do Git).

source "$(dirname "$0")/.env"

RG=rg-564099-solin
LOCAL=eastus2
# A assinatura Azure for Students bloqueia PostgreSQL em eastus2; o banco fica em northcentralus
LOCAL_BANCO=northcentralus
DB_SERVER=psql-564099-solin
DB_NOME=solindb
DB_USUARIO=solinadmin
PLANO=plan-564099-solin
APP=app-564099-solin

# 1. Grupo de recursos
az group create --name $RG --location $LOCAL

# 2. Servidor PostgreSQL (o menor tamanho) liberado para servicos da Azure
az postgres flexible-server create --resource-group $RG --name $DB_SERVER --location $LOCAL_BANCO \
  --admin-user $DB_USUARIO --admin-password "$DB_PASSWORD" \
  --tier Burstable --sku-name Standard_B1ms --storage-size 32 --version 16 \
  --public-access 0.0.0.0 --yes

# 3. Banco de dados do SOLIN
az postgres flexible-server db create --resource-group $RG --server-name $DB_SERVER --database-name $DB_NOME

# 4. Plano do App Service (Linux B1)
az appservice plan create --resource-group $RG --name $PLANO --location $LOCAL --is-linux --sku B1

# 5. Aplicacao web com Java 17
az webapp create --resource-group $RG --plan $PLANO --name $APP --runtime "JAVA:17-java17"

# 5b. O App Service roda o .jar que a pipeline publica
az webapp config set --resource-group $RG --name $APP --startup-file "java -jar /home/site/wwwroot/solin-api-0.0.1-SNAPSHOT.jar"

# 6. Variaveis de ambiente que o SOLIN le (application.properties)
az webapp config appsettings set --resource-group $RG --name $APP --settings \
  DB_URL="jdbc:postgresql://$DB_SERVER.postgres.database.azure.com:5432/$DB_NOME?sslmode=require" \
  DB_USER=$DB_USUARIO DB_PASSWORD="$DB_PASSWORD" SERVER_PORT=80 TZ=America/Sao_Paulo

# 7. Publicar o .jar gerado por "mvn package"
az webapp deploy --resource-group $RG --name $APP --src-path target/solin-api-0.0.1-SNAPSHOT.jar --type jar

echo "SOLIN no ar: https://$APP.azurewebsites.net/solin/login"
