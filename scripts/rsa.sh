#!/bin/sh

# Directory of frontend application
FRONTEND_DIR="services/frontend-client"

# Directory of backend application
AUTH_SERVER_PATH="services/auth-service"

# Private key for JWT
PRIVATE_KEY="private.pem"

# Public key for JWT
PUBLIC_KEY="public.pem"

# Generate private and public key

openssl genpkey -algorithm RSA -out ${PRIVATE_KEY} -pkeyopt rsa_keygen_bits:4096
[ "$?" = 0 ] || exit 1

openssl rsa -pubout -in ${PRIVATE_KEY} -out ${PUBLIC_KEY}
[ "$?" = 0 ] || exit 1

cp ${PRIVATE_KEY} ${PUBLIC_KEY} ../${AUTH_SERVER_PATH}/src/main/resources/
